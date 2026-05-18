package com.mgcss.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.controller.TecnicoController;
import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.dto.TecnicoRequestDTO;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.TecnicoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@Import(ObjectMapper.class)
@WebMvcTest(TecnicoController.class)
class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TecnicoService tecnicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void debeConsultarTecnicoExistente() throws Exception {
        TecnicoEntity deRetorno = new TecnicoEntity(1L, "Juan", EspecialidadTecnico.SOPORTE, true);

        when(tecnicoService.buscarTecnicoOError(any(), any())).thenReturn(deRetorno);

        mockMvc.perform(get("/api/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.especialidad").value("SOPORTE"));
    }

    @Test
    void debeListarTecnicos() throws Exception {
        TecnicoEntity tecnico = new TecnicoEntity(1L, "Juan", EspecialidadTecnico.MANTENIMIENTO, true);

        when(tecnicoService.listarTodos()).thenReturn(List.of(tecnico));

        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }
    
    @Test
    void debeCrearTecnicoYRetornar200() throws Exception {
        TecnicoRequestDTO request = new TecnicoRequestDTO();
        request.setNombre("Pedro");
        request.setEspecialidad(EspecialidadTecnico.SOPORTE);

        TecnicoEntity deRetorno = new TecnicoEntity(1L, "Pedro", EspecialidadTecnico.MANTENIMIENTO, false);

        when(tecnicoService.crearTecnico(eq("Pedro"), any())).thenReturn(deRetorno);

        mockMvc.perform(post("/api/tecnicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.especialidad").value("MANTENIMIENTO"));
    }

    @Test
    void debeRetornar400CuandoElNombreEstaVacio() throws Exception {
        TecnicoRequestDTO request = new TecnicoRequestDTO();
        request.setNombre("");
        request.setEspecialidad(EspecialidadTecnico.SOPORTE);

        // Si el servicio lanza IllegalArgumentException, simulamos el error de validación
        when(tecnicoService.crearTecnico(eq(""), any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/tecnicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    
}