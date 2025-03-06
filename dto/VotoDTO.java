package com.proyecto.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {
    private UsuarioDTO votante;
    private CandidatoDTO candidato;
    private EleccionDTO eleccion;
    private String fechaVoto;
}