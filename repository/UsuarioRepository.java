package com.proyecto.demo.repository;

import com.proyecto.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRolDescripcion(String descripcion);// Métodos adicionales si son necesarios
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
