package org.onions.laboratorio.msvc.resultados.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados")
public class ResultadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_muestra", nullable = false)
    private Long idMuestra;

    @Column(name = "nombre_parametro", nullable = false)
    private String nombreParametro;

    private String unidad;

    @Column(name = "rango_referencia")
    private String rangoReferencia;

    @Column(name = "valor_obtenido")
    private BigDecimal valorObtenido;

    private String estado;

    @Column(name = "fecha_validacion")
    private LocalDateTime fechaValidacion;

    @Column(name = "firmado_por")
    private String firmadoPor;

    private String interpretacion;

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
