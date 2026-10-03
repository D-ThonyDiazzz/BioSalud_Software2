package org.onions.laboratorio.msvc.analisis.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CondicionesPreviasEmbeddable {

    @Column(name = "cond_descripcion", length = 500)
    private String descripcion;

    @Column(name = "cond_tiempo_requerido")
    private Integer tiempoRequerido;

    public CondicionesPreviasEmbeddable() {}

    public CondicionesPreviasEmbeddable(String descripcion, Integer tiempoRequerido) {
        this.descripcion = descripcion;
        this.tiempoRequerido = tiempoRequerido;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getTiempoRequerido() { return tiempoRequerido; }
    public void setTiempoRequerido(Integer tiempoRequerido) { this.tiempoRequerido = tiempoRequerido; }
}
