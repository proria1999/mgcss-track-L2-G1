package com.mgcss.controller;

import com.mgcss.dto.TecnicoResponseDTO;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.TecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tecnicos")
@Tag(name = "Técnicos", description = "Controlador para la administración del personal técnico de soporte")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar técnico por ID", description = "Recupera la información detallada de un técnico mediante su ID único.")
    @ApiResponse(responseCode = "200", description = "Técnico encontrado")
    @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    public ResponseEntity<TecnicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        TecnicoEntity entity = tecnicoService.buscarTecnicoOError(id, "Técnico no encontrado");
        return ResponseEntity.ok(mapToResponse(entity));
    }

    @GetMapping
    @Operation(summary = "Obtener lista de técnicos", description = "Retorna el listado de todos los técnicos de la empresa (activos e inactivos).")
    @ApiResponse(responseCode = "200", description = "Lista de técnicos recuperada correctamente")
    public ResponseEntity<List<TecnicoResponseDTO>> listarTodos() {
        List<TecnicoResponseDTO> dtos = tecnicoService.listarTodos().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private TecnicoResponseDTO mapToResponse(TecnicoEntity entity) {
        return new TecnicoResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEspecialidad().name(),
                entity.isActivo()
        );
    }
}
