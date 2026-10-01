package org.onions.laboratorio.msvc.responsables.infraestructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CorreoElectronicoEmbeddable {

    @Column(name = "correo_electronico")
    private String direccionCorreo;

    public CorreoElectronicoEmbeddable() {}

    public String getDireccionCorreo() { return direccionCorreo; }
    public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
}
