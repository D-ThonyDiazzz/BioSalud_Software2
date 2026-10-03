package org.onions.laboratorio.msvc.perfiles.application.service;

import org.onions.laboratorio.msvc.perfiles.domain.model.Perfil;

import java.util.List;
import java.util.Optional;

public interface PerfilService {

    List<Perfil> listar();

    Optional<Perfil> porId(Long id);

    Perfil guardar(Perfil perfil);

    Optional<Perfil> actualizar(Long id, Perfil perfil);

    void eliminar(Long id);

    //Asigna un analisis existente al perfil (usa msvc-analisis para verificar existencia y vigencia)
    Optional<Perfil> asignarAnalisis(Long idPerfil, Long idAnalisis);

    Optional<Perfil> removerAnalisis(Long idPerfil, Long idAnalisis);

    Optional<Perfil> detalleConAnalisis(Long idPerfil);

    void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis);
}
