package org.onions.laboratorio.msvc.analisis.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MedioBiologicoEmbeddable {

    @Column(name = "medio_tipo")
    private String tipoMedio;

    @Column(name = "medio_contenedor")
    private String contenedorRequerido;

    public MedioBiologicoEmbeddable() {}

    public MedioBiologicoEmbeddable(String tipoMedio, String contenedorRequerido) {
        this.tipoMedio = tipoMedio;
        this.contenedorRequerido = contenedorRequerido;
    }

    public String getTipoMedio() { return tipoMedio; }
    public void setTipoMedio(String tipoMedio) { this.tipoMedio = tipoMedio; }
    public String getContenedorRequerido() { return contenedorRequerido; }
    public void setContenedorRequerido(String contenedorRequerido) { this.contenedorRequerido = contenedorRequerido; }
}
