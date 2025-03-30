package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.FilmeRequest;
import com.fiap.cinema_api.dto.FilmeResponse;
import com.fiap.cinema_api.model.Filme;
import com.fiap.cinema_api.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping
    public List<FilmeResponse> listar() {
        return service.listarTodos().stream()
                .map(f -> new FilmeResponse(f.getId(), f.getTitulo(), f.getDiretor(), f.getGenero(), f.getAnoLancamento(), f.getDuracaoMin()))
                .toList();
    }

    @GetMapping("/{id}")
    public FilmeResponse buscar(@PathVariable Long id) {
        Filme f = service.buscarPorId(id);
        return new FilmeResponse(f.getId(), f.getTitulo(), f.getDiretor(), f.getGenero(), f.getAnoLancamento(), f.getDuracaoMin());
    }

    @PostMapping
    public FilmeResponse criar(@RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        Filme salvo = service.criar(f);
        return new FilmeResponse(salvo.getId(), salvo.getTitulo(), salvo.getDiretor(), salvo.getGenero(), salvo.getAnoLancamento(), salvo.getDuracaoMin());
    }

    @PutMapping("/{id}")
    public FilmeResponse atualizar(@PathVariable Long id, @RequestBody @Valid FilmeRequest request) {
        Filme f = new Filme(null, request.titulo(), request.diretor(), request.genero(), request.anoLancamento(), request.duracaoMin());
        Filme salvo = service.atualizar(id, f);
        return new FilmeResponse(salvo.getId(), salvo.getTitulo(), salvo.getDiretor(), salvo.getGenero(), salvo.getAnoLancamento(), salvo.getDuracaoMin());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
