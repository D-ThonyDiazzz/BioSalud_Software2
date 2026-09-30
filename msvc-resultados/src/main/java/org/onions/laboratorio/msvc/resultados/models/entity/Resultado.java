package org.onions.laboratorio.msvc.resultados.models.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Agregado (raiz): Resultado.
//Representa cada determinacion obtenida a partir del procesamiento manual de una Muestra.
//Mantiene su propio flujo de estados (PENDIENTE, OBSERVADO, VALIDADO) hasta la firma tecnica del bioquimico.
//Referencia a Muestra SOLO por identificador. El parametro analitico se guarda como datos propios del resultado.
@Entity
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Referencias externas
    @Column(name = "id_muestra", nullable = false)
    private Long idMuestra;

    //Datos del parametro medido (propios del Resultado, sin depender de otro microservicio)
    @Column(name = "nombre_parametro", nullable = false)
    private String nombreParametro;

    private String unidad;

    @Column(name = "rango_referencia")
    private String rangoReferencia;

    @Column(name = "valor_obtenido")
    private BigDecimal valorObtenido;

    //PENDIENTE, OBSERVADO, VALIDADO
    private String estado;

    @Column(name = "fecha_validacion")
    private LocalDateTime fechaValidacion;

    @Column(name = "firmado_por")
    private String firmadoPor;

    private String interpretacion;

    public Resultado() {
        this.estado = "PENDIENTE";
    }

    public boolean estaValidado() {
        return "VALIDADO".equalsIgnoreCase(this.estado);
    }

    public void marcarValidado(String bioquimico) {
        this.estado = "VALIDADO";
        this.firmadoPor = bioquimico;
        this.fechaValidacion = LocalDateTime.now();
    }

    public void marcarObservado() {
        this.estado = "OBSERVADO";
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
    public String getInterpretacion() { return interpretacion; }
    public void setInterpretacion(String interpretacion) { this.interpretacion = interpretacion; }
    public BigDecimal getValorObtenido() { return valorObtenido; }
    public void setValorObtenido(BigDecimal valorObtenido) { this.valorObtenido = valorObtenido; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaValidacion() { return fechaValidacion; }
    public void setFechaValidacion(LocalDateTime fechaValidacion) { this.fechaValidacion = fechaValidacion; }
    public String getFirmadoPor() { return firmadoPor; }
    public void setFirmadoPor(String firmadoPor) { this.firmadoPor = firmadoPor; }
}
