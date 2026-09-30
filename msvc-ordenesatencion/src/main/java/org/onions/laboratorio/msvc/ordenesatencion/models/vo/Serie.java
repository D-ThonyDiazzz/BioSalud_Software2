package org.onions.laboratorio.msvc.ordenesatencion.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Serie {

    @Column(name = "serie_codigo")
    private String codigoSerie;

    @Column(name = "serie_correlativo")
    private Integer correlativoActual;

    public Serie() {}

    public Serie(String codigoSerie, Integer correlativoActual) {
        this.codigoSerie = codigoSerie;
        this.correlativoActual = correlativoActual;
    }

    public Integer obtenerSiguienteCorrelativo() {
        if (correlativoActual == null) correlativoActual = 0;
        return ++correlativoActual;
    }

    public String getCodigoSerie() { return codigoSerie; }
    public void setCodigoSerie(String codigoSerie) { this.codigoSerie = codigoSerie; }
    public Integer getCorrelativoActual() { return correlativoActual; }
    public void setCorrelativoActual(Integer correlativoActual) { this.correlativoActual = correlativoActual; }
}
