package org.onions.laboratorio.msvc.perfiles.domain.model;

//Entidad hija del agregado Perfil. Solo guarda el idAnalisis (el Analisis vive en msvc-analisis).
public class PerfilAnalisis {

    private Long id;
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
