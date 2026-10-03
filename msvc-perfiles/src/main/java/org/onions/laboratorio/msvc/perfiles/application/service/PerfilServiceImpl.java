package org.onions.laboratorio.msvc.perfiles.application.service;

import org.onions.laboratorio.msvc.perfiles.application.port.AnalisisClientPort;
import org.onions.laboratorio.msvc.perfiles.application.port.PerfilRepositoryPort;
import org.onions.laboratorio.msvc.perfiles.domain.Analisis;
import org.onions.laboratorio.msvc.perfiles.domain.model.Perfil;
import org.onions.laboratorio.msvc.perfiles.domain.model.PerfilAnalisis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepositoryPort repository;

    @Autowired
    private AnalisisClientPort analisisClient;

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> listar() {
        return repository.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> porId(Long id) {
        return repository.porId(id);
    }

    @Override
    @Transactional
    public Perfil guardar(Perfil perfil) {
        return repository.guardar(perfil);
    }

    @Override
    @Transactional
    public Optional<Perfil> actualizar(Long id, Perfil datos) {
        Optional<Perfil> op = repository.porId(id);
        if (op.isPresent()) {
            Perfil actual = op.get();
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getDescripcion() != null) actual.setDescripcion(datos.getDescripcion());
            if (datos.getCodigoPerfil() != null) actual.setCodigoPerfil(datos.getCodigoPerfil());
            return Optional.of(repository.guardar(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    @Override
    @Transactional
    public Optional<Perfil> asignarAnalisis(Long idPerfil, Long idAnalisis) {
        Optional<Perfil> op = repository.porId(idPerfil);
        if (op.isPresent()) {
            //RN: el analisis debe existir y estar VIGENTE
            Analisis a = analisisClient.porId(idAnalisis);
            if (!a.estaVigente()) {
                throw new IllegalStateException(
                        "El analisis " + idAnalisis + " no esta vigente y no puede formar parte de un perfil.");
            }
            Perfil perfil = op.get();
            perfil.agregarAnalisis(a.getId());
            return Optional.of(repository.guardar(perfil));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Perfil> removerAnalisis(Long idPerfil, Long idAnalisis) {
        Optional<Perfil> op = repository.porId(idPerfil);
        if (op.isPresent()) {
            Perfil perfil = op.get();
            perfil.quitarAnalisis(idAnalisis);
            return Optional.of(repository.guardar(perfil));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> detalleConAnalisis(Long idPerfil) {
        Optional<Perfil> op = repository.porId(idPerfil);
        if (op.isPresent()) {
            Perfil perfil = op.get();
            if (!perfil.getAnalisisPerfil().isEmpty()) {
                List<Long> ids = perfil.getAnalisisPerfil().stream()
                        .map(PerfilAnalisis::getIdAnalisis)
                        .collect(Collectors.toList());
                perfil.setAnalisis(analisisClient.porIds(ids));
            }
            return Optional.of(perfil);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis) {
        repository.eliminarPerfilAnalisisPorIdAnalisis(idAnalisis);
    }
}