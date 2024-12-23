package com.rodrigo.api;

import com.rodrigo.api.model.Funcionario;
import com.rodrigo.api.model.Jornada;
import com.rodrigo.api.model.Ponto;
import com.rodrigo.api.model.TipoContrato;
import com.rodrigo.api.model.TipoPonto;
import com.rodrigo.api.model.response.ResumoJornadaDTO;
import com.rodrigo.api.repository.FuncionarioRepository;
import com.rodrigo.api.repository.JornadaRepository;
import com.rodrigo.api.services.JornadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class JornadaServiceTest {

    @Mock
    private JornadaRepository jornadaRepository;

    @InjectMocks
    private JornadaService jornadaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarOuCriarJornada() {
        Long funcionarioId = 1L;
        LocalDate data = LocalDate.now();
        Jornada jornada = new Jornada();
        when(jornadaRepository.findByFuncionarioIdAndData(funcionarioId, data)).thenReturn(Optional.of(jornada));

        Jornada result = jornadaService.buscarOuCriarJornada(funcionarioId, data);

        assertNotNull(result);
        verify(jornadaRepository, times(1)).findByFuncionarioIdAndData(funcionarioId, data);
    }

    @Test
    void testAtualizarResumoJornada() {
        // Configuração inicial
        Long jornadaId = 1L;
        Long funcionarioId = 1L;
        Jornada jornada = new Jornada();
        jornada.setId(jornadaId);
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioId);
        TipoContrato tipoContrato = new TipoContrato();
        tipoContrato.setHorasDia(8);
        funcionario.setTipoContrato(tipoContrato);
        jornada.setFuncionario(funcionario);

        // Mockando dependências
        when(jornadaRepository.findById(jornadaId)).thenReturn(Optional.of(jornada));
        when(jornadaRepository.save(any(Jornada.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Criando pontos
        Ponto pontoEntrada = new Ponto();
        pontoEntrada.setTipo(TipoPonto.ENTRADA);
        pontoEntrada.setJornada(jornada);
        pontoEntrada.setFuncionario(funcionario);
        pontoEntrada.setDataHora(LocalDateTime.now().minusHours(9));

        Ponto pontoSaida = new Ponto();
        pontoSaida.setTipo(TipoPonto.SAIDA);
        pontoSaida.setJornada(jornada);
        pontoSaida.setFuncionario(funcionario);
        pontoSaida.setDataHora(LocalDateTime.now());

        List<Ponto> pontos = List.of(pontoEntrada, pontoSaida);

        // Chamando o método de teste
        Jornada result = jornadaService.atualizarResumoJornada(jornadaId, pontos);

        // Verificações
        assertNotNull(result, "A Jornada retornada não deve ser nula");
        assertEquals(Duration.ofHours(9), result.getHorasTrabalhadas(), "As horas trabalhadas devem ser 9 horas");
        verify(jornadaRepository, times(1)).findById(jornadaId);
        verify(jornadaRepository, times(1)).save(any(Jornada.class));
    }

    @Test
    void testGetResumoJornadaFuncionario() {
        Long funcionarioId = 1L;
        LocalDate data = LocalDate.now();
        Jornada jornada = new Jornada();
        Funcionario funcionario = new Funcionario();
        TipoContrato tipoContrato = new TipoContrato();
        tipoContrato.setHorasDia(8);
        funcionario.setTipoContrato(tipoContrato);
        jornada.setFuncionario(funcionario);
        jornada.setHorasTrabalhadas(Duration.ZERO);
        jornada.setHorasExtras(Duration.ZERO);
        jornada.setHorasRestantes(Duration.ofHours(8));
        when(jornadaRepository.findByFuncionarioIdAndData(funcionarioId, data)).thenReturn(Optional.of(jornada));

        ResumoJornadaDTO result = jornadaService.getResumoJornadaFuncionario(funcionarioId, data);

        assertNotNull(result);
        verify(jornadaRepository, times(1)).findByFuncionarioIdAndData(funcionarioId, data);
    }

    @Test
    void testCalcularHorasTrabalhadas() {
        Ponto pontoEntrada = new Ponto();
        pontoEntrada.setTipo(TipoPonto.ENTRADA);
        pontoEntrada.setDataHora(LocalDateTime.now().minusHours(9));
        Ponto pontoSaida = new Ponto();
        pontoSaida.setTipo(TipoPonto.SAIDA);
        pontoSaida.setDataHora(LocalDateTime.now());
        List<Ponto> pontos = List.of(pontoEntrada, pontoSaida);

        Duration result = jornadaService.calcularHorasTrabalhadas(pontos);

        assertEquals(Duration.ofHours(9), result);
    }
}