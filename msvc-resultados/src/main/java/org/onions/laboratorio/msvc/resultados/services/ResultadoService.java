package org.onions.laboratorio.msvc.resultados.services;

import org.onions.laboratorio.msvc.resultados.models.entity.Resultado;

import java.util.List;
import java.util.Optional;

public interface ResultadoService {

    List<Resultado> listar();

    Optional<Resultado> porId(Long id);

    List<Resultado> porMuestra(Long idMuestra);

    Resultado registrar(Resultado resultado);

    //Marca el resultado como VALIDADO (firma tecnica)
    Optional<Resultado> validar(Long id, String bioquimico);

    //Marca el resultado como OBSERVADO
    Optional<Resultado> observar(Long id);

    Optional<Resultado> actualizar(Long id, Resultado resultado);

    void eliminar(Long id);

    List<Resultado> listarPorIds(Iterable<Long> ids);
}
