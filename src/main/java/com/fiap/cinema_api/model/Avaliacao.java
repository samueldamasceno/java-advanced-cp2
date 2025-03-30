package com.fiap.cinema_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota;
    private String comentario;
    private LocalDate data = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

}
