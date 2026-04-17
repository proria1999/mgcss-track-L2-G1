package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TecnicoTest {
    @Test
    void testTecnicoFullCoverage() {
        // Test del constructor y estado inicial 
        Tecnico tecnico = new Tecnico(1, "Juan", EspecialidadTecnico.REPARACIONES, true);

        tecnico.setId(5);
        tecnico.setNombre("Pedro");
        tecnico.setEspecialidad(EspecialidadTecnico.MANTENIMIENTO);
        tecnico.setActivo(false);


        assertEquals(5, tecnico.getId());
        assertEquals("Pedro", tecnico.getNombre());
        assertEquals(EspecialidadTecnico.MANTENIMIENTO, tecnico.getEspecialidad());
        assertFalse(tecnico.isActivo());
    }
}
