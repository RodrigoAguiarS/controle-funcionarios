package com.rodrigo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JornadaResponse {
    private Long id;
    private Long funcionarioId;
    private LocalDate data;
    private Duration horasTrabalhadas;
    private Duration horasExtras;
    private Duration horasRestantes;
}
