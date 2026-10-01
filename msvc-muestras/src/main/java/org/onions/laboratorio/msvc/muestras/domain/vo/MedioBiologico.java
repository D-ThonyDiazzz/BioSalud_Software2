package org.onions.laboratorio.msvc.muestras.domain.vo;

public class MedioBiologico {

    private String tipoMedio;
    private String contenedorRequerido;

    public MedioBiologico() {}

    public MedioBiologico(String tipoMedio, String contenedorRequerido) {
        this.tipoMedio = tipoMedio;
        this.contenedorRequerido = contenedorRequerido;
    }

    public String getTipoMedio() { return tipoMedio; }
    public void setTipoMedio(String tipoMedio) { this.tipoMedio = tipoMedio; }
    public String getContenedorRequerido() { return contenedorRequerido; }
    public void setContenedorRequerido(String contenedorRequerido) { this.contenedorRequerido = contenedorRequerido; }
}