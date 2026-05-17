package com.mgcss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoResponseDTO {
    private long id;
    private String nombre;
    private String especialidad;
    private boolean activo;
}