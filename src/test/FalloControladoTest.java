package com.mgcss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FalloControladoTest {

    @Test
    void testFalloControlado() {
        assertEquals(1, 2); // Esto siempre falla
    }
}