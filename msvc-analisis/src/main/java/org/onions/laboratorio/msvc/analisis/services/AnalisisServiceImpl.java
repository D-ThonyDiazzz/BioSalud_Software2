package org.onions.laboratorio.msvc.analisis.services;

import org.onions.laboratorio.msvc.analisis.models.entity.Analisis;
import org.onions.laboratorio.msvc.analisis.repositories.AnalisisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnalisisServiceImpl implements AnalisisService {

    @Autowired
    private AnalisisRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listar() {
        return (List<Analisis>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listarVigentes() {
        return repository.findByEstado("VIGENTE");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Analisis> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Analisis guardar(Analisis analisis) {
        return repository.save(analisis);
    }

    @Override
    @Transactional
    public Optional<Analisis> actualizar(Long id, Analisis datos) {
        Optional<Analisis> op = repository.findById(id);
        if (op.isPresent()) {
            Analisis actual = op.get();
            if (datos.getNombreAnalisis() != null) actual.setNombreAnalisis(datos.getNombreAnalisis());
            if (datos.getDescripcion() != null) actual.setDescripcion(datos.getDescripcion());
            if (datos.getPrecio() != null) actual.setPrecio(datos.getPrecio());
            if (datos.getMedioBiologico() != null) actual.setMedioBiologico(datos.getMedioBiologico());
            if (datos.getCondicionesPrevias() != null) actual.setCondicionesPrevias(datos.getCondicionesPrevias());
            if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
            return Optional.of(repository.save(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Analisis> darDeBaja(Long id) {
        Optional<Analisis> op = repository.findById(id);
        if (op.isPresent()) {
            Analisis a = op.get();
            a.setEstado("NO_VIGENTE");
            return Optional.of(repository.save(a));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Analisis> listarPorIds(Iterable<Long> ids) {
        return (List<Analisis>) repository.findAllById(ids);
    }
}
