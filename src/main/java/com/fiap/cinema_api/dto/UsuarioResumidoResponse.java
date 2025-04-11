package com.fiap.cinema_api.dto;

public record UsuarioResumidoResponse(
        Long id,
        String nome,
        String email
) {}