package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model;

import java.math.BigDecimal;

public class Analisis {

    private Long id;
    private String nombreAnalisis;
    private BigDecimal precio;
    private String estado;

    public Analisis() {}

    public boolean estaVigente() {
        return "VIGENTE".equalsIgnoreCase(this.estado);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreAnalisis() { return nombreAnalisis; }
    public void setNombreAnalisis(String nombreAnalisis) { this.nombreAnalisis = nombreAnalisis; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
