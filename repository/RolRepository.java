package com.proyecto.demo.repository;

import com.proyecto.demo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByDescripcion(String descripcion);
}
