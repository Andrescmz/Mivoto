package com.proyecto.demo.service;

import com.proyecto.demo.dto.CandidatoDTO;
import com.proyecto.demo.dto.EleccionDTO;
import com.proyecto.demo.dto.UsuarioDTO;
import com.proyecto.demo.dto.VotoDTO;
import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Eleccion;
import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.model.Voto;
import com.proyecto.demo.repository.CandidatoRepository;
import com.proyecto.demo.repository.EleccionRepository;
import com.proyecto.demo.repository.UsuarioRepository;
import com.proyecto.demo.repository.VotoRepository;
import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CandidatoRepository candidatoRepository;
    private final EleccionRepository eleccionRepository;

    public VotoDTO registrarVoto(Long idCandidato, Long idEleccion) {
        // Obtener el usuario autenticado
        String email = obtenerUsuarioAutenticado();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Eleccion eleccion = eleccionRepository.findById(idEleccion)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada"));

// Verificar si el usuario ya ha votado en esta elección
        if (votoRepository.existsByVotanteAndEleccion(usuario, eleccion)) {
            throw new RuntimeException("El usuario ya ha votado en esta elección");
        }

        // Verificar que el candidato exista
        Candidato candidato = candidatoRepository.findById(idCandidato)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

        // Registrar el voto
        Voto voto = new Voto();
        voto.setVotante(usuario);
        voto.setCandidato(candidato);
        voto.setEleccion(eleccion);
        votoRepository.save(voto);

        return convertirADTO(voto);
    }

    public List<VotoDTO> obtenerResultados() {
        List<Voto> votos = votoRepository.findAll();
        return votos.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private String obtenerUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private VotoDTO convertirADTO(Voto voto) {
        UsuarioDTO votanteDTO = new UsuarioDTO(
                voto.getVotante().getNombre(),
                voto.getVotante().getSegundoNombre(),
                voto.getVotante().getApellido(),
                voto.getVotante().getSegundoApellido(),
                voto.getVotante().getDocumento(),
                voto.getVotante().getEmail(),
                voto.getVotante().getRol().getDescripcion()
        );

        CandidatoDTO candidatoDTO = new CandidatoDTO(votanteDTO, voto.getCandidato().getPropuesta());

        EleccionDTO eleccionDTO = new EleccionDTO(
                voto.getEleccion().getNombre(),
                voto.getEleccion().getDescripcion(),
                voto.getEleccion().getFechaInicio(),
                voto.getEleccion().getFechaFin()
        );

        return new VotoDTO(votanteDTO, candidatoDTO, eleccionDTO, voto.getFechaVoto().format(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }
}
