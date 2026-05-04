package com.mgcss.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock 
    private JpaSolicitudRepository solicitudRepository;
    @Mock 
    private JpaTecnicoRepository tecnicoRepository;
    @InjectMocks 
    private SolicitudService solicitudService;

    @Test
    @DisplayName("RN: No se puede asignar un técnico inactivo")
    void soloSePuedeAsignarTecnicoActivo() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        TecnicoEntity inactivo = new TecnicoEntity(2, "Pepe", EspecialidadTecnico.SOPORTE, false);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(inactivo));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> solicitudService.asignarTecnico(1L, 2L));
        
        // Verify: Se comprueba que NUNCA se llamó a save debido al error 
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("RN: No se puede cerrar solicitud si no está EN_PROCESO")
    void noCerrarSiNoEstaEnProceso() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA); 
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> solicitudService.cerrarSolicitud(1L));
        
        // Verify
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("RN: No se puede cambiar estado de una solicitud CERRADA")
    void noCambiarEstadoSiYaEstaCerrada() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.CERRADA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);
        });

        // Verify
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("Verificar que se guarda la solicitud tras cambios válidos")
    void verificarGuardado() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.EN_PROCESO);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act
        solicitudService.cerrarSolicitud(1L);

        // Verify: Comportamiento observable 
        verify(solicitudRepository, times(1)).save(s);
        assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
    }
    
    @Test
    @DisplayName("EXITO: Asignar técnico activo actualiza estado y guarda")
    void asignarTecnicoExito() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        TecnicoEntity activo = new TecnicoEntity(2, "Juan", EspecialidadTecnico.MANTENIMIENTO, true);
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(activo));

        solicitudService.asignarTecnico(1L, 2L);

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        assertEquals(activo, s.getTecnicoAsignado());
        verify(solicitudRepository, times(1)).save(s); // Cubre líneas 32-34 de tu imagen
    }

    @Test
    @DisplayName("EXITO: Cambiar estado guarda la solicitud")
    void cambiarEstadoExito() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        verify(solicitudRepository, times(1)).save(s); // Cubre líneas 59-60 de tu imagen
    }

    @Test
    @DisplayName("ERROR: Lanzar RuntimeException si no existe la solicitud")
    void lanzarExcepcionSiNoExisteSolicitud() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> solicitudService.cerrarSolicitud(99L));
        // Cubre los bloques .orElseThrow() de las líneas 22, 39 y 52
    }
    
}