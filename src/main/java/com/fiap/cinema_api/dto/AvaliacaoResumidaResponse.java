package com.fiap.cinema_api.dto;

import java.time.LocalDate;

public record AvaliacaoResumidaResponse(
        Long id,
        Double nota,
        String comentario
) {}