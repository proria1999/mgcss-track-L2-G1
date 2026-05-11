package com.mgcss.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.controller.SolicitudController;
import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.dto.SolicitudRequestDTO;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@Import(ObjectMapper.class)
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
        request.setDescripcion("Corta");

        mockMvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void debeConsultarSolicitudExistente() throws Exception {
        // Preparar los datos
        ClienteEntity cliente = new ClienteEntity(1L, "Cliente S.A.", "cliente@test.com", null);
        SolicitudEntity deRetorno = new SolicitudEntity(1L, cliente, "Fallo de red");

        // Configurar el Mock usando any() para evitar fallos por argumentos exactos
        // Importante: Si el método recibe un Long y un String, usa any() para ambos
        when(solicitudService.buscarSolicitudOError(eq(1L), any())).thenReturn(deRetorno);

        // Ejecutar y verificar
        mockMvc.perform(get("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Fallo de red"))
                .andExpect(jsonPath("$.clienteNombre").value("Cliente S.A."));
    }

    @Test
    void debeListarSolicitudes() throws Exception {
        ClienteEntity cliente = new ClienteEntity(1L, "Cliente S.A.", "cliente@test.com", null);
        SolicitudEntity solicitud = new SolicitudEntity(1L, cliente, "Fallo de red");

        when(solicitudService.listarTodas()).thenReturn(List.of(solicitud));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Fallo de red"));
    }

    @Test
    void debeAsignarTecnico() throws Exception {
        doNothing().when(solicitudService).asignarTecnico(1L, 2L);

        mockMvc.perform(put("/api/solicitudes/1/tecnico/2"))
                .andExpect(status().isOk());
    }

    @Test
    void debeCambiarEstado() throws Exception {
        doNothing().when(solicitudService).cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        mockMvc.perform(put("/api/solicitudes/1/estado")
                        .param("estado", "EN_PROCESO"))
                .andExpect(status().isOk());
    }

    @Test
    void debeReabrirSolicitud() throws Exception {
        doNothing().when(solicitudService).reabrirSolicitud(1L);

        mockMvc.perform(patch("/api/solicitudes/1/reabrir"))
                .andExpect(status().isOk());
    }
}