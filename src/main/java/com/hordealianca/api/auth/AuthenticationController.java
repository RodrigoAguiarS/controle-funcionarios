package com.hordealianca.api.auth;

import com.hordealianca.api.model.Usuario;
import com.hordealianca.api.model.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    /**
     * Autentica um utilizador
     *
     * @param loginForm Dados do utilizador
     * @return Utilizador autenticado
     */
    @PostMapping("/login")
    public ResponseEntity<Void> authenticate(@RequestBody LoginForm loginForm) {
        Usuario usuario = authenticationService.authenticate(loginForm);
        String jwtToken = jwtService.generateToken(usuario);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
