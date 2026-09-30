package org.onions.laboratorio.msvc.analisis.repositories;

import org.onions.laboratorio.msvc.analisis.models.entity.Analisis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalisisRepository extends CrudRepository<Analisis, Long> {

    List<Analisis> findByEstado(String estado);
}
