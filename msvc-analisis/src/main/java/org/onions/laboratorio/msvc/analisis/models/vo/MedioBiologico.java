package org.onions.laboratorio.msvc.analisis.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MedioBiologico {

    //sangre, orina, heces, hisopado, esputo, etc.
    @Column(name = "medio_tipo")
    private String tipoMedio;

    //contenedor requerido para el medio biologico
    @Column(name = "medio_contenedor")
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
