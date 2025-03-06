package com.proyecto.demo.dto;

import com.proyecto.demo.model.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String nombre;
    private String segundoNombre;
    private String apellido;
    private String segundoApellido;
    private String documento;
    private String email;
    private String rol;

    public UsuarioDTO(Usuario usuario) {
        this.segundoNombre = (usuario != null) ? usuario.getNombre() : "Sin nombre";
    }
}