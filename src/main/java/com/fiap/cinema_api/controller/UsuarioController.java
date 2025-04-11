package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.*;
import com.fiap.cinema_api.model.Usuario;
import com.fiap.cinema_api.service.UsuarioService;
import com.fiap.cinema_api.service.AvaliacaoService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para CRUD de usuários")
public class UsuarioController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private AvaliacaoService avaliacaoService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação")
    public Page<UsuarioResponse> listar(Pageable pageable) {
        return usuarioService.listarTodos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")))
                .map(this::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário por ID com avaliações")
    public EntityModel<UsuarioResponse> buscar(@PathVariable Long id) {
        Usuario u = usuarioService.buscarPorId(id);
        return EntityModel.of(toResponse(u),
                linkTo(methodOn(UsuarioController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar(PageRequest.of(0, 10))).withRel("todos"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public UsuarioResponse criar(@RequestBody @Valid UsuarioRequest request) {
        Usuario u = new Usuario(null, request.nome(), request.email(), request.senha());
        return toResponse(usuarioService.criar(u));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public UsuarioResponse atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        Usuario atualizado = new Usuario(null, request.nome(), request.email(), request.senha());
        return toResponse(usuarioService.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário por ID")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

    private UsuarioResponse toResponse(Usuario u) {
        List<AvaliacaoResumidaResponse> avaliacoes = avaliacaoService.listarPorUsuario(u.getId()).stream()
                .map(av -> new AvaliacaoResumidaResponse(av.getId(), av.getNota(), av.getComentario()))
                .toList();

        return new UsuarioResponse(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                avaliacoes
        );
    }
}