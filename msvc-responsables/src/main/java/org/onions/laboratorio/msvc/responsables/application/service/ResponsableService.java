package org.onions.laboratorio.msvc.responsables.application.service;

import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import java.util.List;
import java.util.Optional;

public interface ResponsableService {
    List<Responsable> listar();
    Optional<Responsable> porId(Long id);
    Optional<Responsable> porDocumento(String numeroDocumento);
    Responsable guardar(Responsable responsable);
    Optional<Responsable> actualizar(Long id, Responsable responsable);
    void eliminar(Long id);
    List<Responsable> listarPorIds(Iterable<Long> ids);
}