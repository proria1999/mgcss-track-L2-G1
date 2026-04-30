package com.mgcss.service;

import java.time.LocalDate;

import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.infrastructure.TecnicoRepository;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.infrastructure.persistence.TecnicoEntity;

public class SolicitudService {
    private final JpaSolicitudRepository jpaSolicitudRepository;
    private final JpaTecnicoRepository jpaTecnicoRepository;

    public SolicitudService(JpaSolicitudRepository solicitudRepository, JpaTecnicoRepository tecnicoRepository) {
        this.jpaSolicitudRepository = solicitudRepository;
        this.jpaTecnicoRepository = tecnicoRepository;
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        SolicitudEntity solicitudEntity = jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        TecnicoEntity tecnicoEntity = jpaTecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        if (!tecnicoEntity.isActivo()) {
            throw new IllegalArgumentException("Solo se puede asignar un técnico activo.");
        }

        solicitudEntity.setTecnicoAsignado(tecnicoEntity);
        solicitudEntity.setEstado(EstadoSolicitud.EN_PROCESO);
        jpaSolicitudRepository.save(solicitudEntity);
    }

    public void cerrarSolicitud(Long solicitudId) {
        SolicitudEntity solicitudEntity = jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("No existe la solicitud"));

        if (solicitudEntity.getEstado() != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede cerrar una solicitud si está EN_PROCESO.");
        }

        solicitudEntity.setEstado(EstadoSolicitud.CERRADA);
        solicitudEntity.setFechaCierre(LocalDate.now());
        jpaSolicitudRepository.save(solicitudEntity);
    }
    
    public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        SolicitudEntity solicitudEntity = jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validación de negocio: No se puede cambiar el estado de una cerrada
        if (solicitudEntity.getEstado() == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud ya cerrada.");
        }

        solicitudEntity.setEstado(nuevoEstado);
        jpaSolicitudRepository.save(solicitudEntity);
    }
}