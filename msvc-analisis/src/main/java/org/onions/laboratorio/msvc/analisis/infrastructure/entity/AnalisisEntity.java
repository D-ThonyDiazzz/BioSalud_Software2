package org.onions.laboratorio.msvc.analisis.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "analisis")
public class AnalisisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_analisis", nullable = false)
    private String nombreAnalisis;

    @Column(length = 500)
    private String descripcion;

    private BigDecimal precio;

    @Embedded
    private MedioBiologicoEmbeddable medioBiologico;

    @Embedded
    private CondicionesPreviasEmbeddable condicionesPrevias;

    private String estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreAnalisis() { return nombreAnalisis; }
    public void setNombreAnalisis(String nombreAnalisis) { this.nombreAnalisis = nombreAnalisis; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public MedioBiologicoEmbeddable getMedioBiologico() { return medioBiologico; }
    public void setMedioBiologico(MedioBiologicoEmbeddable medioBiologico) { this.medioBiologico = medioBiologico; }
    public CondicionesPreviasEmbeddable getCondicionesPrevias() { return condicionesPrevias; }
    public void setCondicionesPrevias(CondicionesPreviasEmbeddable condicionesPrevias) { this.condicionesPrevias = condicionesPrevias; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
