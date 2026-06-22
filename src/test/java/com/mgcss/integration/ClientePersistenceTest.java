package com.mgcss.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.domain.TipoCliente; //
import com.mgcss.infrastructure.persistence.JpaClienteRepository; //
import com.mgcss.infrastructure.persistence.ClienteEntity; //

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@Tag("integration")
@ActiveProfiles("test")
class ClientePersistenceTest {

    @Autowired
    private JpaClienteRepository repository; // esto es una prueba

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void debeGuardarYRecuperarCliente() {
        // Arrange
        ClienteEntity cliente = new ClienteEntity(0, "Juan", "juan@test.com", TipoCliente.STANDARD); //

        // Act
        ClienteEntity guardado = repository.save(cliente); //

        entityManager.flush();
        entityManager.clear();

        Optional<ClienteEntity> recuperado = repository.findById(guardado.getId()); //

        // Assert
        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getId()).isEqualTo(guardado.getId());
        assertThat(recuperado.get().getNombre()).isEqualTo("Juan");
    }
}