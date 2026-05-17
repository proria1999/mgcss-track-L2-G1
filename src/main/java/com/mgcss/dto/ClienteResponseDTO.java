package com.mgcss.dto;

import com.mgcss.domain.EspecialidadTecnico;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta de cliente")
public class ClienteResponseDTO {

    @Schema(
        description = "Identificador único del cliente",
        example = "1"
    )
    private Long id;

    @Schema(
        description = "Nombre del cliente",
        example = "Juan Pérez"
    )
    private String nombre;

    @Schema(
        description = "Correo electrónico",
        example = "juan@email.com"
    )
    private String email;
}
