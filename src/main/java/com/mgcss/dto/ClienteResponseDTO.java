package com.mgcss.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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


    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
