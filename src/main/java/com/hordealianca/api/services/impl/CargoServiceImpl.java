package com.hordealianca.api.services.impl;

import com.hordealianca.api.model.Cargo;
import com.hordealianca.api.model.form.CargoForm;
import com.hordealianca.api.model.response.CargoResponse;
import com.hordealianca.api.repository.CargoRepository;
import com.hordealianca.api.services.CrudServiceImpl;
import com.hordealianca.api.services.ModelMapperService;
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
