package com.mgcss.dto;

import com.mgcss.domain.EspecialidadTecnico;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un técnico")
public class TecnicoRequestDTO {
    
    @NotBlank
    @Schema(description = "Nombre completo del técnico", example = "Ana Gómez")
    private String nombre;
    
    @NotNull
    @Schema(description = "Especialidad del técnico", example = "MANTENIMIENTO")
    private EspecialidadTecnico especialidad;
    
    @Schema(description = "Indica si el técnico está activo en la empresa", example = "true")
    private boolean activo;
}