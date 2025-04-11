package com.fiap.cinema_api.dto;

import java.time.LocalDate;

public record AvaliacaoResponse(
        Long id,
        Double nota,
        String comentario,
        UsuarioResumidoResponse usuario,
        FilmeResumidoResponse filme
) {}