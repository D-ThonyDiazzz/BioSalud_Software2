package org.onions.laboratorio.msvc.perfiles.application.port;

import org.onions.laboratorio.msvc.perfiles.domain.Analisis;

import java.util.List;

//Puerto de salida hacia msvc-analisis
public interface AnalisisClientPort {
    Analisis porId(Long id);
    List<Analisis> porIds(List<Long> ids);
}