package org.onions.laboratorio.msvc.resultados.infrastructure.repository;

import org.onions.laboratorio.msvc.resultados.infrastructure.entity.ResultadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoJpaRepository extends JpaRepository<ResultadoEntity, Long> {
    List<ResultadoEntity> findByIdMuestra(Long idMuestra);
    List<ResultadoEntity> findByEstado(String estado);
}
