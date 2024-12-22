package com.rodrigo.api.services.impl;

import com.rodrigo.api.model.Perfil;
import com.rodrigo.api.model.form.PerfilForm;
import com.rodrigo.api.model.response.PerfilResponse;
import com.rodrigo.api.repository.PerfilRepository;
import com.rodrigo.api.services.CrudServiceImpl;
import com.rodrigo.api.services.ModelMapperService;
import org.springframework.stereotype.Service;

@Service
public class PerfilServiceImpl extends CrudServiceImpl<Perfil, PerfilForm, PerfilResponse> {

    private final ModelMapperService modelMapperService;

    public PerfilServiceImpl(PerfilRepository perfilRepository, ModelMapperService modelMapperService) {
        super(perfilRepository);
        this.modelMapperService = modelMapperService;
    }

    @Override
    protected void ativar(Perfil perfil) {
        perfil.ativar();
    }

    @Override
    protected void desativar(Perfil perfil) {
        perfil.desativar();
    }

    @Override
    protected Perfil montarEntidade(PerfilForm perfilForm, Long id) {
        Perfil perfil = modelMapperService.converterParaEntidade(perfilForm, Perfil.class);
        if(id != null) {
            perfil.setId(id);
        }
        return perfil;
    }

    @Override
    protected PerfilResponse montarDto(Perfil perfil) {
        return modelMapperService.converterParaDto(perfil, PerfilResponse.class);
    }
}
