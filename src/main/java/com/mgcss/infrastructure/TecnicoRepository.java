package com.mgcss.infrastructure;

import java.util.Optional;
import com.mgcss.domain.Tecnico;

public interface TecnicoRepository {
    Optional<Tecnico> findById(Long id);
    Tecnico save(Tecnico tecnico);
}