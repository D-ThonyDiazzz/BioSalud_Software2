package org.onions.laboratorio.msvc.analisis.infrastructure.repository;

import org.onions.laboratorio.msvc.analisis.infrastructure.entity.AnalisisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalisisJpaRepository extends JpaRepository<AnalisisEntity, Long> {
    List<AnalisisEntity> findByEstado(String estado);
}
