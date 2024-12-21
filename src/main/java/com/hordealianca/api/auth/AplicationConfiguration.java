package com.hordealianca.api.auth;

import com.hordealianca.api.exception.MensagensError;
import com.hordealianca.api.exception.UsuarioNaoEncontradoException;
import com.hordealianca.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AplicationConfiguration {
    private final UsuarioRepository userRepository;

    /**
     * Cria um bean para o UserDetailsService
     *
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        MensagensError.USUARIO_NAO_ENCONTRADO_POR_LOGIN.getMessage(username)));
    }

    /**
     * Cria um bean para o BCryptPasswordEncoder
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cria um bean para o AuthenticationManager
     *
     * @param config Configuração de autenticação
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
