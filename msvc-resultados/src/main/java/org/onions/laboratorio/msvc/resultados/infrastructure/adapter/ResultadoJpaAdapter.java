package org.onions.laboratorio.msvc.resultados.infrastructure.adapter;

import org.onions.laboratorio.msvc.resultados.application.port.ResultadoRepositoryPort;
import org.onions.laboratorio.msvc.resultados.domain.model.Resultado;
import org.onions.laboratorio.msvc.resultados.infrastructure.entity.ResultadoEntity;
import org.onions.laboratorio.msvc.resultados.infrastructure.repository.ResultadoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ResultadoJpaAdapter implements ResultadoRepositoryPort {

    private final ResultadoJpaRepository repository;

    public ResultadoJpaAdapter(ResultadoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Resultado> listar() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public Optional<Resultado> porId(Long id) { return repository.findById(id).map(this::toDomain); }

    @Override
    public List<Resultado> porMuestra(Long idMuestra) {
        return repository.findByIdMuestra(idMuestra).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Resultado> porEstado(String estado) {
        return repository.findByEstado(estado).stream().map(this::toDomain).toList();
    }

    @Override
    public Resultado guardar(Resultado resultado) { return toDomain(repository.save(toEntity(resultado))); }

    @Override
    public void eliminar(Long id) { repository.deleteById(id); }

    @Override
    public List<Resultado> listarPorIds(Iterable<Long> ids) {
        return repository.findAllById(ids).stream().map(this::toDomain).toList();
    }

    private Resultado toDomain(ResultadoEntity entity) {
        return new Resultado(entity.getId(), entity.getIdMuestra(), entity.getNombreParametro(),
                entity.getUnidad(), entity.getRangoReferencia(), entity.getValorObtenido(),
                entity.getEstado(), entity.getFechaValidacion(), entity.getFirmadoPor(),
                entity.getInterpretacion());
    }

    private ResultadoEntity toEntity(Resultado domain) {
        ResultadoEntity entity = new ResultadoEntity();
        entity.setId(domain.getId());
        entity.setIdMuestra(domain.getIdMuestra());
        entity.setNombreParametro(domain.getNombreParametro());
        entity.setUnidad(domain.getUnidad());
        entity.setRangoReferencia(domain.getRangoReferencia());
        entity.setValorObtenido(domain.getValorObtenido());
        entity.setEstado(domain.getEstado());
        entity.setFechaValidacion(domain.getFechaValidacion());
        entity.setFirmadoPor(domain.getFirmadoPor());
        entity.setInterpretacion(domain.getInterpretacion());
        return entity;
    }
}
