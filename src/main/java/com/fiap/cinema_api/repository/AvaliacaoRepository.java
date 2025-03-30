package com.fiap.cinema_api.repository;

import com.fiap.cinema_api.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByFilmeId(Long filmeId);
    List<Avaliacao> findByUsuarioId(Long usuarioId);

}
