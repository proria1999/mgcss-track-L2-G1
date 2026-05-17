package com.mgcss.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.controller.TecnicoController;
import com.mgcss.domain.EspecialidadTecnico;
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
}