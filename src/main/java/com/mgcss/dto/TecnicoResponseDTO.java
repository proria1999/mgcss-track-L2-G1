package com.mgcss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoResponseDTO {
    private long id;
    private String nombre;
    private String especialidad;
    private boolean activo;
}