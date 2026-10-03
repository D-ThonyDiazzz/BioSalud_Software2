package org.onions.laboratorio.msvc.analisis.application.service;

import org.onions.laboratorio.msvc.analisis.domain.model.Analisis;

import java.util.List;
import java.util.Optional;

public interface AnalisisService {
    List<Analisis> listar();
    List<Analisis> listarVigentes();
    Optional<Analisis> porId(Long id);
    Analisis guardar(Analisis analisis);
    Optional<Analisis> actualizar(Long id, Analisis analisis);
    void eliminar(Long id);
    Optional<Analisis> darDeBaja(Long id);
    List<Analisis> listarPorIds(Iterable<Long> ids);
}
