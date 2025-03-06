package com.proyecto.demo.repository;

import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Eleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findAllByEleccion(Eleccion eleccion);
}
