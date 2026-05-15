package com.mgcss.unit;

import com.mgcss.dto.ClienteRequestDTO;
import com.mgcss.dto.ClienteResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteDTOTest {

    @Test
    void testClienteRequestDTO() {
        // Test constructor vacío y setters
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNombre("Test");
        dto.setEmail("test@test.com");
        
        // Test constructor con parámetros y getters
        ClienteRequestDTO dto2 = new ClienteRequestDTO("Test", "test@test.com");
        
        assertEquals("Test", dto.getNombre());
        assertEquals("test@test.com", dto.getEmail());
        assertEquals(dto.getNombre(), dto2.getNombre());
    }

    @Test
    void testClienteResponseDTO() {
        // Test constructor con parámetros
        ClienteResponseDTO dto = new ClienteResponseDTO(1L, "Nombre", "email@test.com");
        
        // Test constructor vacío y setters
        ClienteResponseDTO dtoEmpty = new ClienteResponseDTO();
        dtoEmpty.setId(2L);
        dtoEmpty.setNombre("Otro");
        dtoEmpty.setEmail("otro@test.com");

        assertEquals(1L, dto.getId());
        assertEquals("Nombre", dto.getNombre());
        assertEquals("email@test.com", dto.getEmail());
    }
}