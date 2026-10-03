package org.onions.laboratorio.msvc.muestras.infrastructure.repository;

import org.onions.laboratorio.msvc.muestras.infrastructure.entity.MuestraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuestraJpaRepository extends JpaRepository<MuestraEntity, Long> {

    List<MuestraEntity> findByIdOrdenAtencion(Long idOrdenAtencion);

    List<MuestraEntity> findByEstado(String estado);
}