package org.onions.laboratorio.msvc.perfiles.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CodigoPerfil {

    @Column(name = "clave_perfil")
    private String clavePerfil;

    private Integer version;

    public CodigoPerfil() {}

    public CodigoPerfil(String clavePerfil, Integer version) {
        this.clavePerfil = clavePerfil;
        this.version = version;
    }

    public String getClavePerfil() { return clavePerfil; }
    public void setClavePerfil(String clavePerfil) { this.clavePerfil = clavePerfil; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
