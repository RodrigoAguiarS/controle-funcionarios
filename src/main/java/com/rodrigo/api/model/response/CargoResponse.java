package com.rodrigo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private BigDecimal salario;
}
