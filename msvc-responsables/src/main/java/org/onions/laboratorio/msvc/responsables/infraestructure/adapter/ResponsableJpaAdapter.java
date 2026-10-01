package org.onions.laboratorio.msvc.responsables.infraestructure.adapter;

import org.onions.laboratorio.msvc.responsables.application.port.ResponsableRepositoryPort;
import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import org.onions.laboratorio.msvc.responsables.infraestructure.entity.ResponsableEntity;
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

    // --- MÉTODOS DE MAPEO (Traducciones) ---

    private Responsable toDomain(ResponsableEntity entity) {
        return new Responsable(
                entity.getId(),
                entity.getDocumentoIdentidad(),
                entity.getNombre(),
                entity.getFechaNacimiento(),
                entity.getSexo(),
                entity.getTelefono(),
                entity.getCorreoElectronico(),
                entity.getFechaRegistro()
        );
    }

    private ResponsableEntity toEntity(Responsable domain) {
        ResponsableEntity entity = new ResponsableEntity();
        entity.setId(domain.getId());
        entity.setDocumentoIdentidad(domain.getDocumentoIdentidad());
        entity.setNombre(domain.getNombre());
        entity.setFechaNacimiento(domain.getFechaNacimiento());
        entity.setSexo(domain.getSexo());
        entity.setTelefono(domain.getTelefono());
        entity.setCorreoElectronico(domain.getCorreoElectronico());

        if (domain.getFechaRegistro() != null) {
            entity.setFechaRegistro(domain.getFechaRegistro());
        }

        return entity;
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
                .collect(java.util.stream.Collectors.toList());
    }
}