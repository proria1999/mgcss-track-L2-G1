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

    @Test
    void noSePuedeIniciarSiNoEstaAbierta() {
        Solicitud s = new Solicitud(EstadoSolicitud.EN_PROCESO);

        assertThrows(IllegalStateException.class, s::iniciarProceso);
    }

    @Test
    void sePuedeIniciarDesdeAbierta() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);

        s.iniciarProceso();

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
    }

    @Test
    void estaCerradaDevuelveTrueSiCerrada() {
        Solicitud s = new Solicitud(EstadoSolicitud.EN_PROCESO);
        s.cerrar();

        assertTrue(s.estaCerrada());
    }

    @Test
    void estaCerradaDevuelveFalseSiNoCerrada() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);

        assertFalse(s.estaCerrada());
    }

    @Test
    void tieneTecnicoAsignadoFunciona() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);

        assertFalse(s.tieneTecnicoAsignado());

        Tecnico t = new Tecnico(true);
        s.asignarTecnico(t);

        assertTrue(s.tieneTecnicoAsignado());
    }

    @Test
    void cerrarDosVecesFalla() {
        Solicitud s = new Solicitud(EstadoSolicitud.EN_PROCESO);
        s.cerrar();

        assertThrows(IllegalStateException.class, s::cerrar);
    }

    @Test
    void asignarTecnicoNuloFalla() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);

        assertThrows(NullPointerException.class, () -> {
            s.asignarTecnico(null);
        });
    }
}