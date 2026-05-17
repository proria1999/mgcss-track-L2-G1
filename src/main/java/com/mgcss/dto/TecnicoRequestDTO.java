package com.mgcss.dto;

import com.mgcss.domain.EspecialidadTecnico;
import lombok.Data;

@Data
public class TecnicoRequestDTO {
    private String nombre;
    private EspecialidadTecnico especialidad;
    private boolean activo;
}