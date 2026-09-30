package org.onions.laboratorio.msvc.pacientes.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {

    @Column(name = "dir_calle")
    private String calle;

    @Column(name = "dir_numero")
    private String numero;

    @Column(name = "dir_distrito")
    private String distrito;

    @Column(name = "dir_referencia")
    private String referencia;

    public Direccion() {}

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
}
