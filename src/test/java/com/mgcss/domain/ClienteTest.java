package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {
    @Test
    void testClienteFullCoverage() {
        // Test del constructor y estado inicial 
        Cliente cliente = new Cliente(1, "Usuario", "test@mail.com", TipoCliente.STANDARD);

        // Test de setters (Elimina marcas rojas)
        cliente.setId(10);
        cliente.setNombre("Actualizado");
        cliente.setEmail("nuevo@mail.com");
        // Nota: si existe setTipo, añadirlo aquí

        // Test de getters (Elimina marcas rojas)
        assertEquals(10, cliente.getId());
        assertEquals("Actualizado", cliente.getNombre());
        assertEquals("nuevo@mail.com", cliente.getEmail());
        assertEquals(TipoCliente.PREMIUM, cliente.getTipo());
    }
}