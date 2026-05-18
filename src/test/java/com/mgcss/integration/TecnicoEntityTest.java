package com.mgcss.integration;

import org.junit.jupiter.api.Test;
import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import static org.junit.jupiter.api.Assertions.*;

class TecnicoEntityTest {

    @Test
    void testCoberturaCompletaDominio() {
        TecnicoEntity tecnico = new TecnicoEntity();

        assertEquals(0, tecnico.getId()); 

        tecnico.setId(200);
        assertEquals(200, tecnico.getId());

        tecnico.setNombre("Pedro");
        assertEquals("Pedro", tecnico.getNombre());

        tecnico.setEspecialidad(EspecialidadTecnico.MANTENIMIENTO);
        assertEquals(EspecialidadTecnico.MANTENIMIENTO, tecnico.getEspecialidad());

        tecnico.setActivo(false);
        assertFalse(tecnico.isActivo());
    }
}