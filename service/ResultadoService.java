package com.proyecto.demo.service;

import com.proyecto.demo.dto.CandidatoDTO;
import com.proyecto.demo.dto.EleccionDTO;
import com.proyecto.demo.dto.ResultadoDTO;
import com.proyecto.demo.dto.UsuarioDTO;
import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Eleccion;
import com.proyecto.demo.model.Resultado;
import com.proyecto.demo.repository.CandidatoRepository;
import com.proyecto.demo.repository.EleccionRepository;
import com.proyecto.demo.repository.ResultadoRepository;
import com.proyecto.demo.repository.VotoRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final VotoRepository votoRepository;
    private final EleccionRepository eleccionRepository;
    private final CandidatoRepository candidatoRepository;

    public List<ResultadoDTO> obtenerResultadosPorEleccion(Long idEleccion) {
        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

        List<Resultado> resultados = resultadoRepository.findAllByEleccion(eleccion);
        return resultados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public void calcularResultados(Long idEleccion) {
        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

        List<Candidato> candidatos = candidatoRepository.findAllByEleccion(eleccion);
        for (Candidato candidato : candidatos) {
            long votos = votoRepository.countByCandidatoAndEleccion(candidato, eleccion);

            Resultado resultado = resultadoRepository.findByEleccionAndCandidato(eleccion, candidato)
                    .orElse(new Resultado(eleccion, candidato, 0));

            resultado.setCantidadVotos((int) votos);
            resultadoRepository.save(resultado);
        }
    }

    private ResultadoDTO convertirADTO(Resultado resultado) {
        EleccionDTO eleccionDTO = new EleccionDTO(
                resultado.getEleccion().getNombre(),
                resultado.getEleccion().getDescripcion(),
                resultado.getEleccion().getFechaInicio(),
                resultado.getEleccion().getFechaFin()
        );

        CandidatoDTO candidatoDTO = new CandidatoDTO(
                new UsuarioDTO(
                        resultado.getCandidato().getUsuario().getNombre(),
                        resultado.getCandidato().getUsuario().getSegundoNombre(),
                        resultado.getCandidato().getUsuario().getApellido(),
                        resultado.getCandidato().getUsuario().getSegundoApellido(),
                        resultado.getCandidato().getUsuario().getDocumento(),
                        resultado.getCandidato().getUsuario().getEmail(),
                        resultado.getCandidato().getUsuario().getRol().getDescripcion()
                ),
                resultado.getCandidato().getPropuesta()
        );

        return new ResultadoDTO(eleccionDTO, candidatoDTO, resultado.getCantidadVotos());
    }
}