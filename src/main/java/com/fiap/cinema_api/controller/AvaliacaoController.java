package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.*;
import com.fiap.cinema_api.model.*;
import com.fiap.cinema_api.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para avaliações de filmes")
public class AvaliacaoController {

    @Autowired private AvaliacaoService avaliacaoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Listar todas as avaliações")
    public List<AvaliacaoResponse> listar() {
        return avaliacaoService.listarTodas().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma avaliação por ID")
    public EntityModel<AvaliacaoResponse> buscar(@PathVariable Long id) {
        Avaliacao av = avaliacaoService.buscarPorId(id);
        return EntityModel.of(toResponse(av),
                linkTo(methodOn(AvaliacaoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(AvaliacaoController.class).listar()).withRel("todas"));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova avaliação")
    public AvaliacaoResponse criar(@RequestBody @Valid AvaliacaoRequest request) {
        Usuario usuario = usuarioService.buscarPorId(request.usuarioId());
        Filme filme = filmeService.buscarPorId(request.filmeId());
        Avaliacao av = new Avaliacao(null, request.nota(), request.comentario(), usuario, filme);
        return toResponse(avaliacaoService.criar(av));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma avaliação por ID")
    public void deletar(@PathVariable Long id) {
        avaliacaoService.deletar(id);
    }

    private AvaliacaoResponse toResponse(Avaliacao av) {
        return new AvaliacaoResponse(
                av.getId(),
                av.getNota(),
                av.getComentario(),
                new UsuarioResumidoResponse(av.getUsuario().getId(), av.getUsuario().getNome(), av.getUsuario().getEmail()),
                new FilmeResumidoResponse(av.getFilme().getId(), av.getFilme().getTitulo(), av.getFilme().getGenero())
        );
    }
}