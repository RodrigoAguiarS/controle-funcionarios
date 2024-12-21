package com.hordealianca.api.rest;

import com.hordealianca.api.model.Ponto;
import com.hordealianca.api.model.TipoPonto;
import com.hordealianca.api.model.form.PontoForm;
import com.hordealianca.api.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pontos")
@RequiredArgsConstructor
public class PontoController {

    private final PontoService pontoService;

    @PostMapping
    public ResponseEntity<Ponto> registrarPonto(@RequestParam Long funcionarioId) {
        Ponto ponto = pontoService.registrarPonto(funcionarioId);
        return ResponseEntity.ok(ponto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ponto> listarPontos(@PathVariable Long id) {
        Ponto ponto = pontoService.obterPontoPorId(id);
        return ResponseEntity.ok(ponto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarPonto(@PathVariable Long id) {
        pontoService.apagarPonto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ultimoTipoPonto/{id}")
    public ResponseEntity<TipoPonto> obterUltimoTipoPonto(@PathVariable Long id) {
        TipoPonto tipoPonto = pontoService.obterUltimoTipoPonto(id);
        return ResponseEntity.ok(tipoPonto);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Ponto> editarPonto(@PathVariable Long id, @RequestBody PontoForm pontoForm) {
        Ponto pontoEditado = pontoService.editarPonto(id, pontoForm.getNovaDataHora(), pontoForm.getTipo());
        return ResponseEntity.ok(pontoEditado);
    }
}
