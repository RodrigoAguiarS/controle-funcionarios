package com.hordealianca.api.services.impl;

import com.hordealianca.api.model.Perfil;
import com.hordealianca.api.model.form.PerfilForm;
import com.hordealianca.api.model.response.PerfilResponse;
import com.hordealianca.api.repository.PerfilRepository;
import com.hordealianca.api.services.CrudServiceImpl;
import com.hordealianca.api.services.ModelMapperService;
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
