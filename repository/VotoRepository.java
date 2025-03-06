package com.proyecto.demo.repository;

import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Eleccion;
import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByVotanteAndEleccion(Usuario votante, Eleccion eleccion);
    long countByCandidatoAndEleccion(Candidato candidato, Eleccion eleccion);
}
