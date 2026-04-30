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
import com.mgcss.domain.Tecnico;
import com.mgcss.infrastructure.*;
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
    // FALLO MOCKITO
    /*
    @Test
    void deberiaLanzarExcepcionSiTecnicoInactivo() {
        // 1. SETUP: Preparar el escenario con un técnico inactivo
        int tecnicoId = 1;
        Tecnico tecnicoInactivo = new Tecnico(tecnicoId, "Juan", EspecialidadTecnico.MANTENIMIENTO, false);
        Solicitud solicitud = new Solicitud(1, );

        // Instruimos al mock para devolver el técnico inactivo
        when(repository.findById(tecnicoId)).thenReturn(Optional.of(tecnicoInactivo));

        // 2. ASSERT & ACT: Ejecutar esperando la "explosión" 
        // Usamos assertThrows para capturar la excepción de la regla de negocio 
        assertThrows(IllegalArgumentException.class, () -> {
            service.asignar(solicitud, tecnicoId);
        });

        // 3. VERIFY: Garantizar la ausencia de efectos secundarios
        // Verificamos que NUNCA se llamó al método save si hubo un error
        verify(repository, never()).save(any());
        
    }
*/
    
}