package com.fiap.cinema_api.service;

import com.fiap.cinema_api.model.Filme;
import com.fiap.cinema_api.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public List<Filme> listarTodos() {
        return repository.findAll();
    }

    public Page<Filme> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }

    public Filme criar(Filme filme) {
        return repository.save(filme);
    }

    public Filme atualizar(Long id, Filme atualizado) {
        Filme existente = buscarPorId(id);
        existente.setTitulo(atualizado.getTitulo());
        existente.setDiretor(atualizado.getDiretor());
        existente.setGenero(atualizado.getGenero());
        existente.setAnoLancamento(atualizado.getAnoLancamento());
        existente.setDuracaoMin(atualizado.getDuracaoMin());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Filme> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }
}
