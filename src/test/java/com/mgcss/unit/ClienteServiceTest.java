package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.TipoCliente; //
import com.mgcss.infrastructure.persistence.ClienteEntity; //
import com.mgcss.infrastructure.persistence.JpaClienteRepository;
import com.mgcss.service.ClienteService; //

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock 
    private JpaClienteRepository clienteRepository; //
    @InjectMocks 
    private ClienteService clienteService;

    @Test
    void lanzarExcepcionSiNoExisteCliente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty()); //

        assertThrows(RuntimeException.class, () -> clienteService.buscarClienteOError(99L, any()));
    }

    @Test
    void listarTodosExito() {
        List<ClienteEntity> listaMock = List.of(
            new ClienteEntity(1L, "Cliente A", "a@test.com", TipoCliente.STANDARD) //
        );
        when(clienteRepository.findAll()).thenReturn(listaMock); //

        List<ClienteEntity> resultado = clienteService.listarTodos();

        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findAll(); //
    }
    
    @Test
    void crearCliente_DebeGuardarExitosamente() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre("Nuevo Cliente");
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(cliente);

        ClienteEntity resultado = clienteService.crearCliente("Nuevo Cliente", "STANDARD");

        assertNotNull(resultado);
        assertEquals("Nuevo Cliente", resultado.getNombre());
        verify(clienteRepository).save(any());
    }

    @Test
    void crearCliente_NombreVacio_DebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> 
            clienteService.crearCliente("", "STANDARD")
        );
        assertThrows(IllegalArgumentException.class, () -> 
            clienteService.crearCliente(null, "STANDARD")
        );
    }

    @Test
    void listarTodos_DebeRetornarLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(new ClienteEntity()));
        List<ClienteEntity> lista = clienteService.listarTodos();
        assertFalse(lista.isEmpty());
    }

    @Test
    void obtenerPorId_CuandoExiste_RetornaCliente() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test", "test@mail.com", TipoCliente.STANDARD);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteEntity resultado = clienteService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerPorId_CuandoNoExiste_LanzaExcepcion() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            clienteService.obtenerPorId(99L)
        );
        assertTrue(exception.getMessage().contains("Cliente no encontrado"));
    }
}