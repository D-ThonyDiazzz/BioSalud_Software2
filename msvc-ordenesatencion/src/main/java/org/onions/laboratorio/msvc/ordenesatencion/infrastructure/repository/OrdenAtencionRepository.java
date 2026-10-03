package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.repository;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdenAtencionRepository extends CrudRepository<OrdenAtencionEntity, Long> {

    List<OrdenAtencionEntity> findByIdPaciente(Long idPaciente);

    List<OrdenAtencionEntity> findByEstado(String estado);

    //Obtiene el mayor numero de turno del dia (para generar el siguiente correlativo)
    @Query("select coalesce(max(o.numeroTurno.numero), 0) from OrdenAtencion o where o.numeroTurno.fechaTurno = ?1")
    Integer maxTurnoDelDia(LocalDate fecha);
}
