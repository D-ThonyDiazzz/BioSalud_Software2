package org.onions.laboratorio.msvc.pacientes.application.service;

import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;
import org.onions.laboratorio.msvc.pacientes.domain.model.Paciente;
import org.onions.laboratorio.msvc.pacientes.domain.model.PacienteResponsable;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> listar();
    Optional<Paciente> porId(Long id);
    Optional<Paciente> porDocumento(String numeroDocumento);
    Paciente guardar(Paciente paciente);
    Optional<Paciente> actualizar(Long id, Paciente datos);
    void eliminar(Long id);
    Optional<PacienteResponsable> asignarResponsable(Long idPaciente, PacienteResponsable vinculo);
    Optional<PacienteResponsable> crearYAsignarResponsable(Long idPaciente, ResponsableData responsable,
                                                            String relacion, String firma);
    List<PacienteResponsable> listarResponsables(Long idPaciente);
    Optional<Paciente> detalleConResponsables(Long idPaciente);
}
