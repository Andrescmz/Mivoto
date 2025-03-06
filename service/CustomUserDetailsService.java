package com.proyecto.demo.service;

import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.repository.UsuarioRepository;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init() {
        System.out.println("CustomUserDetailsService cargado correctamente");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar con email: " + email);

        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: el email recibido es nulo o vacío.");
            throw new UsernameNotFoundException("Email vacío o nulo.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailIgnoreCase(email.trim().toLowerCase());

        if (usuarioOptional.isEmpty()) {
            System.out.println("No se encontró el usuario con email: " + email);
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        Usuario usuario = usuarioOptional.get();
        System.out.println("Usuario encontrado: " + usuario.getEmail());
        System.out.println("Contraseña almacenada en BD: " + usuario.getPassword());
        return usuarioOptional.get();
    }
}


