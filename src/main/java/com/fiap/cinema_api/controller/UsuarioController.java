package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.UsuarioRequest;
import com.fiap.cinema_api.dto.UsuarioResponse;
import com.fiap.cinema_api.model.Usuario;
import com.fiap.cinema_api.service.UsuarioService;
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

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação")
    public Page<UsuarioResponse> listar(Pageable pageable) {
        return service.listarTodos(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")))
                .map(u -> new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getDataCriacao()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário por ID")
    public EntityModel<UsuarioResponse> buscar(@PathVariable Long id) {
        Usuario u = service.buscarPorId(id);
        UsuarioResponse response = new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getDataCriacao());
        return EntityModel.of(response,
                linkTo(methodOn(UsuarioController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar(PageRequest.of(0, 10))).withRel("todos"));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public UsuarioResponse criar(@RequestBody @Valid UsuarioRequest request) {
        Usuario u = new Usuario(null, request.nome(), request.email(), request.senha(), null);
        Usuario salvo = service.criar(u);
        return new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getDataCriacao());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public UsuarioResponse atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        Usuario atualizado = new Usuario(null, request.nome(), request.email(), request.senha(), null);
        Usuario salvo = service.atualizar(id, atualizado);
        return new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getDataCriacao());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário por ID")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
