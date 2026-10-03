package org.onions.laboratorio.msvc.ordenesatencion.application.port;

import org.onions.laboratorio.msvc.ordenesatencion.application.dto.PerfilInfo;

import java.util.Optional;

public interface PerfilPort {
    Optional<PerfilInfo> porId(Long idPerfil);
}
