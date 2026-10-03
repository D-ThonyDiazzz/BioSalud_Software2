package org.onions.laboratorio.msvc.muestras.application.port;

import org.onions.laboratorio.msvc.muestras.domain.model.Muestra;
import java.util.List;
import java.util.Optional;

public interface MuestraRepositoryPort {
    List<Muestra> listar();
    Optional<Muestra> porId(Long id);
    Muestra guardar(Muestra muestra);
    void eliminar(Long id);

    List<Muestra> porIdOrdenAtencion(Long idOrdenAtencion);
    List<Muestra> porEstado(String estado);
}