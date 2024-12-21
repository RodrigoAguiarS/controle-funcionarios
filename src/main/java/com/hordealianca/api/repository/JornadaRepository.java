package com.hordealianca.api.repository;

import com.hordealianca.api.model.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {
    Optional<Jornada> findByFuncionarioIdAndData(Long funcionarioId, LocalDate data);
}
