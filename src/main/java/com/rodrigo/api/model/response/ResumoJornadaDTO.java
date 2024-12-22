package com.rodrigo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoJornadaDTO {
    private LocalDate data;
    private List<PontoDTO> pontos;
    private boolean jornadaCompleta;
    private Duration horasRestantes;
    private Duration horasExtras;

    public ResumoJornadaDTO(Duration totalHorasTrabalhadas, Duration totalHorasExtras, Duration totalHorasRestantes) {
        this.horasRestantes = totalHorasRestantes;
        this.horasExtras = totalHorasExtras;
    }
}
