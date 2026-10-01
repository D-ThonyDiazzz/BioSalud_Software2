package org.onions.laboratorio.msvc.responsables.infraestructure.repository;

import org.onions.laboratorio.msvc.responsables.infraestructure.entity.ResponsableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsableJpaRepository extends JpaRepository<ResponsableEntity, Long> {

    Optional<ResponsableEntity> findByDocumentoIdentidad_NumeroDocumento(String numeroDocumento);
}