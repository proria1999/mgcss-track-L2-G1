package com.mgcss.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.*;
import com.mgcss.infrastructure.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock private SolicitudRepository solicitudRepository;
    @Mock private TecnicoRepository tecnicoRepository;
    @InjectMocks private SolicitudService solicitudService;

    @Test
    @DisplayName("RN: No se puede asignar un técnico inactivo")
    void soloSePuedeAsignarTecnicoActivo() {
        // Arrange
        Solicitud s = new Solicitud(1, null, "Test");
        Tecnico inactivo = new Tecnico(2, "Pepe", EspecialidadTecnico.SOPORTE, false);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(inactivo));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> solicitudService.asignarTecnico(1L, 2L));
        
        // Verify: Se comprueba que NUNCA se llamó a save debido al error 
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("RN: No se puede cerrar solicitud si no está EN_PROCESO")
    void noCerrarSiNoEstaEnProceso() {
        // Arrange
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA); 
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> solicitudService.cerrarSolicitud(1L));
        
        // Verify
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("RN: No se puede cambiar estado de una solicitud CERRADA")
    void noCambiarEstadoSiYaEstaCerrada() {
        // Arrange
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.CERRADA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);
        });

        // Verify
        verify(solicitudRepository, never()).save(any(Solicitud.class));
    }

    @Test
    @DisplayName("Verificar que se guarda la solicitud tras cambios válidos")
    void verificarGuardado() {
        // Arrange
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.EN_PROCESO);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act
        solicitudService.cerrarSolicitud(1L);

        // Verify: Comportamiento observable 
        verify(solicitudRepository, times(1)).save(s);
        assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
    }
}