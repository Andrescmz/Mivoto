package com.proyecto.demo.repository;

import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Eleccion;
import com.proyecto.demo.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    List<Resultado> findAllByEleccion(Eleccion eleccion);
    Optional<Resultado> findByEleccionAndCandidato(Eleccion eleccion, Candidato candidato);
}
