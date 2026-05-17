package com.mgcss.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta con los datos de un técnico")
public class TecnicoResponseDTO {
    
    @Schema(description = "Identificador único del técnico", example = "10")
    private long id;
    
    @Schema(description = "Nombre completo", example = "Ana Gómez")
    private String nombre;
    
    @Schema(description = "Especialidad asignada", example = "REDES")
    private String especialidad;
    
    @Schema(description = "Estado de actividad actual", example = "true")
    private boolean activo;
}