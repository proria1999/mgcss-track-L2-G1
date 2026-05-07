package com.mgcss.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.EstadoSolicitud;
import com.mgcss.infrastructure.persistence.JpaSolicitudRepository;
import com.mgcss.infrastructure.persistence.SolicitudEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


@DataJpaTest
@Tag("integration")
@ActiveProfiles("test")
class SolicitudPersistenceTest {

    @Autowired
    private JpaSolicitudRepository repository;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void debeGuardarYRecuperarSolicitud() {
        // Arrange
        SolicitudEntity solicitud = new SolicitudEntity();

        // Act
        SolicitudEntity guardada = repository.save(solicitud);
        
        // Forzamos la persistencia real y vaciamos la caché
        entityManager.flush();
        entityManager.clear();
        
        Optional<SolicitudEntity> recuperada = repository.findById(guardada.getId());

        // Assert
        assertThat(recuperada).isPresent();
        assertThat(recuperada.get().getId()).isEqualTo(guardada.getId());
    }
    @Test
    void debePersistirYRecuperarHistorialDeEstados() {
        // Fase 4: Verificar almacenamiento correcto del historial 
        SolicitudEntity solicitud = new SolicitudEntity();
        solicitud.registrarCambioEstado(EstadoSolicitud.ABIERTA);
        solicitud.registrarCambioEstado(EstadoSolicitud.EN_PROCESO);
        solicitud.registrarCambioEstado(EstadoSolicitud.CERRADA);
        
        SolicitudEntity guardada = repository.save(solicitud);

        // Forzamos la persistencia real y vaciamos la caché
        entityManager.flush();
        entityManager.clear();

        Optional<SolicitudEntity> recuperada = repository.findById(guardada.getId());

        assertThat(recuperada).isPresent();
        assertThat(recuperada.get().getHistorialEstados())
            .as("El historial debe persistirse correctamente en la tabla auxiliar")
            .hasSize(3)
            .containsExactly(
                EstadoSolicitud.ABIERTA, 
                EstadoSolicitud.EN_PROCESO, 
                EstadoSolicitud.CERRADA
            );
    }
}
