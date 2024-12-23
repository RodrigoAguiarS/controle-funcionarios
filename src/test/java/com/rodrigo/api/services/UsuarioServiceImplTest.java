package com.rodrigo.api.services;

import com.rodrigo.api.model.Cargo;
import com.rodrigo.api.model.Funcionario;
import com.rodrigo.api.model.Perfil;
import com.rodrigo.api.model.Pessoa;
import com.rodrigo.api.model.TipoContrato;
import com.rodrigo.api.model.Usuario;
import com.rodrigo.api.model.form.UsuarioForm;
import com.rodrigo.api.model.response.CargoResponse;
import com.rodrigo.api.model.response.PerfilResponse;
import com.rodrigo.api.model.response.TipoContratoResponse;
import com.rodrigo.api.model.response.UsuarioResponse;
import com.rodrigo.api.repository.UsuarioRepository;
import com.rodrigo.api.services.impl.CargoServiceImpl;
import com.rodrigo.api.services.impl.PerfilServiceImpl;
import com.rodrigo.api.services.impl.TipoContratoServiceImpl;
import com.rodrigo.api.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PerfilServiceImpl perfilService;

    @Mock
    private CargoServiceImpl cargoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private TipoContratoServiceImpl tipoContratoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UsuarioForm inicializarUsuarioForm() {
        UsuarioForm form = new UsuarioForm();
        form.setNome("Nome Teste");
        form.setDataNascimento(LocalDate.now());
        form.setTelefone("1234567890");
        form.setCpf("123.456.789-00");
        form.setRua("Rua Teste");
        form.setNumero("123");
        form.setBairro("Bairro Teste");
        form.setCidade("Cidade Teste");
        form.setEstado("Estado Teste");
        form.setCep("12345-678");
        form.setCargo(1L);
        form.setTipoContrato(1L);
        form.setPerfil(1L);
        form.setEmail("teste@teste.com");
        form.setSenha("senha123");
        return form;
    }

    @Test
    void testCriarUsuario() {
        UsuarioForm form = inicializarUsuarioForm();

        Usuario usuario = new Usuario();
        usuario.setPessoa(new Pessoa());
        usuario.setFuncionario(new Funcionario());
        usuario.getFuncionario().setCargo(new Cargo());
        usuario.getFuncionario().setTipoContrato(new TipoContrato());

        doNothing().when(modelMapperService).mapear(any(), any());
        when(passwordEncoder.encode(anyString())).thenReturn("senha123");
        when(cargoService.obterPorId(anyLong())).thenReturn(new CargoResponse());
        when(tipoContratoService.obterPorId(anyLong())).thenReturn(new TipoContratoResponse());
        when(perfilService.obterPorId(anyLong())).thenReturn(new PerfilResponse());
        when(modelMapperService.converterParaEntidade(any(), eq(Cargo.class))).thenReturn(new Cargo());
        when(modelMapperService.converterParaEntidade(any(), eq(TipoContrato.class))).thenReturn(new TipoContrato());
        when(modelMapperService.converterParaEntidade(any(), eq(Perfil.class))).thenReturn(new Perfil());
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioResponse response = usuarioService.criar(form);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void testAtualizarUsuario() {
        Long id = 1L;
        UsuarioForm form = inicializarUsuarioForm();

        Usuario usuario = new Usuario();
        usuario.setPessoa(new Pessoa());
        usuario.setFuncionario(new Funcionario());
        usuario.getFuncionario().setCargo(new Cargo());
        usuario.getFuncionario().setTipoContrato(new TipoContrato());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        doNothing().when(modelMapperService).mapear(any(), any());
        when(passwordEncoder.encode(anyString())).thenReturn("senha123");
        when(cargoService.obterPorId(anyLong())).thenReturn(new CargoResponse());
        when(tipoContratoService.obterPorId(anyLong())).thenReturn(new TipoContratoResponse());
        when(perfilService.obterPorId(anyLong())).thenReturn(new PerfilResponse());
        when(modelMapperService.converterParaEntidade(any(), eq(Cargo.class))).thenReturn(new Cargo());
        when(modelMapperService.converterParaEntidade(any(), eq(TipoContrato.class))).thenReturn(new TipoContrato());
        when(modelMapperService.converterParaEntidade(any(), eq(Perfil.class))).thenReturn(new Perfil());
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioResponse response = usuarioService.atualizar(id, form);

        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void testObterUsuarioPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.obterUsuarioPorId(id);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void testObterUsuarioPorEmail() {
        String email = "teste@teste.com";
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.obterUsuarioPorEmail(email);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findByEmailIgnoreCase(email);
    }

    @Test
    void testObterPerfis() {
        String email = "teste@teste.com";
        Usuario usuario = new Usuario();
        Perfil perfil = new Perfil();
        perfil.setNome("ROLE_USER");
        usuario.setPerfis(Set.of(perfil));
        when(usuarioRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(usuario));

        var perfis = usuarioService.obterPerfis(email);

        assertNotNull(perfis);
        assertEquals(1, perfis.size());
        assertEquals("ROLE_USER", perfis.get(0));
        verify(usuarioRepository, times(1)).findByEmailIgnoreCase(email);
    }

    @Test
    void testAtivarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        usuarioService.ativar(id);

        assertTrue(usuario.getAtivo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testDesativarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        usuarioService.desativar(id);

        assertFalse(usuario.getAtivo());
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
