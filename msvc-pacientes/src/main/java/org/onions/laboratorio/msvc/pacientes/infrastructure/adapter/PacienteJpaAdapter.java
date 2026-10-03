package org.onions.laboratorio.msvc.pacientes.infrastructure.adapter;

import org.onions.laboratorio.msvc.pacientes.application.port.PacienteRepositoryPort;
import org.onions.laboratorio.msvc.pacientes.application.port.PacienteResponsableRepositoryPort;
import org.onions.laboratorio.msvc.pacientes.domain.model.Paciente;
import org.onions.laboratorio.msvc.pacientes.domain.model.PacienteResponsable;
import org.onions.laboratorio.msvc.pacientes.domain.model.vo.Autorizacion;
import org.onions.laboratorio.msvc.pacientes.domain.model.vo.CorreoElectronico;
import org.onions.laboratorio.msvc.pacientes.domain.model.vo.Direccion;
import org.onions.laboratorio.msvc.pacientes.domain.model.vo.DocumentoIdentidad;
import org.onions.laboratorio.msvc.pacientes.domain.model.vo.Telefono;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.AutorizacionEmbeddable;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.CorreoElectronicoEmbeddable;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.DireccionEmbeddable;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.DocumentoIdentidadEmbeddable;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.PacienteEntity;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.PacienteResponsableEntity;
import org.onions.laboratorio.msvc.pacientes.infrastructure.entity.TelefonoEmbeddable;
import org.onions.laboratorio.msvc.pacientes.infrastructure.repository.PacienteJpaRepository;
import org.onions.laboratorio.msvc.pacientes.infrastructure.repository.PacienteResponsableJpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PacienteJpaAdapter implements PacienteRepositoryPort, PacienteResponsableRepositoryPort {

    private final PacienteJpaRepository pacienteRepository;
    private final PacienteResponsableJpaRepository vinculoRepository;

    public PacienteJpaAdapter(PacienteJpaRepository pacienteRepository,
                              PacienteResponsableJpaRepository vinculoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.vinculoRepository = vinculoRepository;
    }

    @Override
    public List<Paciente> listar() {
        return pacienteRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Paciente> porId(Long id) {
        return pacienteRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Paciente> porDocumento(String numeroDocumento) {
        return pacienteRepository.findByDocumentoIdentidad_NumeroDocumento(numeroDocumento)
                .map(this::toDomain);
    }

    @Override
    public Optional<Paciente> detalleConResponsables(Long idPaciente) {
        return pacienteRepository.buscarConResponsables(idPaciente).map(this::toDomain);
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        return toDomain(pacienteRepository.save(toEntity(paciente)));
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public PacienteResponsable guardar(Long idPaciente, PacienteResponsable vinculo) {
        PacienteEntity paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new IllegalArgumentException("No existe el paciente"));
        PacienteResponsableEntity entity = toEntity(vinculo);
        entity.setPaciente(paciente);
        return toDomain(vinculoRepository.save(entity));
    }

    @Override
    public List<PacienteResponsable> listarPorPaciente(Long idPaciente) {
        return vinculoRepository.findByPaciente_Id(idPaciente).stream().map(this::toDomain).toList();
    }

    private Paciente toDomain(PacienteEntity entity) {
        DocumentoIdentidad documento = entity.getDocumentoIdentidad() == null ? null
                : new DocumentoIdentidad(entity.getDocumentoIdentidad().getTipoDocumento(),
                entity.getDocumentoIdentidad().getNumeroDocumento());
        Telefono telefono = entity.getTelefono() == null ? null
                : new Telefono(entity.getTelefono().getPrefijo(), entity.getTelefono().getNumeroTelefono());
        Direccion direccion = entity.getDireccion() == null ? null
                : new Direccion(entity.getDireccion().getCalle(), entity.getDireccion().getNumero(),
                entity.getDireccion().getDistrito(), entity.getDireccion().getReferencia());
        CorreoElectronico correo = entity.getCorreoElectronico() == null ? null
                : new CorreoElectronico(entity.getCorreoElectronico().getDireccionCorreo());
        List<PacienteResponsable> responsables = entity.getResponsables() == null
                ? new ArrayList<>() : entity.getResponsables().stream().map(this::toDomain).toList();
        return new Paciente(entity.getId(), documento, entity.getNombre(), entity.getFechaNacimiento(),
                entity.getSexo(), telefono, direccion, correo, entity.getFechaRegistro(), responsables);
    }

    private PacienteResponsable toDomain(PacienteResponsableEntity entity) {
        Autorizacion autorizacion = entity.getAutorizacion() == null ? null
                : new Autorizacion(entity.getAutorizacion().getFirmaResponsable(),
                entity.getAutorizacion().getFechaAutorizacion());
        Long idPaciente = entity.getPaciente() == null ? null : entity.getPaciente().getId();
        return new PacienteResponsable(entity.getId(), idPaciente, entity.getIdResponsable(),
                entity.getRelacion(), autorizacion);
    }

    private PacienteEntity toEntity(Paciente domain) {
        PacienteEntity entity = new PacienteEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setSexo(domain.getSexo());
        entity.setFechaRegistro(domain.getFechaRegistro());
        if (domain.getDocumentoIdentidad() != null) {
            entity.setDocumentoIdentidad(new DocumentoIdentidadEmbeddable(
                    domain.getDocumentoIdentidad().getTipoDocumento(),
                    domain.getDocumentoIdentidad().getNumeroDocumento()));
        }
        if (domain.getTelefono() != null) {
            entity.setTelefono(new TelefonoEmbeddable(domain.getTelefono().getPrefijo(),
                    domain.getTelefono().getNumeroTelefono()));
        }
        if (domain.getDireccion() != null) {
            entity.setDireccion(new DireccionEmbeddable(domain.getDireccion().getCalle(),
                    domain.getDireccion().getNumero(), domain.getDireccion().getDistrito(),
                    domain.getDireccion().getReferencia()));
        }
        if (domain.getCorreoElectronico() != null) {
            entity.setCorreoElectronico(new CorreoElectronicoEmbeddable(
                    domain.getCorreoElectronico().getDireccionCorreo()));
        }
        List<PacienteResponsableEntity> vinculos = new ArrayList<>();
        for (PacienteResponsable responsable : domain.getResponsables()) {
            PacienteResponsableEntity vinculo = toEntity(responsable);
            vinculo.setPaciente(entity);
            vinculos.add(vinculo);
        }
        entity.setResponsables(vinculos);
        return entity;
    }

    private PacienteResponsableEntity toEntity(PacienteResponsable domain) {
        PacienteResponsableEntity entity = new PacienteResponsableEntity();
        entity.setId(domain.getId());
        entity.setIdResponsable(domain.getIdResponsable());
        entity.setRelacion(domain.getRelacion());
        if (domain.getAutorizacion() != null) {
            entity.setAutorizacion(new AutorizacionEmbeddable(
                    domain.getAutorizacion().getFirmaResponsable(),
                    domain.getAutorizacion().getFechaAutorizacion()));
        }
        return entity;
    }
}
