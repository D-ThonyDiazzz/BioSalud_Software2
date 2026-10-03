package org.onions.laboratorio.msvc.muestras.application.service;

import org.onions.laboratorio.msvc.muestras.domain.model.Muestra;

import java.util.List;
import java.util.Optional;

public interface MuestraService {

    List<Muestra> listar();

    Optional<Muestra> porId(Long id);

    List<Muestra> porOrdenAtencion(Long idOrdenAtencion);

    //Crea una nueva muestra a partir de un detalle de orden
    Muestra registrarMuestra(Muestra muestra);

    Optional<Muestra> recibirMuestra(Long id);

    Optional<Muestra> marcarProcesada(Long id);

    Optional<Muestra> actualizar(Long id, Muestra muestra);

    void eliminar(Long id);
}
