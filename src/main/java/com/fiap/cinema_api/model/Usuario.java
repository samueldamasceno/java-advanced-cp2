package com.fiap.cinema_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private LocalDate dataCriacao = LocalDate.now();

}
