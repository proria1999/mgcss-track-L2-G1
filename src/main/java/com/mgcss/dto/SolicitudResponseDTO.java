package com.mgcss.dto;

import com.mgcss.domain.EstadoSolicitud;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Contrato de salida de una solicitud procesada")
public class SolicitudResponseDTO {

    @Schema(description = "Identificador único de la solicitud", example = "123")
    private Long id;

    @Schema(description = "Nombre del cliente asociado", example = "Cliente S.A.")
    private String clienteNombre;

    @Schema(description = "Descripción de la solicitud", example = "Fallo en el servicio de red local")
    private String descripcion;

    @Schema(description = "Fecha de registro", example = "2026-05-08")
    private LocalDate fechaCreacion;

    @Schema(description = "Estado operativo actual", example = "ABIERTA")
    private EstadoSolicitud estado;

    @Schema(description = "Nombre del técnico asignado si existe", example = "Juan")
    private String tecnicoNombre;

    @Schema(description = "Fecha de resolución del problema", example = "null")
    private LocalDate fechaCierre;
}
