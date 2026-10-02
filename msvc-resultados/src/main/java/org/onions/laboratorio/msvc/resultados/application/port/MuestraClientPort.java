package org.onions.laboratorio.msvc.resultados.application.port;

/** Puerto de salida para comprobar referencias del microservicio de muestras. */
public interface MuestraClientPort {
    Long obtenerIdMuestra(Long idMuestra);
}
