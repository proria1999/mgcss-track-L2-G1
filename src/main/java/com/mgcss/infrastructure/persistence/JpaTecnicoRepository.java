package com.mgcss.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTecnicoRepository extends JpaRepository<TecnicoEntity, Long>{
	 TecnicoEntity save(TecnicoEntity tecnico);
	 Optional<TecnicoEntity> findById(Long id);
}
