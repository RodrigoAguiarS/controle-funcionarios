package com.rodrigo.api.rest;

import com.rodrigo.api.model.Jornada;
import com.rodrigo.api.model.Ponto;
import com.rodrigo.api.model.response.ResumoJornadaDTO;
import com.rodrigo.api.services.JornadaService;
import com.rodrigo.api.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jornadas")
@RequiredArgsConstructor
public class JornadaController {

    private final JornadaService jornadaService;
    private final PontoService pontoService;

    @GetMapping("/{id}/{data}")
    public ResponseEntity<Jornada> buscarOuCriarJornada(
            @PathVariable Long id,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        Jornada jornada = jornadaService.buscarOuCriarJornada(id, data);
        return ResponseEntity.ok(jornada);
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<Jornada> atualizarResumo(@PathVariable Long id) {
        List<Ponto> pontos = pontoService.listarPontosPorJornada(id);
        Jornada jornada = jornadaService.atualizarResumoJornada(id, pontos);
        return ResponseEntity.ok(jornada);
    }

    @GetMapping("/resumo/{id}")
    public ResponseEntity<ResumoJornadaDTO> getResumoJornada(@PathVariable Long id, @RequestParam LocalDate data) {
        ResumoJornadaDTO resumo = jornadaService.getResumoJornadaFuncionario(id, data);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/resumo/total/{id}")
    public ResponseEntity<ResumoJornadaDTO> getResumoTotalJornadas(@PathVariable Long id) {
        ResumoJornadaDTO resumo = jornadaService.getResumoTotalJornadasFuncionario(id);
        return ResponseEntity.ok(resumo);
    }
}
