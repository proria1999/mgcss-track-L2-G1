package com.mgcss.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.EspecialidadTecnico;
import com.mgcss.infrastructure.persistence.JpaTecnicoRepository;
import com.mgcss.infrastructure.persistence.TecnicoEntity;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@Tag("integration")
@ActiveProfiles("test")
class TecnicoPersistenceTest {

    @Autowired
    private JpaTecnicoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void debeGuardarYRecuperarTecnico() {
        // Arrange
        TecnicoEntity tecnico = new TecnicoEntity(0, "Juan", EspecialidadTecnico.SOPORTE, true);

        // Act
        TecnicoEntity guardado = repository.save(tecnico);

        entityManager.flush();
        entityManager.clear();

        Optional<TecnicoEntity> recuperado = repository.findById(guardado.getId());

        // Assert
        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getId()).isEqualTo(guardado.getId());
        assertThat(recuperado.get().getNombre()).isEqualTo("Juan");
    }
}