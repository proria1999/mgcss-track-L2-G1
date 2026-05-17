package com.mgcss.controller;

import com.mgcss.dto.TecnicoResponseDTO;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.TecnicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> obtenerPorId(@PathVariable Long id) {
        TecnicoEntity entity = tecnicoService.buscarTecnicoOError(id, "Técnico no encontrado");
        return ResponseEntity.ok(mapToResponse(entity));
    }

    @GetMapping
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
