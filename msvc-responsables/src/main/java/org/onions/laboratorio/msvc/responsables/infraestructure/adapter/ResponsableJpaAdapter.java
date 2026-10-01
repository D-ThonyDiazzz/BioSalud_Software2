package org.onions.laboratorio.msvc.responsables.infraestructure.adapter;

import org.onions.laboratorio.msvc.responsables.application.port.ResponsableRepositoryPort;
import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import org.onions.laboratorio.msvc.responsables.domain.model.vo.CorreoElectronico;
import org.onions.laboratorio.msvc.responsables.domain.model.vo.DocumentoIdentidad;
import org.onions.laboratorio.msvc.responsables.domain.model.vo.Telefono;
import org.onions.laboratorio.msvc.responsables.infraestructure.entity.CorreoElectronicoEmbeddable;
import org.onions.laboratorio.msvc.responsables.infraestructure.entity.DocumentoIdentidadEmbeddable;
import org.onions.laboratorio.msvc.responsables.infraestructure.entity.ResponsableEntity;
import org.onions.laboratorio.msvc.responsables.infraestructure.entity.TelefonoEmbeddable;
import org.onions.laboratorio.msvc.responsables.infraestructure.repository.ResponsableJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ResponsableJpaAdapter implements ResponsableRepositoryPort {

    @Autowired
    private ResponsableJpaRepository responsableJpaRepository;

    @Override
    public List<Responsable> listar() {
        return responsableJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Responsable> porId(Long id) {
        return responsableJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Responsable guardar(Responsable responsable) {
        ResponsableEntity entity = toEntity(responsable);
        ResponsableEntity guardado = responsableJpaRepository.save(entity);
        return toDomain(guardado);
    }

    @Override
    public void eliminar(Long id) {
        responsableJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Responsable> porDocumento(String numeroDocumento) {
        return responsableJpaRepository.findByDocumentoIdentidad_NumeroDocumento(numeroDocumento)
                .map(this::toDomain);
    }

    @Override
    public List<Responsable> listarPorIds(Iterable<Long> ids) {
        return responsableJpaRepository.findAllById(ids).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }


    private Responsable toDomain(ResponsableEntity entity) {
        DocumentoIdentidad doc = null;
        if (entity.getDocumentoIdentidad() != null) {
            doc = new DocumentoIdentidad(
                    entity.getDocumentoIdentidad().getTipoDocumento(),
                    entity.getDocumentoIdentidad().getNumeroDocumento()
            );
        }

        Telefono tel = null;
        if (entity.getTelefono() != null) {
            tel = new Telefono();
            tel.setPrefijo(entity.getTelefono().getPrefijo());
            tel.setNumeroTelefono(entity.getTelefono().getNumeroTelefono());
        }

        CorreoElectronico correo = null;
        if (entity.getCorreoElectronico() != null) {
            correo = new CorreoElectronico();
            correo.setDireccionCorreo(entity.getCorreoElectronico().getDireccionCorreo());
        }

        return new Responsable(
                entity.getId(),
                doc,
                entity.getNombre(),
                entity.getFechaNacimiento(),
                entity.getSexo(),
                tel,
                correo,
                entity.getFechaRegistro()
        );
    }

    private ResponsableEntity toEntity(Responsable domain) {
        ResponsableEntity entity = new ResponsableEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setSexo(domain.getSexo());

        if (domain.getDocumentoIdentidad() != null) {
            entity.setDocumentoIdentidad(new DocumentoIdentidadEmbeddable(
                    domain.getDocumentoIdentidad().getTipoDocumento(),
                    domain.getDocumentoIdentidad().getNumeroDocumento()
            ));
        }

        if (domain.getTelefono() != null) {
            TelefonoEmbeddable tel = new TelefonoEmbeddable();
            tel.setPrefijo(domain.getTelefono().getPrefijo());
            tel.setNumeroTelefono(domain.getTelefono().getNumeroTelefono());
            entity.setTelefono(tel);
        }

        if (domain.getCorreoElectronico() != null) {
            CorreoElectronicoEmbeddable correo = new CorreoElectronicoEmbeddable();
            correo.setDireccionCorreo(domain.getCorreoElectronico().getDireccionCorreo());
            entity.setCorreoElectronico(correo);
        }

        if (domain.getFechaRegistro() != null) {
            entity.setFechaRegistro(domain.getFechaRegistro());
        }

        return entity;
    }
}