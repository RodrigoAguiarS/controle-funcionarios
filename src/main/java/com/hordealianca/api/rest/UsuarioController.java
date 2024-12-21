package com.hordealianca.api.rest;

import com.hordealianca.api.model.Usuario;
import com.hordealianca.api.model.form.UsuarioForm;
import com.hordealianca.api.model.response.UsuarioResponse;
import com.hordealianca.api.services.impl.UsuarioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends CrudControllerImpl<UsuarioForm, UsuarioResponse> {

    private final UsuarioServiceImpl usuarioServiceImpl;

    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl) {
        super(usuarioServiceImpl);
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @GetMapping("/papel")
    public ResponseEntity<List<String>> getRoles(Authentication authentication) {
        String email = authentication.getName();
        List<String> roles = usuarioServiceImpl.obterPerfis(email);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/dados")
    public ResponseEntity<Usuario> obterUsuarioPorEmail(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioServiceImpl.obterUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/obterPorId/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioServiceImpl.obterUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
