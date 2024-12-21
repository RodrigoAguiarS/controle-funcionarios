package com.hordealianca.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilResponse {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
}