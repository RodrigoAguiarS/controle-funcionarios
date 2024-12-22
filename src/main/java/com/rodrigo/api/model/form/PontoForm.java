package com.rodrigo.api.model.form;

import com.rodrigo.api.model.TipoPonto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PontoForm {
    @NotNull
    private LocalDateTime novaDataHora;

    @NotNull
    private TipoPonto tipo;
}