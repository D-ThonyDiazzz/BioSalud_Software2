package org.onions.laboratorio.msvc.pacientes.repositories;

import org.onions.laboratorio.msvc.pacientes.models.entity.Paciente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PacienteRepository extends CrudRepository<Paciente, Long> {

    //RN: la busqueda se realiza siempre por documento de identidad para evitar duplicados
    Optional<Paciente> findByDocumentoIdentidad_NumeroDocumento(String numeroDocumento);
}
