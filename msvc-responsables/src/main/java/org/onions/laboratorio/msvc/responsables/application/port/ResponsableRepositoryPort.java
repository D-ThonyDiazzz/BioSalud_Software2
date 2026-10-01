package org.onions.laboratorio.msvc.responsables.application.port;

import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import java.util.List;
import java.util.Optional;

public interface ResponsableRepositoryPort {
    List<Responsable> listar();
    Optional<Responsable> porId(Long id);
    Responsable guardar(Responsable responsable);
    void eliminar(Long id);
    Optional<Responsable> porDocumento(String numeroDocumento);
    List<Responsable> listarPorIds(Iterable<Long> ids);

}