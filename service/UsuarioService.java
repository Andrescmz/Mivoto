package com.proyecto.demo.service;

import com.proyecto.demo.dto.UsuarioDTO;
import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.proyecto.demo.model.Rol;
import com.proyecto.demo.repository.RolRepository;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(this::convertirADTO).orElse(null);
    }

    public List<UsuarioDTO> obtenerCandidatos() {
        List<Usuario> candidatos = usuarioRepository.findByRolDescripcion("Candidato");
        return candidatos.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setSegundoNombre(usuarioDTO.getSegundoNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setSegundoApellido(usuarioDTO.getSegundoApellido());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode("default123"));

        Rol rol = rolRepository.findByDescripcion(usuarioDTO.getRol()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setSegundoNombre(usuarioDTO.getSegundoNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setSegundoApellido(usuarioDTO.getSegundoApellido());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setEmail(usuarioDTO.getEmail());

        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getSegundoNombre(),
                usuario.getApellido(),
                usuario.getSegundoApellido(),
                usuario.getDocumento(),
                usuario.getEmail(),
                usuario.getRol().getDescripcion()
        );
    }
}

