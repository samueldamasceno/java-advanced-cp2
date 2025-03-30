package com.fiap.cinema_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @NotBlank String senha
) {}
