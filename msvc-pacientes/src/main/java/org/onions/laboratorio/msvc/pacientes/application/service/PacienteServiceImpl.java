package org.onions.laboratorio.msvc.pacientes.application.service;

import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;
import org.onions.laboratorio.msvc.pacientes.application.port.PacienteRepositoryPort;
import org.onions.laboratorio.msvc.pacientes.application.port.PacienteResponsableRepositoryPort;
import org.onions.laboratorio.msvc.pacientes.application.port.ResponsableClientPort;
import org.onions.laboratorio.msvc.pacientes.domain.model.Paciente;
import org.onions.laboratorio.msvc.pacientes.domain.model.PacienteResponsable;
import org.onions.laboratorio.msvc.pacientes.domain.vo.Autorizacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepositoryPort pacienteRepository;
    private final PacienteResponsableRepositoryPort vinculoRepository;
    private final ResponsableClientPort responsableClient;

    public PacienteServiceImpl(PacienteRepositoryPort pacienteRepository,
                               PacienteResponsableRepositoryPort vinculoRepository,
                               ResponsableClientPort responsableClient) {
        this.pacienteRepository = pacienteRepository;
        this.vinculoRepository = vinculoRepository;
        this.responsableClient = responsableClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listar() { return pacienteRepository.listar(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> porId(Long id) { return pacienteRepository.porId(id); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> porDocumento(String numeroDocumento) {
        return pacienteRepository.porDocumento(numeroDocumento);
    }

    @Override
    @Transactional
    public Paciente guardar(Paciente paciente) {
        validarDatosObligatorios(paciente);
        String numeroDocumento = paciente.getDocumentoIdentidad().getNumeroDocumento();
        if (pacienteRepository.porDocumento(numeroDocumento).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un paciente registrado con el documento: " + numeroDocumento);
        }
        return pacienteRepository.guardar(paciente);
    }

    @Override
    @Transactional
    public Optional<Paciente> actualizar(Long id, Paciente datos) {
        return pacienteRepository.porId(id).map(actual -> {
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getTelefono() != null) actual.setTelefono(datos.getTelefono());
            if (datos.getDireccion() != null) actual.setDireccion(datos.getDireccion());
            if (datos.getCorreoElectronico() != null) actual.setCorreoElectronico(datos.getCorreoElectronico());
            if (datos.getFechaNacimiento() != null) actual.setFechaNacimiento(datos.getFechaNacimiento());
            if (datos.getSexo() != null) actual.setSexo(datos.getSexo());
            validarDatosObligatorios(actual);
            return pacienteRepository.guardar(actual);
        });
    }

    @Override
    @Transactional
    public void eliminar(Long id) { pacienteRepository.eliminar(id); }

    @Override
    @Transactional
    public Optional<PacienteResponsable> asignarResponsable(Long idPaciente,
                                                             PacienteResponsable vinculo) {
        return pacienteRepository.porId(idPaciente).map(paciente -> {
            if (vinculo.getIdResponsable() == null) {
                throw new IllegalArgumentException("El responsable es obligatorio");
            }
            ResponsableData responsable = responsableClient.detalle(vinculo.getIdResponsable());
            vinculo.setIdPaciente(paciente.getId());
            vinculo.setIdResponsable(responsable.getId());
            return vinculoRepository.guardar(idPaciente, vinculo);
        });
    }

    @Override
    @Transactional
    public Optional<PacienteResponsable> crearYAsignarResponsable(Long idPaciente,
                                                                   ResponsableData responsable,
                                                                   String relacion, String firma) {
        return pacienteRepository.porId(idPaciente).map(paciente -> {
            ResponsableData nuevo = responsableClient.crear(responsable);
            PacienteResponsable vinculo = new PacienteResponsable();
            vinculo.setIdPaciente(paciente.getId());
            vinculo.setIdResponsable(nuevo.getId());
            vinculo.setRelacion(relacion);
            vinculo.setAutorizacion(new Autorizacion(firma, LocalDateTime.now()));
            return vinculoRepository.guardar(idPaciente, vinculo);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponsable> listarResponsables(Long idPaciente) {
        return vinculoRepository.listarPorPaciente(idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> detalleConResponsables(Long idPaciente) {
        return pacienteRepository.detalleConResponsables(idPaciente);
    }

    private void validarDatosObligatorios(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio");
        }
        if (paciente.getDocumentoIdentidad() == null
                || paciente.getDocumentoIdentidad().getNumeroDocumento() == null
                || paciente.getDocumentoIdentidad().getNumeroDocumento().isBlank()) {
            throw new IllegalArgumentException("El documento de identidad es obligatorio");
        }
    }
}
