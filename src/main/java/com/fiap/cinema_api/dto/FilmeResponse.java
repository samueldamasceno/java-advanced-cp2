package com.fiap.cinema_api.dto;

public record FilmeResponse(
        Long id,
        String titulo,
        String diretor,
        String genero,
        Integer anoLancamento,
        Integer duracaoMin
) {}
