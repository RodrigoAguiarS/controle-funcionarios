package com.rodrigo.api.services;

import com.rodrigo.api.exception.MensagensError;
import com.rodrigo.api.exception.ObjetoNaoEncontradoException;
import com.rodrigo.api.model.Funcionario;
import com.rodrigo.api.model.Jornada;
import com.rodrigo.api.model.Ponto;
import com.rodrigo.api.model.TipoPonto;
import com.rodrigo.api.model.response.PontoDTO;
import com.rodrigo.api.model.response.ResumoJornadaDTO;
import com.rodrigo.api.repository.FuncionarioRepository;
import com.rodrigo.api.repository.JornadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JornadaService {

    private final JornadaRepository jornadaRepository;
    private final FuncionarioRepository funcionarioRepository;

    /**
     * Busca ou cria uma jornada para um funcionário numa determinada data.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @param data         Data da jornada.
     * @return Jornada.
     */
    public Jornada buscarOuCriarJornada(Long funcionarioId, LocalDate data) {
        return jornadaRepository.findByFuncionarioIdAndData(funcionarioId, data)
                .orElseGet(() -> criarJornada(funcionarioId, data));
    }

    /**
     * Cria uma jornada para um funcionário numa determinada data.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @param data         Data da jornada.
     * @return Jornada.
     */
    private Jornada criarJornada(Long funcionarioId, LocalDate data) {
        Jornada novaJornada = new Jornada();
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(MensagensError.
                        FUNCIONARIO_NAO_ENCONTRADO_POR_ID.getMessage(funcionarioId)));
        novaJornada.setFuncionario(funcionario);
        novaJornada.setData(data);
        novaJornada.setHorasTrabalhadas(Duration.ZERO);
        novaJornada.setHorasExtras(Duration.ZERO);
        novaJornada.setHorasRestantes(Duration.ofHours(funcionario.getTipoContrato().getHorasDia()));

        return jornadaRepository.save(novaJornada);
    }

    /**
     * Atualiza o resumo de uma jornada.
     *
     * @param jornadaId ‘ID’ da jornada.
     * @param pontos    Lista de pontos.
     * @return Jornada.
     */
    public Jornada atualizarResumoJornada(Long jornadaId, List<Ponto> pontos) {
        Jornada jornada = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException(MensagensError.
                        JORNADA_NAO_ENCONTRADO_POR_ID.getMessage(jornadaId)));

        Duration horasTrabalhadas = calcularHorasTrabalhadas(pontos);
        Duration horasExtras = horasTrabalhadas.minus(Duration.ofHours(jornada.getFuncionario().getTipoContrato().getHorasDia()));
        Duration horasRestantes = Duration.ofHours(jornada.getFuncionario().getTipoContrato().getHorasDia()).minus(horasTrabalhadas);

        jornada.setHorasTrabalhadas(horasTrabalhadas);
        jornada.setHorasExtras(horasExtras.isNegative() ? Duration.ZERO : horasExtras);
        jornada.setHorasRestantes(horasRestantes.isNegative() ? Duration.ZERO : horasRestantes);

        return jornadaRepository.save(jornada);
    }

    /**
     * Calcula as horas trabalhadas a partir de uma lista de pontos.
     *
     * @param pontos Lista de pontos.
     * @return Duração.
     */
    private Duration calcularHorasTrabalhadas(List<Ponto> pontos) {
        Duration total = Duration.ZERO;
        Ponto pontoEntrada = null;

        List<Ponto> pontosOrdenados = pontos.stream()
                .sorted(Comparator.comparing(Ponto::getDataHora))
                .toList();

        for (Ponto ponto : pontosOrdenados) {
            if (ponto.getTipo() == TipoPonto.ENTRADA) {
                pontoEntrada = ponto;
            } else if (pontoEntrada != null && ponto.getTipo() == TipoPonto.SAIDA) {
                total = total.plus(Duration.between(pontoEntrada.getDataHora(), ponto.getDataHora()));
                pontoEntrada = null;
            }
        }

        return total;
    }

    /**
     * Retorna o resumo de uma jornada.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @param data         Data da jornada.
     * @return Resumo da jornada.
     */
    @Transactional
    public ResumoJornadaDTO getResumoJornadaFuncionario(Long funcionarioId, LocalDate data) {
        Jornada jornada = buscarOuCriarJornada(funcionarioId, data);

        boolean jornadaCompleta = jornada.getHorasRestantes().isZero() || jornada.getHorasRestantes().isNegative();
        Duration horasExtras = jornada.getHorasExtras();
        Duration horasRestantes = jornada.getHorasRestantes();

        List<PontoDTO> pontos = jornada.getPontos().stream()
                .sorted(Comparator.comparing(Ponto::getDataHora))
                .map(ponto -> new PontoDTO(ponto.getId(), ponto.getDataHora(), ponto.getObservacao(), ponto.getTipo().name()))
                .collect(Collectors.toList());

        return new ResumoJornadaDTO(data, pontos, jornadaCompleta, horasRestantes, horasExtras);
    }

    /**
     * Retorna o resumo total de todas as jornadas de um funcionário.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @return Resumo total das jornadas.
     */
    @Transactional
    public ResumoJornadaDTO getResumoTotalJornadasFuncionario(Long funcionarioId) {
        List<Jornada> jornadas = jornadaRepository.findByFuncionarioId(funcionarioId);

        Duration totalHorasTrabalhadas = Duration.ZERO;
        Duration totalHorasExtras = Duration.ZERO;
        Duration totalHorasRestantes = Duration.ZERO;

        for (Jornada jornada : jornadas) {
            totalHorasTrabalhadas = totalHorasTrabalhadas.plus(jornada.getHorasTrabalhadas());
            totalHorasExtras = totalHorasExtras.plus(jornada.getHorasExtras());
            totalHorasRestantes = totalHorasRestantes.plus(jornada.getHorasRestantes());
        }

        return new ResumoJornadaDTO(totalHorasTrabalhadas, totalHorasExtras, totalHorasRestantes);
    }
}
