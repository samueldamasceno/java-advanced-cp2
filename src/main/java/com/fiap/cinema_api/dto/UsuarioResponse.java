package com.fiap.cinema_api.dto;

import java.time.LocalDate;
import java.util.List;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        List<AvaliacaoResumidaResponse> avaliacoes
) {}