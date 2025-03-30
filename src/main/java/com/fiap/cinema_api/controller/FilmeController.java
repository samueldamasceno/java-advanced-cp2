package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.model.Filme;
import com.fiap.cinema_api.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping
    public List<Filme> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Filme buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Filme criar(@RequestBody Filme filme) {
        return service.criar(filme);
    }

    @PutMapping("/{id}")
    public Filme atualizar(@PathVariable Long id, @RequestBody Filme filme) {
        return service.atualizar(id, filme);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
