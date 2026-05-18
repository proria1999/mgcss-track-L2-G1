package com.mgcss.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar clientes")
public class ClienteRequestDTO {

    @NotBlank
    @Schema(description = "Nombre completo del cliente",example = "Juan Pérez")
    private String nombre;

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico del cliente",example = "juan@email.com")
    private String email;
}