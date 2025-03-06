package com.proyecto.demo.repository;

import com.proyecto.demo.model.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EleccionRepository extends JpaRepository<Eleccion, Long> {
}
