package org.onions.laboratorio.msvc.analisis.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CondicionesPrevias {

    //ej. "Ayuno de 8 horas", "Abstenerse de orinar 2 horas antes"
    @Column(name = "cond_descripcion", length = 500)
    private String descripcion;

    //tiempo requerido en horas
    @Column(name = "cond_tiempo_requerido")
    private Integer tiempoRequerido;

    public CondicionesPrevias() {}

    public CondicionesPrevias(String descripcion, Integer tiempoRequerido) {
        this.descripcion = descripcion;
        this.tiempoRequerido = tiempoRequerido;
    }

    public boolean requiereAyunoEspecial() {
        return tiempoRequerido != null && tiempoRequerido >= 8;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getTiempoRequerido() { return tiempoRequerido; }
    public void setTiempoRequerido(Integer tiempoRequerido) { this.tiempoRequerido = tiempoRequerido; }
}
