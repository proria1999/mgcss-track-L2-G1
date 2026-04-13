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

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private SolicitudService solicitudService;

    @Test
    @DisplayName("RN: No se puede asignar un técnico inactivo")
    void soloSePuedeAsignarTecnicoActivo() {
        Solicitud s = new Solicitud(1, null, "Test");
        Tecnico inactivo = new Tecnico(2, "Pepe", EspecialidadTecnico.SOPORTE, false);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(inactivo));

        assertThrows(IllegalArgumentException.class, () -> solicitudService.asignarTecnico(1L, 2L));
    }

    @Test
    @DisplayName("RN: No se puede cerrar solicitud si no está EN_PROCESO")
    void noCerrarSiNoEstaEnProceso() {
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA); // No está en proceso

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        assertThrows(IllegalStateException.class, () -> solicitudService.cerrarSolicitud(1L));
    }

    @Test
    @DisplayName("RN: No se puede cambiar estado de una solicitud CERRADA")
    void noCambiarEstadoSiYaEstaCerrada() {
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.CERRADA);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        assertThrows(IllegalStateException.class, () -> {
            solicitudService.cambiarEstado(1L, EstadoSolicitud.ABIERTA);
        });
    }

    @Test
    @DisplayName("Verificar que se guarda la solicitud tras cambios válidos")
    void verificarGuardado() {
        Solicitud s = new Solicitud(1, null, "Test");
        s.setEstado(EstadoSolicitud.EN_PROCESO);

        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        solicitudService.cerrarSolicitud(1L);

        verify(solicitudRepository).save(s);
        assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
    }
}