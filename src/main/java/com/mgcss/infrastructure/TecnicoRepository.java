package com.mgcss.infrastructure;

import org.springframework.data.repository.CrudRepository; // o JpaRepository

import com.mgcss.domain.Tecnico;


public interface TecnicoRepository extends CrudRepository<Tecnico, Long> {
    // findById(Long id) ya está incluido aquí por defecto.
    // Devuelve un Optional<Tecnico>.
}