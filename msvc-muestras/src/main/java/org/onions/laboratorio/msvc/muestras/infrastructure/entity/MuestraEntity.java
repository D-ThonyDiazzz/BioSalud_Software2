package org.onions.laboratorio.msvc.muestras.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "muestras")
public class MuestraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_orden_atencion", nullable = false)
    private Long idOrdenAtencion;

    // Usamos la clase Embeddable de infraestructura, no la del dominio
    @Embedded
    private MedioBiologicoEmbeddable medioBiologico;

    @Column(name = "condiciones_verificadas")
    private boolean condicionesVerificadas;

    @Column(name = "codigo_rotulado")
    private String codigoRotulado;

    @Column(name = "fecha_toma")
    private LocalDateTime fechaToma;

    private String estado;

    public MuestraEntity() {
        this.estado = "PENDIENTE";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdOrdenAtencion() { return idOrdenAtencion; }
    public void setIdOrdenAtencion(Long idOrdenAtencion) { this.idOrdenAtencion = idOrdenAtencion; }

    public MedioBiologicoEmbeddable getMedioBiologico() { return medioBiologico; }
    public void setMedioBiologico(MedioBiologicoEmbeddable medioBiologico) { this.medioBiologico = medioBiologico; }

    public boolean isCondicionesVerificadas() { return condicionesVerificadas; }
    public void setCondicionesVerificadas(boolean condicionesVerificadas) { this.condicionesVerificadas = condicionesVerificadas; }

    public String getCodigoRotulado() { return codigoRotulado; }
    public void setCodigoRotulado(String codigoRotulado) { this.codigoRotulado = codigoRotulado; }

    public LocalDateTime getFechaToma() { return fechaToma; }
    public void setFechaToma(LocalDateTime fechaToma) { this.fechaToma = fechaToma; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}