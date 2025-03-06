package com.proyecto.demo.controller;


import com.proyecto.demo.dto.UsuarioDTO;
import com.proyecto.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping({"/","/lista"})
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista"; // Nombre del archivo HTML sin extensión
    }

    @GetMapping("/registrarse")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioDTO()); // Instancia vacía de UsuarioDTO
        return "usuarios/registro";
    }

    @PostMapping("/registrarse")
    public String registrarUsuario(@ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioService.registrarUsuario(usuarioDTO);
        return "redirect:/usuarios/";
    }

    @GetMapping("/candidato")
    public String listarCandidatos(Model model) {
        List<UsuarioDTO> candidatos = usuarioService.obtenerCandidatos();
        model.addAttribute("candidatos", candidatos);
        return "usuarios/candidatos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/editar_usuario";
    }

    @PostMapping("/{id}/editar")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioDTO usuarioDTO) {
        usuarioService.actualizarUsuario(id, usuarioDTO);
        return "redirect:/usuarios/lista";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios/lista";
    }
}

