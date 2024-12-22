package com.rodrigo.api.auth;

import com.rodrigo.api.model.Usuario;
import com.rodrigo.api.model.form.LoginForm;
import com.rodrigo.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    /**
     * Autentica um utilizador
     *
     * @param loginUsuario Dados do utilizador
     * @return Utilizador autenticado
     */
    public Usuario authenticate(LoginForm loginUsuario) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUsuario.getEmail(),
                        loginUsuario.getSenha()
                )
        );

        return usuarioRepository.findByEmailIgnoreCase(loginUsuario.getEmail())
                .orElseThrow();
    }
}
