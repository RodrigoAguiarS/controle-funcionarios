package com.hordealianca.api.services;

import java.util.List;

/**
 * ‘Interface’ responsável por definir os métodos de um serviço CRUD
 * @param <Form> Formulário
 * @param <Response> DTO
 */
public interface CrudService<Form, Response> {
    /**
     * Método responsável por criar uma entidade
     * @param form Formulário
     * @return DTO
     */
    Response criar(Form form);

    /**
     * Método responsável por atualizar uma entidade
     * @param id Id da entidade
     * @param form Formulário
     * @return DTO
     */
    Response atualizar(Long id, Form form);

    /**
     * Método responsável por obter uma entidade por ‘id’
     * @param id Id da entidade
     * @return DTO
     */
    Response obterPorId(Long id);

    /**
     * Método responsável por listar todas as entidades
     * @return Lista de DTO
     */
    List<Response> listarTodos();

    /**
     * Método responsável por apagar uma entidade
     * @param id Id da entidade
     */
    void apagar(Long id);

    /**
     * Método responsável por ativar uma entidade
     * @param id Id da entidade
     */
    void ativar(Long id);

    /**
     * Método responsável por desativar uma entidade
     * @param id Id da entidade
     */
    void desativar(Long id);
}
