package org.onions.laboratorio.msvc.pacientes.services;

import org.onions.laboratorio.msvc.pacientes.client.ResponsableClientRest;
import org.onions.laboratorio.msvc.pacientes.models.Responsable;
import org.onions.laboratorio.msvc.pacientes.models.entity.Paciente;
import org.onions.laboratorio.msvc.pacientes.models.entity.PacienteResponsable;
import org.onions.laboratorio.msvc.pacientes.models.vo.Autorizacion;
import org.onions.laboratorio.msvc.pacientes.repositories.PacienteRepository;
import org.onions.laboratorio.msvc.pacientes.repositories.PacienteResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteResponsableRepository vinculoRepository;

    @Autowired
    private ResponsableClientRest responsableClient;

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listar() {
        return (List<Paciente>) pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> porId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> porDocumento(String numeroDocumento) {
        return pacienteRepository.findByDocumentoIdentidad_NumeroDocumento(numeroDocumento);
    }

    @Override
    @Transactional
    public Paciente guardar(Paciente paciente) {
        //RN: no duplicar pacientes con el mismo documento
        if (paciente.getDocumentoIdentidad() != null) {
            Optional<Paciente> existente = pacienteRepository
                    .findByDocumentoIdentidad_NumeroDocumento(
                            paciente.getDocumentoIdentidad().getNumeroDocumento());
            if (existente.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe un paciente registrado con el documento: "
                                + paciente.getDocumentoIdentidad().getNumeroDocumento());
            }
        }
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public Optional<Paciente> actualizar(Long id, Paciente datos) {
        Optional<Paciente> op = pacienteRepository.findById(id);
        if (op.isPresent()) {
            Paciente actual = op.get();
            //Solo se actualizan datos que hayan cambiado; nunca se crea ficha nueva
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getTelefono() != null) actual.setTelefono(datos.getTelefono());
            if (datos.getDireccion() != null) actual.setDireccion(datos.getDireccion());
            if (datos.getCorreoElectronico() != null) actual.setCorreoElectronico(datos.getCorreoElectronico());
            if (datos.getFechaNacimiento() != null) actual.setFechaNacimiento(datos.getFechaNacimiento());
            if (datos.getSexo() != null) actual.setSexo(datos.getSexo());
            return Optional.of(pacienteRepository.save(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<PacienteResponsable> asignarResponsable(Long idPaciente, PacienteResponsable vinculo) {
        Optional<Paciente> op = pacienteRepository.findById(idPaciente);
        if (op.isPresent()) {
            //Valida que el responsable exista en msvc-responsables
            Responsable resp = responsableClient.detalle(vinculo.getIdResponsable());
            vinculo.setIdResponsable(resp.getId());
            vinculo.setPaciente(op.get());
            return Optional.of(vinculoRepository.save(vinculo));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<PacienteResponsable> crearYAsignarResponsable(Long idPaciente, Responsable responsable,
                                                                    String relacion, String firma) {
        Optional<Paciente> op = pacienteRepository.findById(idPaciente);
        if (op.isPresent()) {
            //Se crea el responsable en msvc-responsables via Feign
            Responsable nuevo = responsableClient.crear(responsable);

            PacienteResponsable vinculo = new PacienteResponsable();
            vinculo.setPaciente(op.get());
            vinculo.setIdResponsable(nuevo.getId());
            vinculo.setRelacion(relacion);

            Autorizacion aut = new Autorizacion();
            aut.setFirmaResponsable(firma);
            aut.setFechaAutorizacion(LocalDateTime.now());
            vinculo.setAutorizacion(aut);

            return Optional.of(vinculoRepository.save(vinculo));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponsable> listarResponsables(Long idPaciente) {
        return vinculoRepository.findByPaciente_Id(idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> detalleConResponsables(Long idPaciente) {
        Optional<Paciente> op = pacienteRepository.findById(idPaciente);
        op.ifPresent(p -> p.getResponsables().size()); //fuerza carga lazy
        return op;
    }
}
