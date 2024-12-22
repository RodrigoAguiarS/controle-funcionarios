package com.rodrigo.api.repository;

import com.rodrigo.api.model.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    /**
     * Busca uma jornada por funcionário e data
     * @param funcionarioId id do funcionário
     * @param data data
     * @return jornada
     */
    Optional<Jornada> findByFuncionarioIdAndData(Long funcionarioId, LocalDate data);

    /**
     * Busca todas as jornadas de um funcionário
     * @param funcionarioId id do funcionário
     * @return jornadas
     */
    List<Jornada> findByFuncionarioId(Long funcionarioId);
}
