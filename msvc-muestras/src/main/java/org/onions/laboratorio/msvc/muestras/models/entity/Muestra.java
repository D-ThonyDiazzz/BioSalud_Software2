package org.onions.laboratorio.msvc.muestras.models.entity;

import jakarta.persistence.*;
import org.onions.laboratorio.msvc.muestras.models.vo.MedioBiologico;

import java.time.LocalDateTime;

//Agregado (raiz): Muestra.
//Administra la extraccion, recepcion, rotulado y estado fisico de la muestra biologica.
//Referencia SOLO por identificador al DetalleOrden que la origino (msvc-ordenesatencion).
@Entity
@Table(name = "muestras")
public class Muestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Referencia externa al detalle de la orden que dio origen a esta muestra
    @Column(name = "id_detalle_orden", nullable = false)
    private Long idDetalleOrden;

    @Embedded
    private MedioBiologico medioBiologico;

    //Si las condiciones previas fueron verificadas al momento de la toma
    @Column(name = "condiciones_verificadas")
    private boolean condicionesVerificadas;

    //Codigo unico de rotulado (basado en la orden)
    @Column(name = "codigo_rotulado")
    private String codigoRotulado;

    @Column(name = "fecha_toma")
    private LocalDateTime fechaToma;

    //PENDIENTE, RECIBIDA, RECHAZADA, PROCESADA
    private String estado;

    public Muestra() {
        this.estado = "PENDIENTE";
    }

    public boolean requiereAyunoEspecial() {
        return medioBiologico != null
                && ("SANGRE".equalsIgnoreCase(medioBiologico.getTipoMedio())
                        || "ORINA".equalsIgnoreCase(medioBiologico.getTipoMedio()));
    }

    public void marcarComoRecibida() {
        this.estado = "RECIBIDA";
        this.fechaToma = LocalDateTime.now();
        this.condicionesVerificadas = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdDetalleOrden() { return idDetalleOrden; }
    public void setIdDetalleOrden(Long idDetalleOrden) { this.idDetalleOrden = idDetalleOrden; }
    public MedioBiologico getMedioBiologico() { return medioBiologico; }
    public void setMedioBiologico(MedioBiologico medioBiologico) { this.medioBiologico = medioBiologico; }
    public boolean isCondicionesVerificadas() { return condicionesVerificadas; }
    public void setCondicionesVerificadas(boolean condicionesVerificadas) { this.condicionesVerificadas = condicionesVerificadas; }
    public String getCodigoRotulado() { return codigoRotulado; }
    public void setCodigoRotulado(String codigoRotulado) { this.codigoRotulado = codigoRotulado; }
    public LocalDateTime getFechaToma() { return fechaToma; }
    public void setFechaToma(LocalDateTime fechaToma) { this.fechaToma = fechaToma; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
