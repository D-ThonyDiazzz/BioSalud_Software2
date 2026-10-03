package org.onions.laboratorio.msvc.pacientes.application.port;

import org.onions.laboratorio.msvc.pacientes.domain.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepositoryPort {
    List<Paciente> listar();
    Optional<Paciente> porId(Long id);
    Optional<Paciente> porDocumento(String numeroDocumento);
    Optional<Paciente> detalleConResponsables(Long idPaciente);
    Paciente guardar(Paciente paciente);
    void eliminar(Long id);
}
