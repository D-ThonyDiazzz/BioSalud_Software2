package org.onions.laboratorio.msvc.muestras.infrastructure.adapter;

import org.onions.laboratorio.msvc.muestras.application.port.MuestraRepositoryPort;
import org.onions.laboratorio.msvc.muestras.domain.model.Muestra;
import org.onions.laboratorio.msvc.muestras.domain.vo.MedioBiologico;
import org.onions.laboratorio.msvc.muestras.infrastructure.entity.MedioBiologicoEmbeddable;
import org.onions.laboratorio.msvc.muestras.infrastructure.entity.MuestraEntity;
import org.onions.laboratorio.msvc.muestras.infrastructure.repository.MuestraJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MuestraJpaAdapter implements MuestraRepositoryPort {

    private final MuestraJpaRepository repository;

    public MuestraJpaAdapter(MuestraJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Muestra> listar() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Muestra> porId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Muestra guardar(Muestra muestra) {
        MuestraEntity entity = toEntity(muestra);
        MuestraEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Muestra> porIdOrdenAtencion(Long idOrdenAtencion) {
        return repository.findByIdOrdenAtencion(idOrdenAtencion).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Muestra> porEstado(String estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }


    private Muestra toDomain(MuestraEntity entity) {
        Muestra domain = new Muestra();
        domain.setId(entity.getId());
        domain.setIdOrdenAtencion(entity.getIdOrdenAtencion());
        domain.setCondicionesVerificadas(entity.isCondicionesVerificadas());
        domain.setCodigoRotulado(entity.getCodigoRotulado());
        domain.setFechaToma(entity.getFechaToma());
        domain.setEstado(entity.getEstado());

        if (entity.getMedioBiologico() != null) {
            domain.setMedioBiologico(new MedioBiologico(
                    entity.getMedioBiologico().getTipoMedio(),
                    entity.getMedioBiologico().getContenedorRequerido()
            ));
        }
        return domain;
    }

    private MuestraEntity toEntity(Muestra domain) {
        MuestraEntity entity = new MuestraEntity();
        entity.setId(domain.getId());
        entity.setIdOrdenAtencion(domain.getIdOrdenAtencion());
        entity.setCondicionesVerificadas(domain.isCondicionesVerificadas());
        entity.setCodigoRotulado(domain.getCodigoRotulado());
        entity.setFechaToma(domain.getFechaToma());
        entity.setEstado(domain.getEstado());

        if (domain.getMedioBiologico() != null) {
            entity.setMedioBiologico(new MedioBiologicoEmbeddable(
                    domain.getMedioBiologico().getTipoMedio(),
                    domain.getMedioBiologico().getContenedorRequerido()
            ));
        }
        return entity;
    }
}