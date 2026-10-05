package org.onions.laboratorio.msvc.analisis.domain.vo;

/** Indicaciones que debe cumplir el paciente antes del analisis. */
public class CondicionesPrevias {

    private String descripcion;
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
