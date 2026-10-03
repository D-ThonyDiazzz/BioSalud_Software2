package org.onions.laboratorio.msvc.perfiles.infraestructure.repository;

import org.onions.laboratorio.msvc.perfiles.infraestructure.entity.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PerfilJpaRepository extends JpaRepository<PerfilEntity, Long> {

    //Elimina las relaciones que apuntan a un idAnalisis (cuando un analisis se elimina)
    @Modifying
    @Query("delete from PerfilAnalisisEntity pa where pa.idAnalisis = ?1")
    void eliminarPerfilAnalisisPorIdAnalisis(Long idAnalisis);
}