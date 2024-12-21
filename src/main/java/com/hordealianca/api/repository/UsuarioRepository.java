package com.hordealianca.api.repository;

import com.hordealianca.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca um utilizador pelo endereço eletrónico
     *
     * @param email email do utilizador
     * @return um utilizador
     */
    Optional<Usuario> findByEmailIgnoreCase(String email);

}
