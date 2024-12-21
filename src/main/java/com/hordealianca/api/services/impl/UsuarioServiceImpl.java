package com.hordealianca.api.services.impl;

import com.hordealianca.api.exception.MensagensError;
import com.hordealianca.api.exception.UsuarioNaoEncontradoException;
import com.hordealianca.api.model.Cargo;
import com.hordealianca.api.model.Endereco;
import com.hordealianca.api.model.Funcionario;
import com.hordealianca.api.model.Perfil;
import com.hordealianca.api.model.Pessoa;
import com.hordealianca.api.model.TipoContrato;
import com.hordealianca.api.model.Usuario;
import com.hordealianca.api.model.form.UsuarioForm;
import com.hordealianca.api.model.response.CargoResponse;
import com.hordealianca.api.model.response.PerfilResponse;
import com.hordealianca.api.model.response.TipoContratoResponse;
import com.hordealianca.api.model.response.UsuarioResponse;
import com.hordealianca.api.repository.UsuarioRepository;
import com.hordealianca.api.services.CrudServiceImpl;
import com.hordealianca.api.services.ModelMapperService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, UsuarioForm, UsuarioResponse> {

    private final PasswordEncoder passwordEncoder;
    private final PerfilServiceImpl perfilService;
    private final CargoServiceImpl cargoService;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapperService modelMapperService;
    private final TipoContratoServiceImpl tipoContratoService;

    public UsuarioServiceImpl(PasswordEncoder passwordEncoder, PerfilServiceImpl perfilService, CargoServiceImpl cargoService, UsuarioRepository usuarioRepository, ModelMapperService modelMapperService, TipoContratoServiceImpl tipoContratoService) {
        super(usuarioRepository);
        this.passwordEncoder = passwordEncoder;
        this.perfilService = perfilService;
        this.cargoService = cargoService;
        this.usuarioRepository = usuarioRepository;
        this.modelMapperService = modelMapperService;
        this.tipoContratoService = tipoContratoService;
    }

    @Override
    protected void ativar(Usuario usuario) {
        usuario.ativar();
    }

    @Override
    protected void desativar(Usuario usuario) {
        usuario.desativar();
    }

    @Override
    protected Usuario montarEntidade(UsuarioForm usuarioForm, Long id) {
        Usuario usuario;
        if (id != null) {
            usuario = repository.findById(id).orElseThrow(() ->
                    new UsuarioNaoEncontradoException(MensagensError.USUARIO_NAO_ENCONTRADO_POR_ID.getMessage(id)));
        } else {
            usuario = new Usuario();
        }

        modelMapperService.mapear(usuarioForm, usuario);
        usuario.setSenha(passwordEncoder.encode(usuarioForm.getSenha()));

        if (usuario.getPessoa() == null) {
            usuario.setPessoa(new Pessoa());
        }

        Pessoa pessoa = usuario.getPessoa();
        modelMapperService.mapear(usuarioForm, pessoa);

        if (pessoa.getEndereco() == null) {
            pessoa.setEndereco(new Endereco());
        }

        Endereco endereco = pessoa.getEndereco();
        modelMapperService.mapear(usuarioForm, endereco);
        pessoa.setEndereco(endereco);

        if (usuario.getFuncionario() == null) {
            usuario.setFuncionario(new Funcionario());
        }

        CargoResponse cargoResponse = cargoService.obterPorId(usuarioForm.getCargo());
        Cargo cargo = modelMapperService.converterParaEntidade(cargoResponse, Cargo.class);

        TipoContratoResponse tipoContratoResponse = tipoContratoService.obterPorId(usuarioForm.getTipoContrato());
        TipoContrato tipoContrato = modelMapperService.converterParaEntidade(tipoContratoResponse, TipoContrato.class);

        Funcionario funcionario = usuario.getFuncionario();
        funcionario.setCargo(cargo);
        funcionario.setTipoContrato(tipoContrato);
        funcionario.setDataContratacao(LocalDate.now());

        usuario.getPerfis().clear();
        Set<Perfil> perfis = new HashSet<>(usuario.getPerfis());
        PerfilResponse perfilResponse = perfilService.obterPorId(usuarioForm.getPerfil());
        Perfil perfil = modelMapperService.converterParaEntidade(perfilResponse, Perfil.class);
        perfis.add(perfil);
        usuario.setPerfis(perfis);

        return usuario;
    }

    @Override
    protected UsuarioResponse montarDto(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPessoa().getNome(),
                usuario.getFuncionario().getCargo().getNome(),
                usuario.getFuncionario().getTipoContrato().getNome(),
                usuario.getPerfis().stream()
                        .map(Perfil::getNome)
                        .toList()
        );
    }

    public List<String> obterPerfis(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException
                        (MensagensError.USUARIO_NAO_ENCONTRADO_POR_LOGIN.getMessage(email)));
        return usuario.getPerfis().stream().map(Perfil::getNome).collect(Collectors.toList());
    }

    public Usuario obterUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException
                        (MensagensError.USUARIO_NAO_ENCONTRADO_POR_LOGIN.getMessage(email)));
    }

    public Usuario obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        MensagensError.USUARIO_NAO_ENCONTRADO_POR_ID.getMessage(id)));
    }
}
