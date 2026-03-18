package com.mgcss.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

public class FalloControladoTest {

    @Test
    void testDebeFallar() {
        fail("Fallo deliberado para CI");
    }
}