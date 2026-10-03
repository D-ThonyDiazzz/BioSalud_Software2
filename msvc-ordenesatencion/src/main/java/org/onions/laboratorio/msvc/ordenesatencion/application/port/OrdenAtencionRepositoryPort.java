package org.onions.laboratorio.msvc.ordenesatencion.application.port;

import org.onions.laboratorio.msvc.ordenesatencion.domain.model.OrdenAtencion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdenAtencionRepositoryPort {
    List<OrdenAtencion> listar();
    Optional<OrdenAtencion> porId(Long id);
    List<OrdenAtencion> porIdPaciente(Long idPaciente);
    OrdenAtencion guardar(OrdenAtencion orden);
    void eliminar(Long id);

    // Mayor número de turno del día (0 si no hay). El servicio suma 1.
    int ultimoTurnoDelDia(LocalDate fecha);
}