package org.onions.laboratorio.msvc.perfiles.infraestructure.adapter;

import org.onions.laboratorio.msvc.perfiles.application.port.PerfilRepositoryPort;
import org.onions.laboratorio.msvc.perfiles.domain.model.Perfil;
import org.onions.laboratorio.msvc.perfiles.domain.model.PerfilAnalisis;
import org.onions.laboratorio.msvc.perfiles.domain.vo.CodigoPerfil;
import org.onions.laboratorio.msvc.perfiles.infraestructure.entity.CodigoPerfilEmbeddable;
import org.onions.laboratorio.msvc.perfiles.infraestructure.entity.PerfilAnalisisEntity;
import org.onions.laboratorio.msvc.perfiles.infraestructure.entity.PerfilEntity;
import org.onions.laboratorio.msvc.perfiles.infraestructure.repository.PerfilJpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PerfilJpaAdapter implements PerfilRepositoryPort {

    private final PerfilJpaRepository repository;

    public PerfilJpaAdapter(PerfilJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Perfil> listar() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Perfil> porId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Perfil guardar(Perfil perfil) {
        PerfilEntity saved = repository.save(toEntity(perfil));
        return toDomain(saved);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis) {
        repository.eliminarPerfilAnalisisPorIdAnalisis(idAnalisis);
    }

    private Perfil toDomain(PerfilEntity entity) {
        Perfil domain = new Perfil();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setDescripcion(entity.getDescripcion());
        domain.setFechaRegistro(entity.getFechaRegistro());

        if (entity.getCodigoPerfil() != null) {
            domain.setCodigoPerfil(new CodigoPerfil(
                    entity.getCodigoPerfil().getClavePerfil(),
                    entity.getCodigoPerfil().getVersion()));
        }

        List<PerfilAnalisis> hijos = entity.getAnalisisPerfil().stream()
                .map(e -> {
                    PerfilAnalisis pa = new PerfilAnalisis(e.getIdAnalisis());
                    pa.setId(e.getId());
                    return pa;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        domain.setAnalisisPerfil(hijos);
        return domain;
    }

    private PerfilEntity toEntity(Perfil domain) {
        PerfilEntity entity = new PerfilEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaRegistro(domain.getFechaRegistro());

        if (domain.getCodigoPerfil() != null) {
            entity.setCodigoPerfil(new CodigoPerfilEmbeddable(
                    domain.getCodigoPerfil().getClavePerfil(),
                    domain.getCodigoPerfil().getVersion()));
        }

        List<PerfilAnalisisEntity> hijos = domain.getAnalisisPerfil().stream()
                .map(pa -> {
                    PerfilAnalisisEntity e = new PerfilAnalisisEntity();
                    e.setId(pa.getId());
                    e.setIdAnalisis(pa.getIdAnalisis());
                    return e;
                })
                .collect(Collectors.toCollection(ArrayList::new));
        entity.setAnalisisPerfil(hijos);
        return entity;
    }
}