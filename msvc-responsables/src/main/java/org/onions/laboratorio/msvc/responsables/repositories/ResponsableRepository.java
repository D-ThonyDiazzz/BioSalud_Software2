package org.onions.laboratorio.msvc.responsables.repositories;

import org.onions.laboratorio.msvc.responsables.models.entity.Responsable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ResponsableRepository extends CrudRepository<Responsable, Long> {

    Optional<Responsable> findByDocumentoIdentidad_NumeroDocumento(String numeroDocumento);
}
