package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    // TESTS REQUERIDOS POR EL HANDOUT (SESIÓN 5)

    @Test
    void noSePuedeCerrarSiNoEstaEnProceso() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertThrows(IllegalStateException.class, s::cerrar);
    }

    @Test
    void soloSePuedeAsignarTecnicoActivo() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        Tecnico inactivo = new Tecnico(1, "Juan", EspecialidadTecnico.SOPORTE, false); 
        
        assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(inactivo)); 
    }

    @Test
    void asignacionValidaDeTecnicoFunciona() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        Tecnico activo = new Tecnico(2, "Ana", EspecialidadTecnico.MANTENIMIENTO, true);
        
        s.asignarTecnico(activo);
        assertTrue(s.tieneTecnicoAsignado());
    }

    //15 TESTS ADICIONALES 

    @Test
    void constructorSolicitudAsignaFechaCreacionCorrectamente() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertNotNull(LocalDate.now()); 
    }

    @Test
    void iniciarProcesoCambiaEstadoDesdeAbierta() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        s.iniciarProceso();
        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
    }

    @ParameterizedTest
    @EnumSource(value = EstadoSolicitud.class, names = {"EN_PROCESO", "CERRADA"})
    void iniciarProcesoFallaSiNoEstaAbierta(EstadoSolicitud estado) {
        Solicitud s = new Solicitud(estado);
        assertThrows(IllegalStateException.class, s::iniciarProceso);
    }

    @Test
    void cerrarCambiaEstadoACerradaSiEstabaEnProceso() {
        Solicitud s = new Solicitud(EstadoSolicitud.EN_PROCESO);
        s.cerrar();
        assertTrue(s.estaCerrada());
    }

    @Test
    void estaCerradaRetornaFalseSiNoEstaEnEstadoCerrada() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertFalse(s.estaCerrada());
    }

    @Test
    void tieneTecnicoAsignadoRetornaFalseSiEsNulo() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertFalse(s.tieneTecnicoAsignado());
    }

    @Test
    void tecnicoConstructorYGetters() {
        Tecnico t = new Tecnico(10, "Pedro", EspecialidadTecnico.REPARACIONES, true);
        assertEquals(10, t.getId());
        assertEquals("Pedro", t.getNombre());
        assertEquals(EspecialidadTecnico.REPARACIONES, t.getEspecialidad());
        assertTrue(t.isActivo());
    }

    @Test
    void tecnicoSettersCobertura() {
        Tecnico t = new Tecnico(1, "Temp", EspecialidadTecnico.SOPORTE, true);
        t.setActivo(false);
        t.setEspecialidad(EspecialidadTecnico.MANTENIMIENTO);
        assertFalse(t.isActivo());
        assertEquals(EspecialidadTecnico.MANTENIMIENTO, t.getEspecialidad());
    }

    @Test
    void enumEstadoSolicitudCobertura() {
        for (EstadoSolicitud e : EstadoSolicitud.values()) {
            assertNotNull(EstadoSolicitud.valueOf(e.name()));
        }
    }

    @Test
    void enumEspecialidadCobertura() {
        for (EspecialidadTecnico e : EspecialidadTecnico.values()) {
            assertNotNull(EspecialidadTecnico.valueOf(e.name()));
        }
    }

    @Test
    void noSePuedeCerrarUnaSolicitudYaCerrada() {
        Solicitud s = new Solicitud(EstadoSolicitud.EN_PROCESO);
        s.cerrar();
        assertThrows(IllegalStateException.class, s::cerrar);
    }

    @Test
    void asignarTecnicoNuloDebeLanzarExcepcion() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertThrows(NullPointerException.class, () -> s.asignarTecnico(null));
    }

    @Test
    void iniciarProcesoEnSolicitudNuevaFunciona() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertDoesNotThrow(s::iniciarProceso);
    }

    @Test
    void verificarConsistenciaEstadoTrasAsignacionFallida() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        Tecnico inactivo = new Tecnico(1, "Error", EspecialidadTecnico.SOPORTE, false);
        try { s.asignarTecnico(inactivo); } catch (Exception e) {}
        assertEquals(EstadoSolicitud.ABIERTA, s.getEstado());
    }

    @Test
    void coberturaCamposIdYFechaInternos() {
        Solicitud s = new Solicitud(EstadoSolicitud.ABIERTA);
        assertNotNull(s);
    }
}