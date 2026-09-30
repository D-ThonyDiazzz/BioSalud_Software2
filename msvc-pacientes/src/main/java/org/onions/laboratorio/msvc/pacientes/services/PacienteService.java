package org.onions.laboratorio.msvc.pacientes.services;

import org.onions.laboratorio.msvc.pacientes.models.Responsable;
import org.onions.laboratorio.msvc.pacientes.models.entity.Paciente;
import org.onions.laboratorio.msvc.pacientes.models.entity.PacienteResponsable;

import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<Paciente> listar();

    Optional<Paciente> porId(Long id);

    Optional<Paciente> porDocumento(String numeroDocumento);

    Paciente guardar(Paciente paciente);

    Optional<Paciente> actualizar(Long id, Paciente datos);

    void eliminar(Long id);

    //Metodos remotos: agrega el vinculo con un responsable existente
    Optional<PacienteResponsable> asignarResponsable(Long idPaciente, PacienteResponsable vinculo);

    //Crea el responsable en msvc-responsables y luego lo vincula al paciente
    Optional<PacienteResponsable> crearYAsignarResponsable(Long idPaciente, Responsable responsable,
                                                            String relacion, String firma);

    List<PacienteResponsable> listarResponsables(Long idPaciente);

    Optional<Paciente> detalleConResponsables(Long idPaciente);
}
