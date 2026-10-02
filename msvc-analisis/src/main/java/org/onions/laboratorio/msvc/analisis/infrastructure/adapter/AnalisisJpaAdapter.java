package org.onions.laboratorio.msvc.analisis.infrastructure.adapter;

import org.onions.laboratorio.msvc.analisis.application.port.AnalisisRepositoryPort;
import org.onions.laboratorio.msvc.analisis.domain.model.Analisis;
import org.onions.laboratorio.msvc.analisis.domain.model.vo.CondicionesPrevias;
import org.onions.laboratorio.msvc.analisis.domain.model.vo.MedioBiologico;
import org.onions.laboratorio.msvc.analisis.infrastructure.entity.AnalisisEntity;
import org.onions.laboratorio.msvc.analisis.infrastructure.entity.CondicionesPreviasEmbeddable;
import org.onions.laboratorio.msvc.analisis.infrastructure.entity.MedioBiologicoEmbeddable;
import org.onions.laboratorio.msvc.analisis.infrastructure.repository.AnalisisJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AnalisisJpaAdapter implements AnalisisRepositoryPort {

    private final AnalisisJpaRepository repository;

    public AnalisisJpaAdapter(AnalisisJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Analisis> listar() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public List<Analisis> listarVigentes() {
        return repository.findByEstado("VIGENTE").stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Analisis> porId(Long id) { return repository.findById(id).map(this::toDomain); }

    @Override
    public Analisis guardar(Analisis analisis) { return toDomain(repository.save(toEntity(analisis))); }

    @Override
    public void eliminar(Long id) { repository.deleteById(id); }

    @Override
    public List<Analisis> listarPorIds(Iterable<Long> ids) {
        return repository.findAllById(ids).stream().map(this::toDomain).toList();
    }

    private Analisis toDomain(AnalisisEntity entity) {
        MedioBiologico medio = entity.getMedioBiologico() == null ? null : new MedioBiologico(
                entity.getMedioBiologico().getTipoMedio(),
                entity.getMedioBiologico().getContenedorRequerido());
        CondicionesPrevias condiciones = entity.getCondicionesPrevias() == null ? null : new CondicionesPrevias(
                entity.getCondicionesPrevias().getDescripcion(),
                entity.getCondicionesPrevias().getTiempoRequerido());
        return new Analisis(entity.getId(), entity.getNombreAnalisis(), entity.getDescripcion(),
                entity.getPrecio(), medio, condiciones, entity.getEstado(), entity.getFechaRegistro());
    }

    private AnalisisEntity toEntity(Analisis domain) {
        AnalisisEntity entity = new AnalisisEntity();
        entity.setId(domain.getId());
        entity.setNombreAnalisis(domain.getNombreAnalisis());
        entity.setDescripcion(domain.getDescripcion());
        entity.setPrecio(domain.getPrecio());
        entity.setEstado(domain.getEstado());
        entity.setFechaRegistro(domain.getFechaRegistro());
        if (domain.getMedioBiologico() != null) {
            entity.setMedioBiologico(new MedioBiologicoEmbeddable(
                    domain.getMedioBiologico().getTipoMedio(),
                    domain.getMedioBiologico().getContenedorRequerido()));
        }
        if (domain.getCondicionesPrevias() != null) {
            entity.setCondicionesPrevias(new CondicionesPreviasEmbeddable(
                    domain.getCondicionesPrevias().getDescripcion(),
                    domain.getCondicionesPrevias().getTiempoRequerido()));
        }
        return entity;
    }
}
