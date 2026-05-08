package com.mgcss.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.ClienteEntity;
import com.mgcss.infrastructure.persistence.JpaClienteRepository;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.SolicitudEntity;
import com.mgcss.infrastructure.persistence.TecnicoEntity;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock 
    private JpaSolicitudRepository solicitudRepository;
    @Mock 
    private JpaTecnicoRepository tecnicoRepository;
    @InjectMocks 
    private SolicitudService solicitudService;
    @Mock 
    private JpaClienteRepository clienteRepository;

    @Test
    @DisplayName("RN: No se puede asignar un técnico inactivo")
    void soloSePuedeAsignarTecnicoActivo() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        TecnicoEntity inactivo = new TecnicoEntity(2, "Pepe", EspecialidadTecnico.SOPORTE, false);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(inactivo));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> solicitudService.asignarTecnico(1L, 2L));
        
        // Verify: Se comprueba que NUNCA se llamó a save debido al error 
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("RN: No se puede cerrar solicitud si no está EN_PROCESO")
    void noCerrarSiNoEstaEnProceso() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA); 
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> solicitudService.cerrarSolicitud(1L));
        
        // Verify
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("RN: No se puede cambiar estado de una solicitud CERRADA")
    void noCambiarEstadoSiYaEstaCerrada() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.CERRADA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);
        });

        // Verify
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("Verificar que se guarda la solicitud tras cambios válidos")
    void verificarGuardado() {
        // Arrange
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.EN_PROCESO);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act
        solicitudService.cerrarSolicitud(1L);

        // Verify: Comportamiento observable 
        verify(solicitudRepository, times(1)).save(s);
        assertEquals(EstadoSolicitud.CERRADA, s.getEstado());
    }
    
    @Test
    @DisplayName("EXITO: Asignar técnico activo actualiza estado y guarda")
    void asignarTecnicoExito() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        TecnicoEntity activo = new TecnicoEntity(2, "Juan", EspecialidadTecnico.MANTENIMIENTO, true);
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));
        when(tecnicoRepository.findById(2L)).thenReturn(Optional.of(activo));

        solicitudService.asignarTecnico(1L, 2L);

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        assertEquals(activo, s.getTecnicoAsignado());
        verify(solicitudRepository, times(1)).save(s); // Cubre líneas 32-34 de tu imagen
    }

    @Test
    @DisplayName("EXITO: Cambiar estado guarda la solicitud")
    void cambiarEstadoExito() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test");
        s.setEstado(EstadoSolicitud.ABIERTA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);

        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        verify(solicitudRepository, times(1)).save(s); // Cubre líneas 59-60 de tu imagen
    }

    @Test
    @DisplayName("ERROR: Lanzar RuntimeException si no existe la solicitud")
    void lanzarExcepcionSiNoExisteSolicitud() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> solicitudService.cerrarSolicitud(99L));
        // Cubre los bloques .orElseThrow() de las líneas 22, 39 y 52
    }

    @Test
    @DisplayName("Reabrir solicitud cerrada cambia estado a EN_PROCESO")
    void reabrirSolicitudExito() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test Reabrir");
        s.setEstado(EstadoSolicitud.CERRADA);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act
        solicitudService.reabrirSolicitud(1L);

        // Assert
        assertEquals(EstadoSolicitud.EN_PROCESO, s.getEstado());
        verify(solicitudRepository, times(1)).save(s);
    }

    @Test
    @DisplayName("El historial registra los cambios en orden")
    void verificarHistorialEstados() {
        SolicitudEntity s = new SolicitudEntity(1, null, "Test Historial");
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act: Realizamos varios cambios
        solicitudService.cambiarEstado(1L, EstadoSolicitud.EN_PROCESO);
        solicitudService.cerrarSolicitud(1L);
        solicitudService.reabrirSolicitud(1L);

        // Assert: Ahora verificamos los 4 estados
        List<EstadoSolicitud> historial = s.getHistorialEstados();
        assertEquals(4, historial.size(), "El historial debe tener 4 estados");
        
        // Verificamos el orden cronológico exacto
        assertEquals(EstadoSolicitud.ABIERTA, historial.get(0));
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(1));
        assertEquals(EstadoSolicitud.CERRADA, historial.get(2));
        assertEquals(EstadoSolicitud.EN_PROCESO, historial.get(3));
    }
    
    @Test
    @DisplayName("EXITO: Listar todas devuelve una lista de solicitudes")
    void listarTodasExito() {
        // Arrange
        List<SolicitudEntity> listaMock = List.of(
            new SolicitudEntity(1L, null, "Error 1"),
            new SolicitudEntity(2L, null, "Error 2")
        );
        when(solicitudRepository.findAll()).thenReturn(listaMock);

        // Act
        List<SolicitudEntity> resultado = solicitudService.listarTodas();

        // Assert
        assertEquals(2, resultado.size());
        verify(solicitudRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("EXITO: Crear solicitud correctamente busca al cliente y la guarda")
    void crearSolicitudExito() {
        Long clienteId = 1L;
        String descripcion = "Incidencia de conectividad en oficina";
        ClienteEntity cliente = new ClienteEntity(clienteId, "Cliente S.A.", "cliente@test.com", null);
        SolicitudEntity solicitudEsperada = new SolicitudEntity(100L, cliente, descripcion);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(solicitudRepository.save(any(SolicitudEntity.class))).thenReturn(solicitudEsperada);

        SolicitudEntity resultado = solicitudService.crearSolicitud(clienteId, descripcion);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("Cliente S.A.", resultado.getCliente().getNombre());
        assertEquals(descripcion, resultado.getDescripcion());
        verify(solicitudRepository, times(1)).save(any(SolicitudEntity.class));
    }

    @Test
    @DisplayName("ERROR: Crear solicitud lanza excepción si el cliente no existe")
    void crearSolicitudClienteNoEncontrado() {
        Long clienteId = 99L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            solicitudService.crearSolicitud(clienteId, "Fallo en el sistema de red")
        );
        verify(solicitudRepository, never()).save(any(SolicitudEntity.class));
    }
}