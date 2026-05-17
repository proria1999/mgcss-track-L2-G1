package com.mgcss.dto;

import com.mgcss.domain.EspecialidadTecnico;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoRequestDTO {
    private String nombre;
    private EspecialidadTecnico especialidad;
    private boolean activo;
}