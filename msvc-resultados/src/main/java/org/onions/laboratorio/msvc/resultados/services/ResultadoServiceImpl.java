package org.onions.laboratorio.msvc.resultados.services;

import org.onions.laboratorio.msvc.resultados.client.MuestraClientRest;
import org.onions.laboratorio.msvc.resultados.models.Muestra;
import org.onions.laboratorio.msvc.resultados.models.entity.Resultado;
import org.onions.laboratorio.msvc.resultados.repositories.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoServiceImpl implements ResultadoService {

    @Autowired
    private ResultadoRepository repository;

    @Autowired
    private MuestraClientRest muestraClient;

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> listar() {
        return (List<Resultado>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resultado> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> porMuestra(Long idMuestra) {
        return repository.findByIdMuestra(idMuestra);
    }

    @Override
    @Transactional
    public Resultado registrar(Resultado resultado) {
        //Valida existencia de la muestra en su microservicio
        Muestra m = muestraClient.detalle(resultado.getIdMuestra());
        resultado.setIdMuestra(m.getId());

        return repository.save(resultado);
    }

    @Override
    @Transactional
    public Optional<Resultado> validar(Long id, String bioquimico) {
        Optional<Resultado> op = repository.findById(id);
        if (op.isPresent()) {
            Resultado r = op.get();
            r.marcarValidado(bioquimico);
            return Optional.of(repository.save(r));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Resultado> observar(Long id) {
        Optional<Resultado> op = repository.findById(id);
        if (op.isPresent()) {
            Resultado r = op.get();
            r.marcarObservado();
            return Optional.of(repository.save(r));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Resultado> actualizar(Long id, Resultado datos) {
        Optional<Resultado> op = repository.findById(id);
        if (op.isPresent()) {
            Resultado actual = op.get();
            if (datos.getValorObtenido() != null) actual.setValorObtenido(datos.getValorObtenido());
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
    @Transactional(readOnly = true)
    public List<Resultado> listarPorIds(Iterable<Long> ids) {
        return (List<Resultado>) repository.findAllById(ids);
    }
}
