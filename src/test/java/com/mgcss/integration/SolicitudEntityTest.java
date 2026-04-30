
package com.mgcss.integration;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.infrastructure.persistence.TecnicoEntity;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudEntityTest {

    @Test
    void testCoberturaCompletaDominio() {
        // 1. Preparación de datos
        ClienteEntity clienteInicial = new ClienteEntity(0, null, null, null);
        ClienteEntity clienteNuevo = new ClienteEntity(0, null, null, null);
        TecnicoEntity tecnico = new TecnicoEntity(0, null, null, false);
        LocalDate fechaCualquiera = LocalDate.of(2026, 1, 1);
        String desc = "Fallo en sistema";

        // 2. Test del Constructor 
        SolicitudEntity solicitud = new SolicitudEntity();

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

        solicitud.setTecnicoAsignado(null);
        assertEquals(tecnico, solicitud.getTecnicoAsignado());

        solicitud.setFechaCierre(fechaCualquiera);
        assertEquals(fechaCualquiera, solicitud.getFechaCierre());
    }
}