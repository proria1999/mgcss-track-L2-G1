package com.mgcss.service;

import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.infrastructure.SolicitudRepository;
import com.mgcss.infrastructure.TecnicoRepository;

public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final TecnicoRepository tecnicoRepository;

    // Inyección por constructor (cumple con la restricción de no crear instancias manualmente)
    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public void crearSolicitud(Solicitud solicitud) {
        // Delegamos al repositorio la persistencia
        solicitudRepository.save(solicitud);
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        // DELEGAR AL DOMINIO: La lógica de cómo se asigna está en la entidad Solicitud
        solicitud.asignarTecnico(tecnico);
        
        solicitudRepository.save(solicitud);
    }

    public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // DELEGACIÓN: El servicio no sabe SI puede cambiar el estado, 
        // solo le pide a la solicitud que lo haga.
        solicitud.actualizarEstado(nuevoEstado);

        solicitudRepository.save(solicitud);
    }
    
    public void cerrarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("No existe la solicitud"));

        // Delegamos la validación y el cambio de fecha al dominio
        solicitud.cerrarSolicitud(); 

        solicitudRepository.save(solicitud);
    }
    
}
