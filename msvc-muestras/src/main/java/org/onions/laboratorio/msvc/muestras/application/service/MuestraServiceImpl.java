package org.onions.laboratorio.msvc.muestras.application.service;

import org.onions.laboratorio.msvc.muestras.application.port.MuestraRepositoryPort;
import org.onions.laboratorio.msvc.muestras.application.port.OrdenAtencionPort;
import org.onions.laboratorio.msvc.muestras.domain.model.Muestra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MuestraServiceImpl implements MuestraService {

    @Autowired
    private MuestraRepositoryPort repository;

    @Autowired
    private OrdenAtencionPort ordenPort;

    @Override
    @Transactional(readOnly = true)
    public List<Muestra> listar() {
        return repository.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Muestra> porId(Long id) {
        return repository.porId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Muestra> porOrdenAtencion(Long idOrdenAtencion) {
        return repository.porIdOrdenAtencion(idOrdenAtencion);
    }

    @Override
    @Transactional
    public Muestra registrarMuestra(Muestra muestra) {
        if (!ordenPort.existe(muestra.getIdOrdenAtencion())) {
            throw new NoSuchElementException("No existe la orden de atencion con id " + muestra.getIdOrdenAtencion());
        }
        //Genera codigo de rotulado si no viene
        if (muestra.getCodigoRotulado() == null) {
            muestra.setCodigoRotulado("MU-" + muestra.getIdOrdenAtencion() + "-" + System.currentTimeMillis());
        }
        return repository.guardar(muestra);
    }

    @Override
    @Transactional
    public Optional<Muestra> recibirMuestra(Long id) {
        Optional<Muestra> op = repository.porId(id);
        if (op.isPresent()) {
            Muestra m = op.get();
            m.marcarComoRecibida();
            return Optional.of(repository.guardar(m));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Muestra> marcarProcesada(Long id) {
        Optional<Muestra> op = repository.porId(id);
        if (op.isPresent()) {
            Muestra m = op.get();
            m.setEstado("PROCESADA");
            return Optional.of(repository.guardar(m));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Muestra> actualizar(Long id, Muestra datos) {
        Optional<Muestra> op = repository.porId(id);
        if (op.isPresent()) {
            Muestra actual = op.get();
            if (datos.getMedioBiologico() != null) actual.setMedioBiologico(datos.getMedioBiologico());
            if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
            if (datos.getCodigoRotulado() != null) actual.setCodigoRotulado(datos.getCodigoRotulado());
            actual.setCondicionesVerificadas(datos.isCondicionesVerificadas());
            return Optional.of(repository.guardar(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.eliminar(id);
    }
}