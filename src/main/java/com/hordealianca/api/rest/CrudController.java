package com.hordealianca.api.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Implementação padrão de um controlador CRUD
 *
 * @param <Form>     Formulário de entrada
 * @param <Response> Resposta de saída
 */
public interface CrudController<Form, Response> {
    @PostMapping
    ResponseEntity<Response> create(@Valid @RequestBody Form form);

    @PutMapping("/{id}")
    ResponseEntity<Response> update(@Valid @PathVariable Long id, @RequestBody Form form);

    @GetMapping("/{id}")
    ResponseEntity<Response> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Response>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping("/{id}/ativar")
    ResponseEntity<Void> ativar(@PathVariable Long id);

    @PutMapping("/{id}/desativar")
    ResponseEntity<Void> desativar(@PathVariable Long id);
}
