package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(nullable = false, unique = true)
    private String descripcion;
}
