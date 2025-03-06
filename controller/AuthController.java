package com.proyecto.demo.controller;

import com.proyecto.demo.model.Usuario;
import com.proyecto.demo.service.AuthService;
import com.proyecto.demo.service.CandidatoService;
import com.proyecto.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String mostrarInicio(Model model) {
        model.addAttribute("candidatos", candidatoService.obtenerCandidatosConPropuestas());
        return "home";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Intentando autenticar usuario con email: " + email);

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setPassword(password);

            String token = authService.login(usuario);

            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
