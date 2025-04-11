package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.*;
import com.fiap.cinema_api.model.Filme;
import com.fiap.cinema_api.service.AvaliacaoService;
import com.fiap.cinema_api.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Endpoints para CRUD de filmes")
public class FilmeController {

    @Autowired private FilmeService filmeService;
    @Autowired private AvaliacaoService avaliacaoService;

    @GetMapping
    @Operation(summary = "Listar todos os filmes com paginação")
    public Page<FilmeResponse> listar(Pageable pageable) {
        return filmeService.listarTodos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("titulo")))
                .map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um filme por ID com avaliações")
    public EntityModel<FilmeResponse> buscar(@PathVariable Long id) {
        Filme f = filmeService.buscarPorId(id);
        return EntityModel.of(toResponse(f),
                linkTo(methodOn(FilmeController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(FilmeController.class).listar(PageRequest.of(0, 10))).withRel("todos"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo filme")
    public FilmeResponse criar(@RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        return toResponse(filmeService.criar(f));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um filme existente")
    public FilmeResponse atualizar(@PathVariable Long id, @RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        return toResponse(filmeService.atualizar(id, f));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um filme por ID")
    public void deletar(@PathVariable Long id) {
        filmeService.deletar(id);
    }

    private FilmeResponse toResponse(Filme f) {
        List<AvaliacaoResumidaResponse> avaliacoes = avaliacaoService.listarPorFilme(f.getId()).stream()
                .map(av -> new AvaliacaoResumidaResponse(av.getId(), av.getNota(), av.getComentario()))
                .toList();

        return new FilmeResponse(
                f.getId(),
                f.getTitulo(),
                f.getDiretor(),
                f.getGenero(),
                f.getAnoLancamento(),
                f.getDuracaoMin(),
                avaliacoes
        );
    }
}