package org.onions.laboratorio.msvc.pacientes.domain.model.vo;

public class CorreoElectronico {
    private String direccionCorreo;

    public CorreoElectronico() {}

    public CorreoElectronico(String direccionCorreo) {
        this.direccionCorreo = direccionCorreo;
    }

    public String getDireccionCorreo() { return direccionCorreo; }
    public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
}
