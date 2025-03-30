package com.fiap.cinema_api.dto;

import java.time.LocalDate;

public record AvaliacaoResponse (
        Long id,
        Double nota,
        String comentario,
        LocalDate data,
        UsuarioResponse usuario,
        FilmeResponse filme
) {}
