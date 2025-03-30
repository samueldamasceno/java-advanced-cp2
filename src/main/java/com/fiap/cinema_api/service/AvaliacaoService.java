package com.fiap.cinema_api.service;

import com.fiap.cinema_api.model.Avaliacao;
import com.fiap.cinema_api.repository.AvaliacaoRepository;
import com.fiap.cinema_api.repository.FilmeRepository;
import com.fiap.cinema_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    public List<Avaliacao> listarTodas() {
        return repository.findAll();
    }

    public Avaliacao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public Avaliacao criar(Avaliacao avaliacao) {

        usuarioRepository.findById(avaliacao.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        filmeRepository.findById(avaliacao.getFilme().getId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        return repository.save(avaliacao);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Avaliacao> listarPorFilme(Long filmeId) {
        return repository.findByFilmeId(filmeId);
    }

    public List<Avaliacao> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
