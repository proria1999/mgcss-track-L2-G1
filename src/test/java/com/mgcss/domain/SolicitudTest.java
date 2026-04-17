
package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void testCoberturaCompletaDominio() {
        // 1. Preparación de datos
        Cliente clienteInicial = new Cliente(0, null, null, null);
        Cliente clienteNuevo = new Cliente(0, null, null, null);
        Tecnico tecnico = new Tecnico(0, null, null, false);
        LocalDate fechaCualquiera = LocalDate.of(2026, 1, 1);
        String desc = "Fallo en sistema";

        // 2. Test del Constructor (Cubre líneas 15-23 y estado inicial)
        Solicitud solicitud = new Solicitud(1, clienteInicial, desc);

        assertEquals(1, solicitud.getId());
        assertEquals(clienteInicial, solicitud.getCliente());
        assertEquals(desc, solicitud.getDescripcion());
        assertNotNull(solicitud.getFechaCreacion()); // Verifica asignación de LocalDate.now()
        assertEquals(EstadoSolicitud.ABIERTA, solicitud.getEstado());
        assertNull(solicitud.getTecnicoAsignado());
        assertNull(solicitud.getFechaCierre());

        // 3. Test de Setters y Getters (Elimina todas las marcas rojas)
        solicitud.setId(500);
        assertEquals(500, solicitud.getId());

        solicitud.setCliente(clienteNuevo);
        assertEquals(clienteNuevo, solicitud.getCliente());

        solicitud.setDescripcion("Nueva descripción técnica");
        assertEquals("Nueva descripción técnica", solicitud.getDescripcion());

        solicitud.setFechaCreacion(fechaCualquiera);
        assertEquals(fechaCualquiera, solicitud.getFechaCreacion());

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        assertEquals(EstadoSolicitud.EN_PROCESO, solicitud.getEstado());

        solicitud.setTecnicoAsignado(tecnico);
        assertEquals(tecnico, solicitud.getTecnicoAsignado());

        solicitud.setFechaCierre(fechaCualquiera);
        assertEquals(fechaCualquiera, solicitud.getFechaCierre());
    }
}