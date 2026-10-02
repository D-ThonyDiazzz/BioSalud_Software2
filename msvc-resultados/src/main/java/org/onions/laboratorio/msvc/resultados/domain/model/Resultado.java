package org.onions.laboratorio.msvc.resultados.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Agregado raiz de una determinacion obtenida a partir de una muestra. */
public class Resultado {

    private Long id;
    private Long idMuestra;
    private String nombreParametro;
    private String unidad;
    private String rangoReferencia;
    private BigDecimal valorObtenido;
    private String estado;
    private LocalDateTime fechaValidacion;
    private String firmadoPor;
    private String interpretacion;

    public Resultado() {
        this.estado = "PENDIENTE";
    }

    public Resultado(Long id, Long idMuestra, String nombreParametro, String unidad,
                     String rangoReferencia, BigDecimal valorObtenido, String estado,
                     LocalDateTime fechaValidacion, String firmadoPor, String interpretacion) {
        this.id = id;
        this.idMuestra = idMuestra;
        this.nombreParametro = nombreParametro;
        this.unidad = unidad;
        this.rangoReferencia = rangoReferencia;
        this.valorObtenido = valorObtenido;
        this.estado = estado;
        this.fechaValidacion = fechaValidacion;
        this.firmadoPor = firmadoPor;
        this.interpretacion = interpretacion;
    }

    public boolean estaValidado() {
        return "VALIDADO".equalsIgnoreCase(estado);
    }

    public void marcarValidado(String bioquimico) {
        if (bioquimico == null || bioquimico.isBlank()) {
            throw new IllegalArgumentException("El bioquimico que valida el resultado es obligatorio");
        }
        this.estado = "VALIDADO";
        this.firmadoPor = bioquimico;
        this.fechaValidacion = LocalDateTime.now();
    }

    public void marcarObservado() {
        this.estado = "OBSERVADO";
        this.fechaValidacion = null;
        this.firmadoPor = null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdMuestra() { return idMuestra; }
    public void setIdMuestra(Long idMuestra) { this.idMuestra = idMuestra; }
    public String getNombreParametro() { return nombreParametro; }
    public void setNombreParametro(String nombreParametro) { this.nombreParametro = nombreParametro; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public String getRangoReferencia() { return rangoReferencia; }
    public void setRangoReferencia(String rangoReferencia) { this.rangoReferencia = rangoReferencia; }
    public BigDecimal getValorObtenido() { return valorObtenido; }
    public void setValorObtenido(BigDecimal valorObtenido) { this.valorObtenido = valorObtenido; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaValidacion() { return fechaValidacion; }
    public void setFechaValidacion(LocalDateTime fechaValidacion) { this.fechaValidacion = fechaValidacion; }
    public String getFirmadoPor() { return firmadoPor; }
    public void setFirmadoPor(String firmadoPor) { this.firmadoPor = firmadoPor; }
    public String getInterpretacion() { return interpretacion; }
    public void setInterpretacion(String interpretacion) { this.interpretacion = interpretacion; }
}
