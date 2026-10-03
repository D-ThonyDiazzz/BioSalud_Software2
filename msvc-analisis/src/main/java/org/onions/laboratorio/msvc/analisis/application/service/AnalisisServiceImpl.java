package org.onions.laboratorio.msvc.analisis.application.service;

import org.onions.laboratorio.msvc.analisis.application.port.AnalisisRepositoryPort;
import org.onions.laboratorio.msvc.analisis.domain.model.Analisis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AnalisisServiceImpl implements AnalisisService {

    private final AnalisisRepositoryPort repository;

    public AnalisisServiceImpl(AnalisisRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listar() { return repository.listar(); }

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listarVigentes() { return repository.listarVigentes(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Analisis> porId(Long id) { return repository.porId(id); }

    @Override
    @Transactional
    public Analisis guardar(Analisis analisis) {
        validarDatosObligatorios(analisis);
        return repository.guardar(analisis);
    }

    @Override
    @Transactional
    public Optional<Analisis> actualizar(Long id, Analisis datos) {
        return repository.porId(id).map(actual -> {
            if (datos.getNombreAnalisis() != null) actual.setNombreAnalisis(datos.getNombreAnalisis());
            if (datos.getDescripcion() != null) actual.setDescripcion(datos.getDescripcion());
            if (datos.getPrecio() != null) actual.setPrecio(datos.getPrecio());
            if (datos.getMedioBiologico() != null) actual.setMedioBiologico(datos.getMedioBiologico());
            if (datos.getCondicionesPrevias() != null) actual.setCondicionesPrevias(datos.getCondicionesPrevias());
            if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
            validarDatosObligatorios(actual);
            return repository.guardar(actual);
        });
    }

    @Override
    @Transactional
    public void eliminar(Long id) { repository.eliminar(id); }

    @Override
    @Transactional
    public Optional<Analisis> darDeBaja(Long id) {
        return repository.porId(id).map(analisis -> {
            analisis.darDeBaja();
            return repository.guardar(analisis);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listarPorIds(Iterable<Long> ids) { return repository.listarPorIds(ids); }

    private void validarDatosObligatorios(Analisis analisis) {
        if (analisis.getNombreAnalisis() == null || analisis.getNombreAnalisis().isBlank()) {
            throw new IllegalArgumentException("El nombre del analisis es obligatorio");
        }
        if (analisis.getPrecio() != null && analisis.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del analisis no puede ser negativo");
        }
    }
}
