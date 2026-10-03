package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.repository;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrdenAtencionJpaRepository extends JpaRepository<OrdenAtencionEntity, Long> {

    List<OrdenAtencionEntity> findByIdPaciente(Long idPaciente);

    @Query("select coalesce(max(o.numeroTurno.numero), 0) from OrdenAtencionEntity o where o.numeroTurno.fechaTurno = ?1")
    Integer maxTurnoDelDia(LocalDate fecha);
}