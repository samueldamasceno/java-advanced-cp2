package com.fiap.cinema_api.dto;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        LocalDate dataCriacao
) {}
