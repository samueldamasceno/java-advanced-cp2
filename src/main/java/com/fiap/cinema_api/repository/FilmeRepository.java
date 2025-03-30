package com.fiap.cinema_api.repository;

import com.fiap.cinema_api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

    List<Filme> findByTituloContainingIgnoreCase(String titulo);

}
