package org.onions.laboratorio.msvc.analisis.application.port;

import org.onions.laboratorio.msvc.analisis.domain.model.Analisis;

import java.util.List;
import java.util.Optional;

public interface AnalisisRepositoryPort {
    List<Analisis> listar();
    List<Analisis> listarVigentes();
    Optional<Analisis> porId(Long id);
    Analisis guardar(Analisis analisis);
    void eliminar(Long id);
    List<Analisis> listarPorIds(Iterable<Long> ids);
}
