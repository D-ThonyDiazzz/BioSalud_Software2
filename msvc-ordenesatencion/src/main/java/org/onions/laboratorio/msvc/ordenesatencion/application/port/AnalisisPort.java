package org.onions.laboratorio.msvc.ordenesatencion.application.port;

import org.onions.laboratorio.msvc.ordenesatencion.application.dto.AnalisisInfo;

import java.util.Optional;

public interface AnalisisPort {
    Optional<AnalisisInfo> porId(Long idAnalisis);
}
