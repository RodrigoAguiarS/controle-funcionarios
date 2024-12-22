package com.rodrigo.api.services.impl;

import com.rodrigo.api.exception.CargoNaoEncontradoException;
import com.rodrigo.api.exception.MensagensError;
import com.rodrigo.api.exception.ObjetoNaoEncontradoException;
import com.rodrigo.api.exception.ViolacaoIntegridadeDadosException;
import com.rodrigo.api.model.Perfil;
import com.rodrigo.api.model.form.PerfilForm;
import com.rodrigo.api.model.response.PerfilResponse;
import com.rodrigo.api.repository.PerfilRepository;
import com.rodrigo.api.repository.UsuarioRepository;
import com.rodrigo.api.services.CrudServiceImpl;
import com.rodrigo.api.services.ModelMapperService;
import org.springframework.stereotype.Service;

@Service
public class PerfilServiceImpl extends CrudServiceImpl<Perfil, PerfilForm, PerfilResponse> {

    private final ModelMapperService modelMapperService;

    private final UsuarioRepository usuarioRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository, ModelMapperService modelMapperService, UsuarioRepository usuarioRepository) {
        super(perfilRepository);
        this.modelMapperService = modelMapperService;
        this.usuarioRepository = usuarioRepository;
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
    protected void validarExclusao(Long id) {
        boolean existeUsuario = usuarioRepository.existsByPerfisId(id);
        if (existeUsuario) {
            throw new ViolacaoIntegridadeDadosException(MensagensError.PERFIL_POSSUI_USUARIO.getMessage());
        }
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
