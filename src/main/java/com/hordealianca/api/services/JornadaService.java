package com.hordealianca.api.services;

import com.hordealianca.api.exception.FuncionarioNaoEncontradoException;
import com.hordealianca.api.exception.JornadaNaoEncontradoException;
import com.hordealianca.api.exception.MensagensError;
import com.hordealianca.api.model.Funcionario;
import com.hordealianca.api.model.Jornada;
import com.hordealianca.api.model.Ponto;
import com.hordealianca.api.model.TipoPonto;
import com.hordealianca.api.model.response.PontoDTO;
import com.hordealianca.api.model.response.ResumoJornadaDTO;
import com.hordealianca.api.repository.FuncionarioRepository;
import com.hordealianca.api.repository.JornadaRepository;
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

    public Jornada buscarOuCriarJornada(Long funcionarioId, LocalDate data) {
        return jornadaRepository.findByFuncionarioIdAndData(funcionarioId, data)
                .orElseGet(() -> criarJornada(funcionarioId, data));
    }

    private Jornada criarJornada(Long funcionarioId, LocalDate data) {
        Jornada novaJornada = new Jornada();
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException(MensagensError.
                        FUNCIONARIO_NAO_ENCONTRADO_POR_ID.getMessage(funcionarioId)));
        novaJornada.setFuncionario(funcionario);
        novaJornada.setData(data);
        novaJornada.setHorasTrabalhadas(Duration.ZERO);
        novaJornada.setHorasExtras(Duration.ZERO);
        novaJornada.setHorasRestantes(Duration.ofHours(funcionario.getTipoContrato().getHorasDia()));

        return jornadaRepository.save(novaJornada);
    }

    public Jornada atualizarResumoJornada(Long jornadaId, List<Ponto> pontos) {
        Jornada jornada = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new JornadaNaoEncontradoException(MensagensError.
                        JORNADA_NAO_ENCONTRADO_POR_ID.getMessage(jornadaId)));

        Duration horasTrabalhadas = calcularHorasTrabalhadas(pontos);
        Duration horasExtras = horasTrabalhadas.minus(Duration.ofHours(jornada.getFuncionario().getTipoContrato().getHorasDia()));
        Duration horasRestantes = Duration.ofHours(jornada.getFuncionario().getTipoContrato().getHorasDia()).minus(horasTrabalhadas);

        jornada.setHorasTrabalhadas(horasTrabalhadas);
        jornada.setHorasExtras(horasExtras.isNegative() ? Duration.ZERO : horasExtras);
        jornada.setHorasRestantes(horasRestantes.isNegative() ? Duration.ZERO : horasRestantes);

        return jornadaRepository.save(jornada);
    }

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

    @Transactional
    public ResumoJornadaDTO getResumoJornada(Long funcionarioId, LocalDate data) {
        Jornada jornada = jornadaRepository.findByFuncionarioIdAndData(funcionarioId, data)
                .orElseThrow(() -> new JornadaNaoEncontradoException(MensagensError.JORNADA_NAO_ENCONTRADO.getMessage()));

        boolean jornadaCompleta = jornada.getHorasRestantes().isZero() || jornada.getHorasRestantes().isNegative();
        Duration horasExtras = jornada.getHorasExtras();
        Duration horasRestantes = jornada.getHorasRestantes();

        List<PontoDTO> pontos = jornada.getPontos().stream()
                .sorted(Comparator.comparing(Ponto::getDataHora))
                .map(ponto -> new PontoDTO(ponto.getId(), ponto.getDataHora(), ponto.getTipo().name()))
                .collect(Collectors.toList());

        return new ResumoJornadaDTO(data, pontos, jornadaCompleta, horasRestantes, horasExtras);
    }
}
