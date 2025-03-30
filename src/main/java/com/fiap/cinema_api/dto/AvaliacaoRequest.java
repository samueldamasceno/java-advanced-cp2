package com.fiap.cinema_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AvaliacaoRequest (
        @Min(0) @Max(10) Double nota,
        @Size(max = 500) String comentario,
        Long usuarioId,
        Long filmeId
) {}
