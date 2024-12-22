package com.rodrigo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PontoDTO {
    private Long id;
    private LocalDateTime dataHora;
    private String observacao;
    private String tipo;
}
