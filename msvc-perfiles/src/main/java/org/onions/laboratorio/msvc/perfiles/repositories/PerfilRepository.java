package org.onions.laboratorio.msvc.perfiles.repositories;

import org.onions.laboratorio.msvc.perfiles.models.entity.Perfil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PerfilRepository extends CrudRepository<Perfil, Long> {

    //Elimina las relaciones que apuntan a un idAnalisis dado (para cuando un analisis se elimina)
    @Modifying
    @Query("delete from PerfilAnalisis pa where pa.idAnalisis = ?1")
    void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis);
}
