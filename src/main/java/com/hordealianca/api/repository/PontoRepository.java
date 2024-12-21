package com.hordealianca.api.repository;

import com.hordealianca.api.model.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, Long> {
    /**
     * Busca todos os pontos de uma jornadan
     */
    List<Ponto> findByJornadaId(Long jornadaId);

    /**
     * Busca todos os pontos de uma jornada ordenados por data e hora
     */
    List<Ponto> findByJornadaIdOrderByDataHoraAsc(Long jornadaId);

    /**
     * Busca o último ponto de um funcionário num intervalo de tempo
     */
    Optional<Object> findTopByFuncionarioIdAndDataHoraBetweenOrderByDataHoraDesc(Long funcionarioId,
                                                                                 LocalDateTime inicioDoDia, LocalDateTime fimDoDia);
}
