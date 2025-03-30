package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.FilmeRequest;
import com.fiap.cinema_api.dto.FilmeResponse;
import com.fiap.cinema_api.model.Filme;
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

    @Autowired
    private FilmeService service;

    @GetMapping
    @Operation(summary = "Listar todos os filmes com paginação")
    public Page<FilmeResponse> listar(Pageable pageable) {
        return service.listarTodos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("titulo")))
                .map(f -> new FilmeResponse(f.getId(), f.getTitulo(), f.getDiretor(), f.getGenero(), f.getAnoLancamento(), f.getDuracaoMin()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um filme por ID")
    public EntityModel<FilmeResponse> buscar(@PathVariable Long id) {
        Filme f = service.buscarPorId(id);
        FilmeResponse response = new FilmeResponse(f.getId(), f.getTitulo(), f.getDiretor(), f.getGenero(), f.getAnoLancamento(), f.getDuracaoMin());
        return EntityModel.of(response,
                linkTo(methodOn(FilmeController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(FilmeController.class).listar(PageRequest.of(0, 10))).withRel("todos"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo filme")
    public FilmeResponse criar(@RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        Filme salvo = service.criar(f);
        return new FilmeResponse(salvo.getId(), salvo.getTitulo(), salvo.getDiretor(), salvo.getGenero(), salvo.getAnoLancamento(), salvo.getDuracaoMin());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um filme existente")
    public FilmeResponse atualizar(@PathVariable Long id, @RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        Filme salvo = service.atualizar(id, f);
        return new FilmeResponse(salvo.getId(), salvo.getTitulo(), salvo.getDiretor(), salvo.getGenero(), salvo.getAnoLancamento(), salvo.getDuracaoMin());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um filme por ID")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
