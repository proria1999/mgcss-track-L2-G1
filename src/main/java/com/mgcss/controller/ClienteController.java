package com.mgcss.controller;

import com.mgcss.dto.ClienteRequestDTO;
import com.mgcss.dto.ClienteResponseDTO;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente en el sistema")
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO request) {
        // Nota: Tu ClienteService actual solo recibe nombre y tipo. 
        // He usado el nombre del DTO. El tipo se asigna como STANDARD internamente en el servicio.
        ClienteEntity cliente = clienteService.crearCliente(request.getNombre(), "STANDARD");
        return ResponseEntity.ok(toDTO(cliente));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar cliente", description = "Recupera la información de un cliente por su ID")
    public ResponseEntity<ClienteResponseDTO> consultar(@PathVariable Long id) {
        ClienteEntity cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(toDTO(cliente));
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Obtiene el listado completo de clientes registrados")
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        List<ClienteResponseDTO> dtos = clienteService.listarTodos().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    // Mapeo manual para mantener consistencia con SolicitudController
    private ClienteResponseDTO toDTO(ClienteEntity entity) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}