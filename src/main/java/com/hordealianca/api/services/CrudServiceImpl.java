package com.hordealianca.api.services;

import com.hordealianca.api.exception.MensagensError;
import com.hordealianca.api.exception.ObjetoNaoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Classe responsável por implementar os métodos de um serviço CRUD
 * @param <Entity> Entidade
 * @param <Form> Formulário
 * @param <Response> DTO
 */
public abstract class CrudServiceImpl<Entity, Form, Response> implements CrudService<Form, Response> {

    protected final JpaRepository<Entity, Long> repository;

    protected CrudServiceImpl(JpaRepository<Entity, Long> repository) {
        this.repository = repository;
    }

    /**
     * Método responsável por montar a entidade a partir do formulário
     * @param form Formulário
     * @param id Id da entidade
     * @return Entidade
     */
    protected abstract Entity montarEntidade(Form form, Long id);

    /**
     * Método responsável por montar o DTO a partir da entidade
     * @param entity Entidade
     * @return DTO
     */
    protected abstract Response montarDto(Entity entity);

    /**
     * Método responsável por ativar a entidade
     * @param entity Entidade
     */
    protected abstract void ativar(Entity entity);

    /**
     * Método responsável por desativar a entidade
     * @param entity Entidade
     */
    protected abstract void desativar(Entity entity);


    /**
     * Método responsável por criar uma entidade
     * @param form Formulário
     * @return DTO
     */
    @Override
    public Response criar(Form form) {
        Entity entity = montarEntidade(form, null);
        entity = repository.save(entity);
        return montarDto(entity);
    }

    /**
     * Método responsável por atualizar uma entidade
     * @param id Id da entidade
     * @param form Formulário
     * @return DTO
     */
    @Override
    public Response atualizar(Long id, Form form) {
        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            Entity entity = montarEntidade(form, id);
            entity = repository.save(entity);
            return montarDto(entity);
        }
        throw new ObjetoNaoEncontradoException(MensagensError.ENTIDADE_NAO_ENCONTRADO.getMessage());
    }

    /**
     * Método responsável por obter uma entidade por ‘id’
     * @param id Id da entidade
     * @return DTO
     */
    @Override
    public Response obterPorId(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            return montarDto(optionalEntity.get());
        }
        throw new ObjetoNaoEncontradoException(MensagensError.ENTIDADE_NAO_ENCONTRADO.getMessage());
    }

    /**
     * Método responsável por listar todas as entidades
     * @return Lista de DTO
     */
    @Override
    public List<Response> listarTodos() {
        List<Entity> entities = repository.findAll();
        return entities.stream().map(this::montarDto).toList();
    }

    /**
     * Método responsável por apagar uma entidade
     * @param id Id da entidade
     */
    @Override
    public void apagar(Long id) {
        repository.deleteById(id);
    }

    /**
     * Método responsável por ativar uma entidade
     * @param id Id da entidade
     */
    @Override
    public void ativar(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            Entity entity = optionalEntity.get();
            ativar(entity);
            repository.save(entity);
        } else {
            throw new ObjetoNaoEncontradoException(MensagensError.ENTIDADE_NAO_ENCONTRADO.getMessage());
        }
    }

    /**
     * Método responsável por desativar uma entidade
     * @param id Id da entidade
     */
    @Override
    public void desativar(Long id) {
        Optional<Entity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            Entity entity = optionalEntity.get();
            desativar(entity);
            repository.save(entity);
        } else {
            throw new ObjetoNaoEncontradoException(MensagensError.ENTIDADE_NAO_ENCONTRADO.getMessage());
        }
    }
}
