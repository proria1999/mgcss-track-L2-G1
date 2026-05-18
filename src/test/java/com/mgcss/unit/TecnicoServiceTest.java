package com.mgcss.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.dto.TecnicoRequestDTO;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.TecnicoService;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

    @Mock 
    private JpaTecnicoRepository tecnicoRepository;

    @InjectMocks 
    private TecnicoService tecnicoService;

    @Test
    void lanzarExcepcionSiNoExisteTecnico() {
        when(tecnicoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tecnicoService.buscarTecnicoOError(99L, "Error"));
    }

    @Test
    void listarTodosExito() {
        List<TecnicoEntity> listaMock = List.of(
            new TecnicoEntity(1L, "Juan", EspecialidadTecnico.REPARACIONES, true)
        );
        when(tecnicoRepository.findAll()).thenReturn(listaMock);

        List<TecnicoEntity> resultado = tecnicoService.listarTodos();

        assertEquals(1, resultado.size());
        verify(tecnicoRepository, times(1)).findAll();
    }
    
    @Test
    void crearTecnicoExito() {
        String nombre = "Pedro";
        EspecialidadTecnico esp = EspecialidadTecnico.SOPORTE;
        TecnicoEntity tecnicoEsperado = new TecnicoEntity(1L, nombre, EspecialidadTecnico.MANTENIMIENTO, false);

        when(tecnicoRepository.save(any(TecnicoEntity.class))).thenReturn(tecnicoEsperado);

        TecnicoEntity resultado = tecnicoService.crearTecnico(nombre, esp);

        assertNotNull(resultado);
        assertEquals(nombre, resultado.getNombre());
        assertEquals(EspecialidadTecnico.MANTENIMIENTO, resultado.getEspecialidad());
        verify(tecnicoRepository, times(1)).save(any(TecnicoEntity.class));
    }

    @Test
    void crearTecnicoNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> 
            tecnicoService.crearTecnico("", EspecialidadTecnico.SOPORTE)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            tecnicoService.crearTecnico(null, EspecialidadTecnico.SOPORTE)
        );
        verify(tecnicoRepository, never()).save(any(TecnicoEntity.class));
    }
}