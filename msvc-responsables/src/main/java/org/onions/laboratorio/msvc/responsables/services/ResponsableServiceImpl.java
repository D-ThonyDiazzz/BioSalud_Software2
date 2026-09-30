package org.onions.laboratorio.msvc.responsables.services;

import org.onions.laboratorio.msvc.responsables.models.entity.Responsable;
import org.onions.laboratorio.msvc.responsables.repositories.ResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsableServiceImpl implements ResponsableService {

    @Autowired
    private ResponsableRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Responsable> listar() {
        return (List<Responsable>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Responsable> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Responsable> porDocumento(String numeroDocumento) {
        return repository.findByDocumentoIdentidad_NumeroDocumento(numeroDocumento);
    }

    @Override
    @Transactional
    public Responsable guardar(Responsable responsable) {
        if (responsable.getDocumentoIdentidad() != null) {
            Optional<Responsable> existente = repository
                    .findByDocumentoIdentidad_NumeroDocumento(
                            responsable.getDocumentoIdentidad().getNumeroDocumento());
            if (existente.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe un responsable registrado con el documento: "
                                + responsable.getDocumentoIdentidad().getNumeroDocumento());
            }
        }
        return repository.save(responsable);
    }

    @Override
    @Transactional
    public Optional<Responsable> actualizar(Long id, Responsable datos) {
        Optional<Responsable> op = repository.findById(id);
        if (op.isPresent()) {
            Responsable actual = op.get();
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getTelefono() != null) actual.setTelefono(datos.getTelefono());
            if (datos.getCorreoElectronico() != null) actual.setCorreoElectronico(datos.getCorreoElectronico());
            if (datos.getFechaNacimiento() != null) actual.setFechaNacimiento(datos.getFechaNacimiento());
            if (datos.getSexo() != null) actual.setSexo(datos.getSexo());
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
    @Transactional(readOnly = true)
    public List<Responsable> listarPorIds(Iterable<Long> ids) {
        return (List<Responsable>) repository.findAllById(ids);
    }
}
