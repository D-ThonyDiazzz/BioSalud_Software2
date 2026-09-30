package org.onions.laboratorio.msvc.responsables.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Telefono {

    @Column(name = "tel_prefijo")
    private String prefijo;

    @Column(name = "tel_numero")
    private String numeroTelefono;

    public Telefono() {}

    public String getPrefijo() { return prefijo; }
    public void setPrefijo(String prefijo) { this.prefijo = prefijo; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
}
