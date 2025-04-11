package com.fiap.cinema_api.dto;

import java.util.List;

public record FilmeResponse(
        Long id,
        String titulo,
        String diretor,
        String genero,
        Integer anoLancamento,
        Integer duracaoMin,
        List<AvaliacaoResumidaResponse> avaliacoes
) {}