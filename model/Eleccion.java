package com.proyecto.demo.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "eleccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Eleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEleccion;

    private String nombre;

    private String descripcion;

    private String fechaInicio;

    private String fechaFin;

    @OneToMany(mappedBy = "eleccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidato> candidatos;
}
