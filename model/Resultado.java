package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResultado;

    @ManyToOne
    @JoinColumn(name = "id_eleccion", nullable = false)
    private Eleccion eleccion;

    @ManyToOne
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;

    private Integer cantidadVotos;

    public Resultado(Eleccion eleccion, Candidato candidato, Integer cantidadVotos) {
        this.eleccion = eleccion;
        this.candidato = candidato;
        this.cantidadVotos = cantidadVotos;
    }
}
