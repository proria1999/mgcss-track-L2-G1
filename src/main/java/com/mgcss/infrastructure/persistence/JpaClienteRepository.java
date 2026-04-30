package com.mgcss.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long>{
	ClienteEntity save(ClienteEntity solicitud);
	Optional<ClienteEntity> findById(Long id);
}