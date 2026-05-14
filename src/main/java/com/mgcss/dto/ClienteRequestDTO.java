package com.mgcss.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para crear o actualizar clientes")
public class ClienteRequestDTO {

    @NotBlank
    @Schema(
        description = "Nombre completo del cliente",
        example = "Juan Pérez"
    )
    private String nombre;

    @Email
    @NotBlank
    @Schema(
        description = "Correo electrónico del cliente",
        example = "juan@email.com"
    )
    private String email;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}