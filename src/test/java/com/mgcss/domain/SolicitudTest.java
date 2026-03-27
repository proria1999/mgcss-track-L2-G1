package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void noSePuedeCerrarSiNoEstaEnProceso() {
        Solicitud solicitud = new Solicitud(EstadoSolicitud.ABIERTA);

        assertThrows(IllegalStateException.class, solicitud::cerrar);
    }

    @Test
    void sePuedeCerrarSiEstaEnProceso() {
        Solicitud solicitud = new Solicitud(EstadoSolicitud.EN_PROCESO);

        solicitud.cerrar();

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    @Test
    void soloSePuedeAsignarTecnicoActivo() {
        Tecnico activo = new Tecnico(true);
        Tecnico inactivo = new Tecnico(false);

        Solicitud solicitud = new Solicitud(EstadoSolicitud.ABIERTA);

        solicitud.asignarTecnico(activo);

        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.asignarTecnico(inactivo);
        });
    }
}