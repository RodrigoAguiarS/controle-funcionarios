package com.rodrigo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    /**
     * Desativa a entidade.
     */
    public void desativar() {
        this.ativo = false;
    }

    /**
     * Ativa a entidade.
     */
    public void ativar() {
        this.ativo = true;
    }

    /**
     * Atualiza a data de criação da entidade.
     */
    @PrePersist
    protected void onCreate() {
        this.ativo = true;
        this.criadoEm = LocalDateTime.now();
    }

    /**
     * Atualiza a data de atualização da entidade.
     */
    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}

