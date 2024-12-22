package com.rodrigo.api.rest;

import com.rodrigo.api.services.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Implementação padrão de um controlador CRUD
 *
 * @param <Form>     Formulário de entrada
 * @param <Response> Resposta de saída
 */
public abstract class CrudControllerImpl<Form, Response> implements CrudController<Form, Response> {

    protected final CrudService<Form, Response> service;

    protected CrudControllerImpl(CrudService<Form, Response> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Response> create(@RequestBody Form form) {
        Response response = service.criar(form);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Form form) {
        Response response = service.atualizar(id, form);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        Response response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<Response>> getAll() {
        List<Response> responses = service.listarTodos();
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}