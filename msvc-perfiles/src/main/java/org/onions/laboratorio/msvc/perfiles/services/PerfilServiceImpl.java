package org.onions.laboratorio.msvc.perfiles.services;

import org.onions.laboratorio.msvc.perfiles.client.AnalisisClientRest;
import org.onions.laboratorio.msvc.perfiles.models.Analisis;
import org.onions.laboratorio.msvc.perfiles.models.entity.Perfil;
import org.onions.laboratorio.msvc.perfiles.models.entity.PerfilAnalisis;
import org.onions.laboratorio.msvc.perfiles.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository repository;

    @Autowired
    private AnalisisClientRest analisisClient;

    @Override
    @Transactional(readOnly = true)
    public List<Perfil> listar() {
        return (List<Perfil>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Perfil guardar(Perfil perfil) {
        return repository.save(perfil);
    }

    @Override
    @Transactional
    public Optional<Perfil> actualizar(Long id, Perfil datos) {
        Optional<Perfil> op = repository.findById(id);
        if (op.isPresent()) {
            Perfil actual = op.get();
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getDescripcion() != null) actual.setDescripcion(datos.getDescripcion());
            if (datos.getCodigoPerfil() != null) actual.setCodigoPerfil(datos.getCodigoPerfil());
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
    public Optional<Perfil> asignarAnalisis(Long idPerfil, Long idAnalisis) {
        Optional<Perfil> op = repository.findById(idPerfil);
        if (op.isPresent()) {
            //RN: para agregar un analisis al perfil, el analisis debe existir y estar VIGENTE
            Analisis a = analisisClient.detalle(idAnalisis);
            if (!a.estaVigente()) {
                throw new IllegalStateException(
                        "El analisis " + idAnalisis + " no esta vigente y no puede formar parte de un perfil.");
            }
            Perfil perfil = op.get();
            PerfilAnalisis pa = new PerfilAnalisis(a.getId());
            perfil.agregarAnalisis(pa);
            return Optional.of(repository.save(perfil));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Perfil> removerAnalisis(Long idPerfil, Long idAnalisis) {
        Optional<Perfil> op = repository.findById(idPerfil);
        if (op.isPresent()) {
            Perfil perfil = op.get();
            perfil.getAnalisisPerfil().removeIf(pa -> pa.getIdAnalisis().equals(idAnalisis));
            return Optional.of(repository.save(perfil));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> detalleConAnalisis(Long idPerfil) {
        Optional<Perfil> op = repository.findById(idPerfil);
        if (op.isPresent()) {
            Perfil perfil = op.get();
            if (!perfil.getAnalisisPerfil().isEmpty()) {
                List<Long> ids = perfil.getAnalisisPerfil().stream()
                        .map(PerfilAnalisis::getIdAnalisis)
                        .collect(Collectors.toList());
                List<Analisis> analisis = analisisClient.obtenerAnalisisPorIds(ids);
                perfil.setAnalisis(analisis);
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
