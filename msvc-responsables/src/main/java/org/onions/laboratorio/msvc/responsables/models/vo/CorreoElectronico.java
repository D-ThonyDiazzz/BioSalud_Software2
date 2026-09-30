package org.onions.laboratorio.msvc.responsables.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CorreoElectronico {

    @Column(name = "correo_electronico")
    private String direccionCorreo;

    public CorreoElectronico() {}

    public String getDireccionCorreo() { return direccionCorreo; }
    public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
}
