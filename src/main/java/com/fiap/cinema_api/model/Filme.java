package com.fiap.cinema_api.model;

import jakarta.persistence.*;

@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String diretor;
    private String genero;
    private Integer anoLancamento;
    private Integer duracaoMin;

}
