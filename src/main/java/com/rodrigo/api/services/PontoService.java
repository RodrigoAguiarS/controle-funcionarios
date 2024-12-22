package com.rodrigo.api.services;

import com.rodrigo.api.exception.MensagensError;
import com.rodrigo.api.exception.PontoNaoEncontradoException;
import com.rodrigo.api.model.Funcionario;
import com.rodrigo.api.model.Jornada;
import com.rodrigo.api.model.Ponto;
import com.rodrigo.api.model.TipoPonto;
import com.rodrigo.api.repository.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoRepository pontoRepository;
    private final JornadaService jornadaService;

    /**
     * Registra um ponto para um funcionário.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @return Ponto.
     */
    public Ponto registrarPonto(Long funcionarioId) {
        LocalDate hoje = LocalDate.now();
        Jornada jornada = jornadaService.buscarOuCriarJornada(funcionarioId, hoje);
        Funcionario funcionario = jornada.getFuncionario();

        TipoPonto proximoTipo = determinarProximoTipo(jornada.getId());
        Ponto ponto = new Ponto();
        ponto.setDataHora(LocalDateTime.now());
        ponto.setTipo(proximoTipo);
        ponto.setFuncionario(funcionario);
        ponto.setJornada(jornada);
        atualizarJornadaAposRegistrarPonto(jornada, ponto);
        return pontoRepository.save(ponto);
    }

    /**
     * Determina o próximo tipo de ponto a ser registrado.
     *
     * @param jornadaId ‘ID’ da jornada.
     * @return TipoPonto.
     */
    private TipoPonto determinarProximoTipo(Long jornadaId) {
        List<Ponto> pontos = pontoRepository.findByJornadaIdOrderByDataHoraAsc(jornadaId);

        if (pontos.isEmpty()) {
            return TipoPonto.ENTRADA;
        }

        Ponto ultimoPonto = pontos.get(pontos.size() - 1);
        if (ultimoPonto.getTipo() == TipoPonto.ENTRADA) {
            return TipoPonto.SAIDA;
        } else {
            return TipoPonto.ENTRADA;
        }
    }

    /**
     * Lista os pontos de uma jornada.
     *
     * @param jornadaId ‘ID’ da jornada.
     * @return Lista de pontos.
     */
    public List<Ponto> listarPontosPorJornada(Long jornadaId) {
        return pontoRepository.findByJornadaId(jornadaId);
    }

    /**
     * Atualiza a jornada após registrar um ponto.
     *
     * @param jornada Jornada.
     * @param ponto   Ponto.
     */
    private void atualizarJornadaAposRegistrarPonto(Jornada jornada, Ponto ponto) {
        List<Ponto> pontos = pontoRepository.findByJornadaId(jornada.getId());

        boolean pontoExistente = pontos.stream()
                .anyMatch(p -> p.getId().equals(ponto.getId()));

        if (pontoExistente) {
            pontos = pontos.stream()
                    .map(p -> p.getId().equals(ponto.getId()) ? ponto : p)
                    .collect(Collectors.toList());
        } else {
            pontos.add(ponto);
        }

        jornadaService.atualizarResumoJornada(jornada.getId(), pontos);
    }

    /**
     * Obtém o último tipo de ponto registrado.
     *
     * @param funcionarioId ‘ID’ do funcionário.
     * @return TipoPonto.
     */
    public TipoPonto obterUltimoTipoPonto(Long funcionarioId) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDia = hoje.atStartOfDay();
        LocalDateTime fimDoDia = hoje.atTime(23, 59, 59);

        Ponto ultimoPontoDoDia = (Ponto) pontoRepository.findTopByFuncionarioIdAndDataHoraBetweenOrderByDataHoraDesc(funcionarioId, inicioDoDia, fimDoDia)
                .orElseThrow(() -> new PontoNaoEncontradoException(MensagensError.PONTO_NAO_ENCONTRADO.getMessage()));

        if (ultimoPontoDoDia != null) {
            return ultimoPontoDoDia.getTipo();
        }

        return TipoPonto.ENTRADA;
    }

    /**
     * Edita um ponto.
     *
     * @param pontoId     ‘ID’ do ponto.
     * @param novaDataHora Nova data e hora.
     * @param novoTipoPonto Novo tipo de ponto.
     * @return Ponto.
     */
    public Ponto editarPonto(Long pontoId, LocalDateTime novaDataHora, TipoPonto novoTipoPonto) {
        Ponto ponto = pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNaoEncontradoException(MensagensError.PONTO_NAO_ENCONTRADO.getMessage(pontoId)));

        ponto.setDataHora(novaDataHora);
        ponto.setTipo(novoTipoPonto);
        atualizarJornadaAposRegistrarPonto(ponto.getJornada(), ponto);
        return pontoRepository.save(ponto);
    }

    /**
     * Obtém um ponto por ‘ID’.
     *
     * @param pontoId ‘ID’ do ponto.
     * @return Ponto.
     */
    public Ponto obterPontoPorId(Long pontoId) {
        return pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNaoEncontradoException(MensagensError.PONTO_NAO_ENCONTRADO.getMessage(pontoId)));
    }

    /**
     * Apaga um ponto.
     *
     * @param pontoId ‘ID’ do ponto.
     */
    public void apagarPonto(Long pontoId) {
        Ponto ponto = pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNaoEncontradoException(MensagensError.PONTO_NAO_ENCONTRADO.getMessage(pontoId)));

        Jornada jornada = ponto.getJornada();
        pontoRepository.delete(ponto);

        List<Ponto> pontosAtualizados = pontoRepository.findByJornadaId(jornada.getId());
        jornadaService.atualizarResumoJornada(jornada.getId(), pontosAtualizados);
    }
}
