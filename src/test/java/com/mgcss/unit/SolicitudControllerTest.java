package com.mgcss.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.controller.SolicitudController;
import com.mgcss.dto.SolicitudRequestDTO;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.service.SolicitudService;
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

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SolicitudService solicitudService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeCrearSolicitudYRetornar200() throws Exception {
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setClienteId(1L);
        request.setDescripcion("Incidencia de conectividad en oficina");

        ClienteEntity cliente = new ClienteEntity(1L, "Cliente S.A.", "cliente@test.com", null);
        SolicitudEntity deRetorno = new SolicitudEntity(1L, cliente, "Incidencia de conectividad en oficina");

        when(solicitudService.crearSolicitud(eq(1L), any(String.class))).thenReturn(deRetorno);

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Incidencia de conectividad en oficina"))
                .andExpect(jsonPath("$.clienteNombre").value("Cliente S.A."))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void debeRetornar400CuandoLaDescripcionEsCorta() throws Exception {
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setClienteId(1L);
        request.setDescripcion("Corta"); // Menor a 10 caracteres

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}