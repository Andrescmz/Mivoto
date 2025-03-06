package com.proyecto.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {
    private EleccionDTO eleccion;
    private CandidatoDTO candidato;
    private Integer cantidadVotos;
}
