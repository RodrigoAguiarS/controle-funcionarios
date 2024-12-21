package com.hordealianca.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario extends BaseEntidade {
    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private LocalDate dataContratacao;

    @ManyToOne
    @JoinColumn(name = "tipo_contrato_id", nullable = false)
    private TipoContrato tipoContrato;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ponto> pontos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jornada> jornadas = new ArrayList<>();
}
