package org.onions.laboratorio.msvc.resultados.application.service;

import org.onions.laboratorio.msvc.resultados.domain.model.Resultado;

import java.util.List;
import java.util.Optional;

public interface ResultadoService {
    List<Resultado> listar();
    Optional<Resultado> porId(Long id);
    List<Resultado> porMuestra(Long idMuestra);
    Resultado registrar(Resultado resultado);
    Optional<Resultado> validar(Long id, String bioquimico);
    Optional<Resultado> observar(Long id);
    Optional<Resultado> actualizar(Long id, Resultado resultado);
    void eliminar(Long id);
    List<Resultado> listarPorIds(Iterable<Long> ids);
}
