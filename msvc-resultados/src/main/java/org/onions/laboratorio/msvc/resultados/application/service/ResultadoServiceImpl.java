package org.onions.laboratorio.msvc.resultados.application.service;

import org.onions.laboratorio.msvc.resultados.application.port.MuestraClientPort;
import org.onions.laboratorio.msvc.resultados.application.port.ResultadoRepositoryPort;
import org.onions.laboratorio.msvc.resultados.domain.model.Resultado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoServiceImpl implements ResultadoService {

    private final ResultadoRepositoryPort repository;
    private final MuestraClientPort muestraClient;

    public ResultadoServiceImpl(ResultadoRepositoryPort repository, MuestraClientPort muestraClient) {
        this.repository = repository;
        this.muestraClient = muestraClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> listar() { return repository.listar(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resultado> porId(Long id) { return repository.porId(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> porMuestra(Long idMuestra) { return repository.porMuestra(idMuestra); }

    @Override
    @Transactional
    public Resultado registrar(Resultado resultado) {
        validarDatosObligatorios(resultado);
        resultado.setIdMuestra(muestraClient.obtenerIdMuestra(resultado.getIdMuestra()));
        return repository.guardar(resultado);
    }

    @Override
    @Transactional
    public Optional<Resultado> validar(Long id, String bioquimico) {
        return repository.porId(id).map(resultado -> {
            resultado.marcarValidado(bioquimico);
            return repository.guardar(resultado);
        });
    }

    @Override
    @Transactional
    public Optional<Resultado> observar(Long id) {
        return repository.porId(id).map(resultado -> {
            resultado.marcarObservado();
            return repository.guardar(resultado);
        });
    }

    @Override
    @Transactional
    public Optional<Resultado> actualizar(Long id, Resultado datos) {
        return repository.porId(id).map(actual -> {
            if (datos.getValorObtenido() != null) actual.setValorObtenido(datos.getValorObtenido());
            if (datos.getNombreParametro() != null) actual.setNombreParametro(datos.getNombreParametro());
            if (datos.getUnidad() != null) actual.setUnidad(datos.getUnidad());
            if (datos.getRangoReferencia() != null) actual.setRangoReferencia(datos.getRangoReferencia());
            if (datos.getInterpretacion() != null) actual.setInterpretacion(datos.getInterpretacion());
            if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
            validarDatosObligatorios(actual);
            return repository.guardar(actual);
        });
    }

    @Override
    @Transactional
    public void eliminar(Long id) { repository.eliminar(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Resultado> listarPorIds(Iterable<Long> ids) { return repository.listarPorIds(ids); }

    private void validarDatosObligatorios(Resultado resultado) {
        if (resultado.getIdMuestra() == null) {
            throw new IllegalArgumentException("La muestra del resultado es obligatoria");
        }
        if (resultado.getNombreParametro() == null || resultado.getNombreParametro().isBlank()) {
            throw new IllegalArgumentException("El nombre del parametro es obligatorio");
        }
    }
}
