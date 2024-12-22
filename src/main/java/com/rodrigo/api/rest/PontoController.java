package com.rodrigo.api.rest;

import com.rodrigo.api.model.Ponto;
import com.rodrigo.api.model.TipoPonto;
import com.rodrigo.api.model.form.PontoForm;
import com.rodrigo.api.services.PontoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pontos")
@RequiredArgsConstructor
public class PontoController {

    private final PontoService pontoService;

    @PostMapping
    public ResponseEntity<Ponto> registrarPonto(@Valid @RequestBody PontoForm registroPontoForm) {
        Ponto ponto = pontoService.registrarPonto(registroPontoForm);
        return ResponseEntity.ok(ponto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ponto> obterPontoPorId(@PathVariable Long id) {
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
    public ResponseEntity<Ponto> editarPonto(@Valid @PathVariable Long id, @RequestBody PontoForm pontoForm) {
        Ponto pontoEditado = pontoService.editarPonto(id, pontoForm);
        return ResponseEntity.ok(pontoEditado);
    }
}
