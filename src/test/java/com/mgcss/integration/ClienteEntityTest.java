package com.mgcss.integration;

import org.junit.jupiter.api.Test;
import com.mgcss.domain.TipoCliente; //
import com.mgcss.infrastructure.persistence.ClienteEntity; //
import static org.junit.jupiter.api.Assertions.*;

class ClienteEntityTest {

    @Test
    void testCoberturaCompletaDominio() {
        ClienteEntity cliente = new ClienteEntity(); //

        assertEquals(0, cliente.getId()); 

        cliente.setId(100);
        assertEquals(100, cliente.getId());

        cliente.setNombre("Carlos");
        assertEquals("Carlos", cliente.getNombre());

        cliente.setEmail("carlos@test.com");
        assertEquals("carlos@test.com", cliente.getEmail());

        cliente.setTipo(TipoCliente.PREMIUM); //
        assertEquals(TipoCliente.PREMIUM, cliente.getTipo());
    }
}