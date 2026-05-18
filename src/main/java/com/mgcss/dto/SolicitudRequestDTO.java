package com.mgcss.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos requeridos para crear una solicitud")
public class SolicitudRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "Identificador único del cliente", example = "1")
    private Long clienteId;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 10, max = 255, message = "La descripción debe tener entre 10 y 255 caracteres")
    @Schema(description = "Descripción de la incidencia técnica", example = "Fallo en el servicio de red local")
    private String descripcion;
}