package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "voto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVoto;

    @ManyToOne
    @JoinColumn(name = "id_votante", nullable = false)
    private Usuario votante;

    @ManyToOne
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "id_eleccion", nullable = false)
    private Eleccion eleccion;

    @Column(nullable = false)
    private String fechaVoto;
}
