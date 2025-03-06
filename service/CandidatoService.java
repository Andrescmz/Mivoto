package com.proyecto.demo.service;

import com.proyecto.demo.dto.CandidatoDTO;
import com.proyecto.demo.dto.UsuarioDTO;
import com.proyecto.demo.model.Candidato;
import com.proyecto.demo.model.Rol;
import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.repository.CandidatoRepository;
import com.proyecto.demo.repository.RolRepository;
import com.proyecto.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public List<CandidatoDTO> obtenerTodosCandidatos() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        return candidatos.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public CandidatoDTO obtenerCandidatoPorId(Long id) {
        Optional<Candidato> candidato = candidatoRepository.findById(id);
        return candidato.map(this::convertirADTO).orElse(null);
    }

    public List<CandidatoDTO> obtenerCandidatosConPropuestas() {
        return candidatoRepository.findAll()
                .stream()
                .map(c -> new CandidatoDTO(
                        new UsuarioDTO(c.getUsuario()), // Pasar un UsuarioDTO en lugar de un String
                        c.getPropuesta()
                ))
                .collect(Collectors.toList());
    }

    public CandidatoDTO registrarCandidato(CandidatoDTO candidatoDTO) {
        // Verificar si el usuario existe
        Usuario usuario = usuarioRepository.findByEmail(candidatoDTO.getUsuario().getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que tenga el rol de Candidato
        Rol rolCandidato = rolRepository.findByDescripcion("CANDIDATO")
                .orElseThrow(() -> new RuntimeException("Rol de Candidato no encontrado"));

        if (!usuario.getRol().equals(rolCandidato)) {
            throw new RuntimeException("El usuario no tiene el rol de Candidato");
        }

        // Registrar el candidato
        Candidato candidato = new Candidato();
        candidato.setUsuario(usuario);
        candidato.setPropuesta(candidatoDTO.getPropuesta());

        candidato = candidatoRepository.save(candidato);
        return convertirADTO(candidato);
    }

    public void eliminarCandidato(Long id) {
        candidatoRepository.deleteById(id);
    }

    public CandidatoDTO actualizarPropuesta(Long id, String nuevaPropuesta) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));
        candidato.setPropuesta(nuevaPropuesta);
        candidato = candidatoRepository.save(candidato);
        return convertirADTO(candidato);
    }

    private CandidatoDTO convertirADTO(Candidato candidato) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                candidato.getUsuario().getNombre(),
                candidato.getUsuario().getSegundoNombre(),
                candidato.getUsuario().getApellido(),
                candidato.getUsuario().getSegundoApellido(),
                candidato.getUsuario().getDocumento(),
                candidato.getUsuario().getEmail(),
                candidato.getUsuario().getRol().getDescripcion()
        );
        return new CandidatoDTO(usuarioDTO, candidato.getPropuesta());
    }
}
