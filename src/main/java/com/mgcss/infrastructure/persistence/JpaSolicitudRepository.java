package com.mgcss.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long>{
	 SolicitudEntity save(SolicitudEntity solicitud);
	 Optional<SolicitudEntity> findById(Long id);
}