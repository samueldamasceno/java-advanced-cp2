package com.fiap.cinema_api.controller;

import com.fiap.cinema_api.dto.AvaliacaoRequest;
import com.fiap.cinema_api.dto.AvaliacaoResponse;
import com.fiap.cinema_api.model.Avaliacao;
import com.fiap.cinema_api.model.Filme;
import com.fiap.cinema_api.model.Usuario;
import com.fiap.cinema_api.service.AvaliacaoService;
import com.fiap.cinema_api.service.FilmeService;
import com.fiap.cinema_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public List<AvaliacaoResponse> listar() {
        return service.listarTodas().stream()
                .map(av -> new AvaliacaoResponse(
                        av.getId(),
                        av.getNota(),
                        av.getComentario(),
                        av.getData(),
                        new com.fiap.cinema_api.dto.UsuarioResponse(
                                av.getUsuario().getId(),
                                av.getUsuario().getNome(),
                                av.getUsuario().getEmail(),
                                av.getUsuario().getDataCriacao()
                        ),
                        new com.fiap.cinema_api.dto.FilmeResponse(
                                av.getFilme().getId(),
                                av.getFilme().getTitulo(),
                                av.getFilme().getDiretor(),
                                av.getFilme().getGenero(),
                                av.getFilme().getAnoLancamento(),
                                av.getFilme().getDuracaoMin()
                        )
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public AvaliacaoResponse buscar(@PathVariable Long id) {
        Avaliacao av = service.buscarPorId(id);
        return new AvaliacaoResponse(
                av.getId(),
                av.getNota(),
                av.getComentario(),
                av.getData(),
                new com.fiap.cinema_api.dto.UsuarioResponse(
                        av.getUsuario().getId(),
                        av.getUsuario().getNome(),
                        av.getUsuario().getEmail(),
                        av.getUsuario().getDataCriacao()
                ),
                new com.fiap.cinema_api.dto.FilmeResponse(
                        av.getFilme().getId(),
                        av.getFilme().getTitulo(),
                        av.getFilme().getDiretor(),
                        av.getFilme().getGenero(),
                        av.getFilme().getAnoLancamento(),
                        av.getFilme().getDuracaoMin()
                )
        );
    }

    @PostMapping
    public AvaliacaoResponse criar(@RequestBody @Valid AvaliacaoRequest request) {
        Usuario usuario = usuarioService.buscarPorId(request.usuarioId());
        Filme filme = filmeService.buscarPorId(request.filmeId());
        Avaliacao av = new Avaliacao(null, request.nota(), request.comentario(), null, usuario, filme);
        Avaliacao salvo = service.criar(av);
        return new AvaliacaoResponse(
                salvo.getId(),
                salvo.getNota(),
                salvo.getComentario(),
                salvo.getData(),
                new com.fiap.cinema_api.dto.UsuarioResponse(
                        salvo.getUsuario().getId(),
                        salvo.getUsuario().getNome(),
                        salvo.getUsuario().getEmail(),
                        salvo.getUsuario().getDataCriacao()
                ),
                new com.fiap.cinema_api.dto.FilmeResponse(
                        salvo.getFilme().getId(),
                        salvo.getFilme().getTitulo(),
                        salvo.getFilme().getDiretor(),
                        salvo.getFilme().getGenero(),
                        salvo.getFilme().getAnoLancamento(),
                        salvo.getFilme().getDuracaoMin()
                )
        );
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
