package com.hordealianca.api.rest;

import com.hordealianca.api.model.Jornada;
import com.hordealianca.api.model.Ponto;
import com.hordealianca.api.model.response.ResumoJornadaDTO;
import com.hordealianca.api.services.JornadaService;
import com.hordealianca.api.services.PontoService;
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
        ResumoJornadaDTO resumo = jornadaService.getResumoJornada(id, data);
        return ResponseEntity.ok(resumo);
    }
}
