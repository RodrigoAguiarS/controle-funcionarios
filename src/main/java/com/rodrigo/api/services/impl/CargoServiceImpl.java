package com.rodrigo.api.services.impl;

import com.rodrigo.api.exception.CargoNaoEncontradoException;
import com.rodrigo.api.exception.MensagensError;
import com.rodrigo.api.exception.ObjetoNaoEncontradoException;
import com.rodrigo.api.exception.ViolacaoIntegridadeDadosException;
import com.rodrigo.api.model.Cargo;
import com.rodrigo.api.model.form.CargoForm;
import com.rodrigo.api.model.response.CargoResponse;
import com.rodrigo.api.repository.CargoRepository;
import com.rodrigo.api.repository.FuncionarioRepository;
import com.rodrigo.api.services.CrudServiceImpl;
import com.rodrigo.api.services.ModelMapperService;
import org.springframework.stereotype.Service;


@Service
public class CargoServiceImpl extends CrudServiceImpl<Cargo, CargoForm, CargoResponse> {

    private final ModelMapperService modelMapperService;

    private final FuncionarioRepository funcionarioRepository;

    protected CargoServiceImpl(CargoRepository cargoRepository, ModelMapperService modelMapperService,
                               FuncionarioRepository funcionarioRepository) {
        super(cargoRepository);
        this.funcionarioRepository = funcionarioRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    protected void ativar(Cargo cargo) { cargo.ativar(); }

    @Override
    protected void desativar(Cargo cargo) {
        cargo.desativar();
    }

    @Override
    protected void validarExclusao(Long id) {
        boolean existeFuncionario = funcionarioRepository.existsByCargoId(id);
        if (existeFuncionario) {
            throw new ViolacaoIntegridadeDadosException(MensagensError.CARGO_POSSUI_FUNCIONARIO.getMessage());
        }
    }

    @Override
    protected Cargo montarEntidade(CargoForm cargoForm, Long id) {
        Cargo cargo = modelMapperService.converterParaEntidade(cargoForm, Cargo.class);
        if (id != null) {
            cargo.setId(id);
        }
        return cargo;
    }

    @Override
    protected CargoResponse montarDto(Cargo cargo) {
        return modelMapperService.converterParaDto(cargo, CargoResponse.class);
    }
}
