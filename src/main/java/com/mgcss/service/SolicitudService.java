package com.mgcss.service;

import java.time.LocalDate;

import com.mgcss.domain.EstadoSolicitud;
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
        SolicitudEntity solicitudEntity = buscarSolicitudOError(solicitudId, "Solicitud no encontrada");

        TecnicoEntity tecnicoEntity = jpaTecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        if (!tecnicoEntity.isActivo()) {
            throw new IllegalArgumentException("Solo se puede asignar un técnico activo.");
        }

        solicitudEntity.setTecnicoAsignado(tecnicoEntity);
        solicitudEntity.registrarCambioEstado(EstadoSolicitud.EN_PROCESO);
        jpaSolicitudRepository.save(solicitudEntity);
    }

    // Extract Method
	private SolicitudEntity buscarSolicitudOError(Long solicitudId, String excepcion) {
		return jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException(excepcion));
	}
    
    public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        SolicitudEntity solicitudEntity = buscarSolicitudOError(solicitudId, "Solicitud no encontrada");

        // Validación de negocio: No se puede cambiar el estado de una cerrada
        if (solicitudEntity.getEstado() == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud ya cerrada.");
        }

        solicitudEntity.registrarCambioEstado(nuevoEstado);
        jpaSolicitudRepository.save(solicitudEntity);
    }
    
    public void reabrirSolicitud(Long solicitudId) {
        SolicitudEntity solicitudEntity = buscarSolicitudOError(solicitudId, "Solicitud no encontrada");

        // REGLA: Solo se puede reabrir si está CERRADA
        if (solicitudEntity.getEstado() != EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Solo se puede reabrir una solicitud CERRADA.");
        }

        // Cambio de estado y registro en histórico
        solicitudEntity.registrarCambioEstado(EstadoSolicitud.EN_PROCESO);
        jpaSolicitudRepository.save(solicitudEntity);
    }

    public void cerrarSolicitud(Long solicitudId) {
        SolicitudEntity solicitudEntity = buscarSolicitudOError(solicitudId, "No existe la solicitud");
        
        if (solicitudEntity.getEstado() != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede cerrar si está EN_PROCESO.");
        }

        solicitudEntity.registrarCambioEstado(EstadoSolicitud.CERRADA);
        solicitudEntity.setFechaCierre(LocalDate.now());
        jpaSolicitudRepository.save(solicitudEntity);
    }
}