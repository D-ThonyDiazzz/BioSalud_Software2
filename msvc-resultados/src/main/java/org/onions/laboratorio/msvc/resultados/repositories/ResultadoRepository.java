package org.onions.laboratorio.msvc.resultados.repositories;

import org.onions.laboratorio.msvc.resultados.models.entity.Resultado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultadoRepository extends CrudRepository<Resultado, Long> {

    List<Resultado> findByIdMuestra(Long idMuestra);

    List<Resultado> findByEstado(String estado);
}
