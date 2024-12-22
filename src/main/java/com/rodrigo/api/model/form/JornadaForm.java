package com.rodrigo.api.model.form;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JornadaForm {
    @NotNull
    private Long funcionarioId;

    @NotNull
    @PastOrPresent
    private LocalDate data;

    @NotNull
    private Duration horasTrabalhadas;

    @NotNull
    private Duration horasExtras;

    @NotNull
    private Duration horasRestantes;
}