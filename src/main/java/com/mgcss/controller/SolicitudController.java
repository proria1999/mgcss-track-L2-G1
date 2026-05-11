package com.mgcss.controller;

import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.dto.SolicitudRequestDTO;
import com.mgcss.dto.SolicitudResponseDTO;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "Endpoints obligatorios para la gestión de solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    @Operation(summary = "Crear solicitud", description = "Registra una nueva solicitud para un cliente determinado")
    public ResponseEntity<SolicitudResponseDTO> crear(@Valid @RequestBody SolicitudRequestDTO request) {
        SolicitudEntity solicitud = solicitudService.crearSolicitud(request.getClienteId(), request.getDescripcion());
        return ResponseEntity.ok(toDTO(solicitud));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar solicitud", description = "Recupera la información detallada de una solicitud por ID")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
        SolicitudEntity solicitud = solicitudService.buscarSolicitudOError(id, null);
        return ResponseEntity.ok(toDTO(solicitud));
    }

    @GetMapping
    @Operation(summary = "Listar solicitudes", description = "Obtiene el listado completo de solicitudes persistidas")
    public ResponseEntity<List<SolicitudResponseDTO>> listar() {
        List<SolicitudResponseDTO> dtos = solicitudService.listarTodas().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/tecnico/{tecnicoId}")
    @Operation(summary = "Asignar técnico", description = "Asigna un operario de soporte a la solicitud")
    public ResponseEntity<Void> asignarTecnico(@PathVariable Long id, @PathVariable Long tecnicoId) {
        solicitudService.asignarTecnico(id, tecnicoId);
        return ResponseEntity.ok().build();
    } 

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Modifica manualmente el estado operativo de la solicitud")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam EstadoSolicitud estado) {
        solicitudService.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reabrir")
    @Operation(summary = "Reabrir solicitud", description = "Reabre una solicitud cerrada previamente")
    public ResponseEntity<Void> reabrir(@PathVariable Long id) {
        solicitudService.reabrirSolicitud(id);
        return ResponseEntity.ok().build();
    }

    // Mapeo explícito manual para evitar filtrado accidental del dominio interno
    private SolicitudResponseDTO toDTO(SolicitudEntity entity) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setEstado(entity.getEstado());
        dto.setFechaCierre(entity.getFechaCierre());
        
        if (entity.getCliente() != null) {
            dto.setClienteNombre(entity.getCliente().getNombre());
        }
        if (entity.getTecnicoAsignado() != null) {
            dto.setTecnicoNombre(entity.getTecnicoAsignado().getNombre());
        }
        return dto;
    }
}