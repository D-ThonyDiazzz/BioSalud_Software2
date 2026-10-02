package org.onions.laboratorio.msvc.pacientes.application.port;

import org.onions.laboratorio.msvc.pacientes.domain.model.PacienteResponsable;

import java.util.List;

public interface PacienteResponsableRepositoryPort {
    PacienteResponsable guardar(Long idPaciente, PacienteResponsable vinculo);
    List<PacienteResponsable> listarPorPaciente(Long idPaciente);
}
