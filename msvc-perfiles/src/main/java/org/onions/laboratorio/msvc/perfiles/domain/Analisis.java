package org.onions.laboratorio.msvc.perfiles.domain;

import java.math.BigDecimal;

/**
 * Representacion externa de Analisis, obtenida via Feign desde msvc-analisis.
 * NO se persiste aqui.
 */
public class Analisis {

    private Long id;
    private String nombreAnalisis;
    private String descripcion;
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
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
