package org.onions.laboratorio.msvc.responsables.application.service;

import org.onions.laboratorio.msvc.responsables.application.port.ResponsableRepositoryPort;
import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsableServiceImpl implements ResponsableService {

    @Autowired
    private ResponsableRepositoryPort port;

    @Override
    @Transactional(readOnly = true)
    public List<Responsable> listar() {
        return port.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Responsable> porId(Long id) {
        return port.porId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Responsable> porDocumento(String numeroDocumento) {
        return port.porDocumento(numeroDocumento);
    }

    @Override
    @Transactional
    public Responsable guardar(Responsable responsable) {
        if (responsable.getDocumentoIdentidad() != null) {
            Optional<Responsable> existente = port
                    .porDocumento(responsable.getDocumentoIdentidad().getNumeroDocumento());
            if (existente.isPresent()) {
                throw new IllegalArgumentException(
                        "Ya existe un responsable registrado con el documento: "
                                + responsable.getDocumentoIdentidad().getNumeroDocumento());
            }
        }
        return port.guardar(responsable);
    }

    @Override
    @Transactional
    public Optional<Responsable> actualizar(Long id, Responsable datos) {
        Optional<Responsable> op = port.porId(id);
        if (op.isPresent()) {
            Responsable actual = op.get();
            if (datos.getNombre() != null) actual.setNombre(datos.getNombre());
            if (datos.getTelefono() != null) actual.setTelefono(datos.getTelefono());
            if (datos.getCorreoElectronico() != null) actual.setCorreoElectronico(datos.getCorreoElectronico());
            if (datos.getFechaNacimiento() != null) actual.setFechaNacimiento(datos.getFechaNacimiento());
            if (datos.getSexo() != null) actual.setSexo(datos.getSexo());

            // Guardamos usando el puerto
            return Optional.of(port.guardar(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        port.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Responsable> listarPorIds(Iterable<Long> ids) {
        return port.listarPorIds(ids);
    }
}