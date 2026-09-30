package org.onions.laboratorio.msvc.muestras.services;

import feign.FeignException;
import org.onions.laboratorio.msvc.muestras.client.OrdenClientRest;
import org.onions.laboratorio.msvc.muestras.models.OrdenAtencion;
import org.onions.laboratorio.msvc.muestras.models.entity.Muestra;
import org.onions.laboratorio.msvc.muestras.repositories.MuestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MuestraServiceImpl implements MuestraService {

    @Autowired
    private MuestraRepository repository;

    @Autowired
    private OrdenClientRest ordenClient;

    @Override
    @Transactional(readOnly = true)
    public List<Muestra> listar() {
        return (List<Muestra>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Muestra> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Muestra> porDetalleOrden(Long idDetalleOrden) {
        return repository.findByIdDetalleOrden(idDetalleOrden);
    }

    @Override
    @Transactional
    public Muestra registrarMuestra(Muestra muestra) {
        //Valida que la orden y el detalle existan en msvc-ordenesatencion
        //Nota: se puede obtener la orden completa para validar existencia del detalle
        try {
            //Aqui se busca la orden que contiene el detalle referenciado
            OrdenAtencion o = ordenClient.detalleCompleto(muestra.getIdDetalleOrden());
            //En caso mas sofisticado, se ubicaria el detalle exacto por su id
        } catch (FeignException e) {
            //Se conserva la creacion aunque la orden no responda; en un flujo real se decidiria
        }
        //Genera codigo de rotulado si no viene
        if (muestra.getCodigoRotulado() == null) {
            muestra.setCodigoRotulado("MU-" + muestra.getIdDetalleOrden() + "-" + System.currentTimeMillis());
        }
        return repository.save(muestra);
    }

    @Override
    @Transactional
    public Optional<Muestra> recibirMuestra(Long id) {
        Optional<Muestra> op = repository.findById(id);
        if (op.isPresent()) {
            Muestra m = op.get();
            m.marcarComoRecibida();
            return Optional.of(repository.save(m));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Muestra> marcarProcesada(Long id) {
        Optional<Muestra> op = repository.findById(id);
        if (op.isPresent()) {
            Muestra m = op.get();
            m.setEstado("PROCESADA");
            return Optional.of(repository.save(m));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Muestra> actualizar(Long id, Muestra datos) {
        Optional<Muestra> op = repository.findById(id);
        if (op.isPresent()) {
            Muestra actual = op.get();
            if (datos.getMedioBiologico() != null) actual.setMedioBiologico(datos.getMedioBiologico());
            if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
            if (datos.getCodigoRotulado() != null) actual.setCodigoRotulado(datos.getCodigoRotulado());
            actual.setCondicionesVerificadas(datos.isCondicionesVerificadas());
            return Optional.of(repository.save(actual));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
