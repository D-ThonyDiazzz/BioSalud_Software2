package org.onions.laboratorio.msvc.pacientes.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CorreoElectronicoEmbeddable {
    @Column(name = "correo_electronico")
    private String direccionCorreo;

    public CorreoElectronicoEmbeddable() {}
    public CorreoElectronicoEmbeddable(String direccionCorreo) {
        this.direccionCorreo = direccionCorreo;
    }
    public String getDireccionCorreo() { return direccionCorreo; }
    public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
}
