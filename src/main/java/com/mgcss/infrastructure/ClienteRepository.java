package com.mgcss.infrastructure;

import com.mgcss.domain.Cliente;
import java.util.Optional;

public interface ClienteRepository{

	Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
}

