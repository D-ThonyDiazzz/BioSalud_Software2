package org.onions.laboratorio.msvc.pacientes.infrastructure.repository;

import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Long> {
    Optional<PacienteEntity> findByDocumentoIdentidad_NumeroDocumento(String numeroDocumento);

    @Query("select distinct p from PacienteEntity p left join fetch p.responsables where p.id = :id")
    Optional<PacienteEntity> buscarConResponsables(@Param("id") Long id);
}
