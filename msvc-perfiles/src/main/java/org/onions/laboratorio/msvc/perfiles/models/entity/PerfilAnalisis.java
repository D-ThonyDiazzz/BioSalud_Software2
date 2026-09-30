package org.onions.laboratorio.msvc.perfiles.models.entity;

import jakarta.persistence.*;

//Entidad hija del agregado Perfil.
//Representa la relacion entre un Perfil y los Analisis que lo integran.
//Solo guarda idAnalisis; el Analisis en si vive en msvc-analisis.
@Entity
@Table(name = "perfil_analisis")
public class PerfilAnalisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_analisis", unique = true)
    private Long idAnalisis;

    public PerfilAnalisis() {}

    public PerfilAnalisis(Long idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(Long idAnalisis) { this.idAnalisis = idAnalisis; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PerfilAnalisis)) return false;
        PerfilAnalisis pa = (PerfilAnalisis) obj;
        return this.idAnalisis != null && this.idAnalisis.equals(pa.idAnalisis);
    }

    @Override
    public int hashCode() {
        return idAnalisis == null ? 0 : idAnalisis.hashCode();
    }
}
