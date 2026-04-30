
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
        ClienteEntity clienteNuevo = new ClienteEntity(0, null, null, null);
        TecnicoEntity tecnico = new TecnicoEntity(0, "Juan", null, true);
        LocalDate fechaCualquiera = LocalDate.of(2026, 1, 1);
        String desc = "Fallo en sistema";

        // 2. Test del Estado inicial
        SolicitudEntity solicitud = new SolicitudEntity();

        // Si tu entidad inicializa el ID en 0, asegúrate de que coincida
        assertEquals(0, solicitud.getId()); 

        // 3. Test de Setters y Getters
        solicitud.setId(500);
        assertEquals(500, solicitud.getId());

        solicitud.setCliente(clienteNuevo);
        assertEquals(clienteNuevo, solicitud.getCliente());

        solicitud.setDescripcion(desc);
        assertEquals(desc, solicitud.getDescripcion());

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