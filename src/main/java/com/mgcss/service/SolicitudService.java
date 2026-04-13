package com.mgcss.service;

import com.mgcss.domain.*;
import com.mgcss.infrastructure.*;
import java.time.LocalDate;

public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final TecnicoRepository tecnicoRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("Solo se puede asignar un técnico activo.");
        }

        solicitud.setTecnicoAsignado(tecnico);
        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitudRepository.save(solicitud);
    }

    public void cerrarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("No existe la solicitud"));

        if (solicitud.getEstado() != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede cerrar una solicitud si está EN_PROCESO.");
        }

        solicitud.setEstado(EstadoSolicitud.CERRADA);
        solicitud.setFechaCierre(LocalDate.now());
        solicitudRepository.save(solicitud);
    }
    
    public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validación de negocio: No se puede cambiar el estado de una cerrada
        if (solicitud.getEstado() == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud ya cerrada.");
        }

        solicitud.setEstado(nuevoEstado);
        solicitudRepository.save(solicitud);
    }
}