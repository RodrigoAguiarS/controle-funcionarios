package com.hordealianca.api.services.impl;

import com.hordealianca.api.model.TipoContrato;
import com.hordealianca.api.model.form.TipoContratoForm;
import com.hordealianca.api.model.response.TipoContratoResponse;
import com.hordealianca.api.repository.TipoContratoRepository;
import com.hordealianca.api.services.CrudServiceImpl;
import com.hordealianca.api.services.ModelMapperService;
import org.springframework.stereotype.Service;


@Service
public class TipoContratoServiceImpl extends CrudServiceImpl<TipoContrato, TipoContratoForm, TipoContratoResponse> {

    private final ModelMapperService modelMapperService;
    protected TipoContratoServiceImpl(TipoContratoRepository tipoContratoRepository, ModelMapperService modelMapperService) {
        super(tipoContratoRepository);
        this.modelMapperService = modelMapperService;
    }

    @Override
    protected void ativar(TipoContrato tipoContrato) { tipoContrato.ativar(); }

    @Override
    protected void desativar(TipoContrato tipoContrato) {
        tipoContrato.desativar();
    }

    @Override
    protected TipoContrato montarEntidade(TipoContratoForm tipoContratoForm, Long id) {
        TipoContrato tipoContrato = modelMapperService.converterParaEntidade(
                tipoContratoForm, TipoContrato.class);
        if(id != null) {
            tipoContrato.setId(id);
        }
        return tipoContrato;
    }

    @Override
    protected TipoContratoResponse montarDto(TipoContrato tipoContrato) {
        return modelMapperService.converterParaDto(tipoContrato, TipoContratoResponse.class);
    }
}
