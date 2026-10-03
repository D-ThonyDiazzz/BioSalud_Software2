package org.onions.laboratorio.msvc.perfiles.application.port;

import org.onions.laboratorio.msvc.perfiles.domain.model.Perfil;

import java.util.List;
import java.util.Optional;

public interface PerfilRepositoryPort {
    List<Perfil> listar();
    Optional<Perfil> porId(Long id);
    Perfil guardar(Perfil perfil);
    void eliminar(Long id);

    void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis);
}