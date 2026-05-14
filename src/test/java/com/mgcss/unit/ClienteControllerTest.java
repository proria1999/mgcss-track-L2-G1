package com.mgcss.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.controller.ClienteController;
import com.mgcss.domain.TipoCliente; //
import com.mgcss.infrastructure.persistence.ClienteEntity; //
import com.mgcss.service.ClienteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeConsultarClienteExistente() throws Exception {
        ClienteEntity deRetorno = new ClienteEntity(1L, "Cliente S.A.", "cliente@test.com", TipoCliente.PREMIUM); //

        when(clienteService.buscarClienteOError(eq(1L), any())).thenReturn(deRetorno);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cliente S.A."))
                .andExpect(jsonPath("$.email").value("cliente@test.com"));
    }

    @Test
    void debeListarClientes() throws Exception {
        ClienteEntity cliente = new ClienteEntity(1L, "Cliente S.A.", "cliente@test.com", TipoCliente.STANDARD); //

        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cliente S.A."));
    }
}