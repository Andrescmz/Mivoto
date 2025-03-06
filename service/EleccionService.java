package com.proyecto.demo.service;

import com.proyecto.demo.dto.EleccionDTO;
import com.proyecto.demo.model.Eleccion;
import com.proyecto.demo.repository.EleccionRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EleccionService {

    private final EleccionRepository eleccionRepository;

    public List<EleccionDTO> obtenerTodasElecciones() {
        List<Eleccion> elecciones = eleccionRepository.findAll();
        return elecciones.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public EleccionDTO obtenerEleccionPorId(Long id) {
        Optional<Eleccion> eleccion = eleccionRepository.findById(id);
        return eleccion.map(this::convertirADTO).orElse(null);
    }

    public EleccionDTO crearEleccion(EleccionDTO eleccionDTO) {
        Eleccion eleccion = new Eleccion();
        eleccion.setNombre(eleccionDTO.getNombre());
        eleccion.setDescripcion(eleccionDTO.getDescripcion());
        eleccion.setFechaInicio(eleccionDTO.getFechaInicio());
        eleccion.setFechaFin(eleccionDTO.getFechaFin());

        eleccion = eleccionRepository.save(eleccion);
        return convertirADTO(eleccion);
    }

    public void eliminarEleccion(Long id) {
        eleccionRepository.deleteById(id);
    }

    private EleccionDTO convertirADTO(Eleccion eleccion) {
        return new EleccionDTO(
                eleccion.getNombre(),
                eleccion.getDescripcion(),
                eleccion.getFechaInicio(),
                eleccion.getFechaFin()
        );
    }
}
