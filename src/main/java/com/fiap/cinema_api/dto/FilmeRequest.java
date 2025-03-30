package com.fiap.cinema_api.dto;

import jakarta.validation.constraints.NotBlank;

public record FilmeRequest(
        @NotBlank String titulo,
        String diretor,
        @NotBlank String genero,
        Integer anoLancamento,
        Integer duracaoMin
) {}
