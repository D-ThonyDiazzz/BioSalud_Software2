package org.onions.laboratorio.msvc.pacientes.infrastructure.repository;

import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.PacienteResponsableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteResponsableJpaRepository extends JpaRepository<PacienteResponsableEntity, Long> {
    List<PacienteResponsableEntity> findByPaciente_Id(Long idPaciente);
}
