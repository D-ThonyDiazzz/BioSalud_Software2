package org.onions.laboratorio.msvc.resultados.application.port;

import org.onions.laboratorio.msvc.resultados.domain.model.Resultado;

import java.util.List;
import java.util.Optional;

public interface ResultadoRepositoryPort {
    List<Resultado> listar();
    Optional<Resultado> porId(Long id);
    List<Resultado> porMuestra(Long idMuestra);
    List<Resultado> porEstado(String estado);
    Resultado guardar(Resultado resultado);
    void eliminar(Long id);
    List<Resultado> listarPorIds(Iterable<Long> ids);
}
