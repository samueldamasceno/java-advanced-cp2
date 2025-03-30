package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.UsuarioRequest;
import com.fiap.cinema_api.dto.UsuarioResponse;
import com.fiap.cinema_api.model.Usuario;
import com.fiap.cinema_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listarTodos().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getDataCriacao()))
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        Usuario u = service.buscarPorId(id);
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getDataCriacao());
    }

    @PostMapping
    public UsuarioResponse criar(@RequestBody @Valid UsuarioRequest request) {
        Usuario u = new Usuario(null, request.nome(), request.email(), request.senha(), null);
        Usuario salvo = service.criar(u);
        return new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getDataCriacao());
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        Usuario atualizado = new Usuario(null, request.nome(), request.email(), request.senha(), null);
        Usuario salvo = service.atualizar(id, atualizado);
        return new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getDataCriacao());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
