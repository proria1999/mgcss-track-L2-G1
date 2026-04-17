
package com.mgcss.domain;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void testSolicitudFullCoverage() {
        // Datos de prueba
        Cliente cliente = new Cliente(0, null, null, null);
        Tecnico tecnico = new Tecnico(0, null, null, false);
        LocalDate fecha = LocalDate.now();

        // Test del constructor (Cubre líneas 15-23)
        Solicitud solicitud = new Solicitud(1, cliente, "Descripción");

        // Test de setters (Cubre marcas rojas de set...)
        solicitud.setId(100);
        solicitud.setCliente(cliente);
        solicitud.setDescripcion("Nueva descripción");
        solicitud.setFechaCreacion(fecha);
        solicitud.setEstado(EstadoSolicitud.ABIERTA);
        solicitud.setTecnicoAsignado(tecnico);
        solicitud.setFechaCierre(fecha);

        // Test de getters (Cubre marcas rojas de get...)
        assertEquals(100, solicitud.getId());
        assertEquals(cliente, solicitud.getCliente());
        assertEquals("Nueva descripción", solicitud.getDescripcion());
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals(EstadoSolicitud.ABIERTA, solicitud.getEstado());
        assertEquals(tecnico, solicitud.getTecnicoAsignado());
        assertEquals(fecha, solicitud.getFechaCierre());
    }
}