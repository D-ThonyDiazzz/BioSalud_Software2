package org.onions.laboratorio.msvc.muestras.repositories;

import org.onions.laboratorio.msvc.muestras.models.entity.Muestra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MuestraRepository extends CrudRepository<Muestra, Long> {

    List<Muestra> findByIdDetalleOrden(Long idDetalleOrden);

    List<Muestra> findByEstado(String estado);
}
