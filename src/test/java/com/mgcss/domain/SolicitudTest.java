package com.mgcss.domain;
//
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class SolicitudTest {

    // --- TESTS OBLIGATORIOS DEL HANDOUT (REGLAS DE NEGOCIO) ---

    @Test
    @DisplayName("RN: No se puede cerrar una solicitud si no está en EN_PROCESO")
    void noSePuedeCerrarSolicitudSiNoEstaEnProceso() {
        Cliente cliente = new Cliente(1, "Juan", "juan@test.com", TipoCliente.STANDARD);
        Solicitud s = new Solicitud(101, cliente, "Reparación PC");
        
        // La solicitud nace en ABIERTA, por lo que debe fallar al cerrar
        assertThrows(IllegalStateException.class, s::cerrarSolicitud);
    }

    @Test
    @DisplayName("RN: Solo se puede asignar un técnico activo")
    void soloSePuedeAsignarTecnicoActivo() {
        Cliente cliente = new Cliente(1, "Juan", "juan@test.com", TipoCliente.STANDARD);
        Solicitud s = new Solicitud(101, cliente, "Reparación PC");
        Tecnico inactivo = new Tecnico(1, "Pedro", EspecialidadTecnico.SOPORTE, false);

        assertThrows(IllegalArgumentException.class, () -> s.asignarTecnico(inactivo));
    }

    @Test
    @DisplayName("RN: Asignación válida de técnico activo")
    void asignacionTecnicoActivoFunciona() {
        // [cite: 221, 223, 226]
        Cliente cliente = new Cliente(1, "Juan", "juan@test.com", TipoCliente.STANDARD);
        Solicitud s = new Solicitud(101, cliente, "Reparación PC");
        Tecnico activo = new Tecnico(2, "Ana", EspecialidadTecnico.MANTENIMIENTO, true);

        s.asignarTecnico(activo);
        
        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        assertEquals(activo, s.getTecnicoAsignado());
    }

    //TESTS PARA ALCANZAR EL >80% COVERAGE (GETTERS, SETTERS Y ENUMS)

    @Test
    @DisplayName("Cobertura completa de la entidad Solicitud")
    void testSolicitudAtributosYFlujo() {
        Cliente cliente = new Cliente(1, "Juan", "juan@test.com", TipoCliente.STANDARD);
        Solicitud s = new Solicitud(101, cliente, "Descripción de prueba");
        Tecnico t = new Tecnico(2, "Ana", EspecialidadTecnico.MANTENIMIENTO, true);

        assertEquals(101, s.getId());
        assertEquals(cliente, s.getCliente());
        assertEquals("Descripción de prueba", s.getDescripcion());
        assertEquals(LocalDate.now(), s.getFechaCreacion());
        assertNull(s.getFechaCierre());

        // Flujo a CERRADA
        s.asignarTecnico(t);
        s.cerrarSolicitud();
        
        assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
        assertEquals(LocalDate.now(), s.getFechaCierre());
    }

    @Test
    @DisplayName("Cobertura completa de la entidad Cliente")
    void testClienteGettersYSetters() {
        Cliente c = new Cliente(1, "Empresa A", "info@empresa.com", TipoCliente.STANDARD);
        
        assertEquals(1, c.getId());
        assertEquals("Empresa A", c.getNombre());
        assertEquals("info@empresa.com", c.getEmail());
        assertEquals(TipoCliente.STANDARD, c.getTipo());

        // Test de setters (importante para cobertura)
        c.setEmail("nuevo@empresa.com");
        c.setTipo(TipoCliente.PREMIUM);
        
        assertEquals("nuevo@empresa.com", c.getEmail());
        assertEquals(TipoCliente.PREMIUM, c.getTipo());
    }

    @Test
    @DisplayName("Cobertura completa de la entidad Técnico")
    void testTecnicoGettersYSetters() {
        Tecnico t = new Tecnico(1, "Ramón", EspecialidadTecnico.REPARACIONES, true);
        
        assertEquals(1, t.getId());
        assertEquals("Ramón", t.getNombre());
        assertEquals(EspecialidadTecnico.REPARACIONES, t.getEspecialidad());
        assertTrue(t.isActivo());

        t.setActivo(false);
        t.setEspecialidad(EspecialidadTecnico.SOPORTE);
        
        assertFalse(t.isActivo());
        assertEquals(EspecialidadTecnico.SOPORTE, t.getEspecialidad());
    }

    @Test
    @DisplayName("Cobertura de métodos internos de Enums")
    void testEnumsCoverage() {
        // SonarQube requiere ejecutar values() y valueOf() para el 100% en Enums
        assertNotNull(EstadoSolicitud.values());
        assertEquals(EstadoSolicitud.ABIERTA, EstadoSolicitud.valueOf("ABIERTA"));
        
        assertNotNull(TipoCliente.values());
        assertEquals(TipoCliente.PREMIUM, TipoCliente.valueOf("PREMIUM"));
        
        assertNotNull(EspecialidadTecnico.values());
        assertEquals(EspecialidadTecnico.MANTENIMIENTO, EspecialidadTecnico.valueOf("MANTENIMIENTO"));
    }
    /*
     * 	Haz clic derecho sobre tu proyecto mgcss-track-L2-G1.

		Selecciona Run As → Maven build... (el que tiene los puntos suspensivos).

		En la ventana que se abre, en el campo Goals, escribe exactamente:
		clean verify

		Haz clic en Run.
     */
}