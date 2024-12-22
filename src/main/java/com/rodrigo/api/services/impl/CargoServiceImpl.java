package com.rodrigo.api.services.impl;

import com.rodrigo.api.model.Cargo;
import com.rodrigo.api.model.form.CargoForm;
import com.rodrigo.api.model.response.CargoResponse;
import com.rodrigo.api.repository.CargoRepository;
import com.rodrigo.api.services.CrudServiceImpl;
import com.rodrigo.api.services.ModelMapperService;
import org.springframework.stereotype.Service;


@Service
public class CargoServiceImpl extends CrudServiceImpl<Cargo, CargoForm, CargoResponse> {

    private final ModelMapperService modelMapperService;

    protected CargoServiceImpl(CargoRepository cargoRepository, ModelMapperService modelMapperService) {
        super(cargoRepository);
        this.modelMapperService = modelMapperService;
    }

    @Override
    protected void ativar(Cargo cargo) { cargo.ativar(); }

    @Override
    protected void desativar(Cargo cargo) {
        cargo.desativar();
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
