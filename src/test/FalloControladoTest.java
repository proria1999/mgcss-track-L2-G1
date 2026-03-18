package com.mgcss.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

public class FalloControladoTest {

    @Test
    void testDebeFallar() {
        // Este fallo es deliberado para cumplir con el Paso 5 de la Sesión 3
        fail("Fallo provocado para verificar la Integración Continua");
    }
}