package com.hordealianca.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoContratoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Integer horasDia;
}

