package com.mgcss.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    void debeGuardarYRecuperarSolicitud() {
        // Arrange
        SolicitudEntity solicitud = new SolicitudEntity();
        // Configura los datos necesarios de la entidad

        // Act
        SolicitudEntity guardada = repository.save(solicitud);
        Optional<SolicitudEntity> recuperada = repository.findById(guardada.getId());

        // Assert
        assertThat(recuperada).isPresent();
        assertThat(recuperada.get().getId()).isEqualTo(guardada.getId());
    }
}
