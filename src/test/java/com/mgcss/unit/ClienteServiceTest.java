package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}