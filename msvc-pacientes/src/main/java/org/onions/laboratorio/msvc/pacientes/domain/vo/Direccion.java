package org.onions.laboratorio.msvc.pacientes.domain.vo;

public class Direccion {
    private String calle;
    private String numero;
    private String distrito;
    private String referencia;

    public Direccion() {}

    public Direccion(String calle, String numero, String distrito, String referencia) {
        this.calle = calle;
        this.numero = numero;
        this.distrito = distrito;
        this.referencia = referencia;
    }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
}
