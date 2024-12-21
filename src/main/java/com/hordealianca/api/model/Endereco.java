package com.hordealianca.api.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco extends BaseEntidade {
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String numero;

    @Override
    public String toString() {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + ", " + cep;
    }
}

