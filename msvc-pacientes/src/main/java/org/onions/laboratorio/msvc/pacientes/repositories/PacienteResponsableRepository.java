package org.onions.laboratorio.msvc.pacientes.repositories;

import org.onions.laboratorio.msvc.pacientes.models.entity.PacienteResponsable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PacienteResponsableRepository extends CrudRepository<PacienteResponsable, Long> {

    List<PacienteResponsable> findByPaciente_Id(Long idPaciente);
}
