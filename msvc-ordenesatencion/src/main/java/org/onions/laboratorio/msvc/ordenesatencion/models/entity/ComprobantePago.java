package org.onions.laboratorio.msvc.ordenesatencion.models.entity;

import jakarta.persistence.*;
import org.onions.laboratorio.msvc.ordenesatencion.models.vo.MedioPago;
import org.onions.laboratorio.msvc.ordenesatencion.models.vo.Serie;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Entidad hija del agregado OrdenAtencion.
//Regla del Negocio: no existe comprobante sin una orden que lo origine, y una orden cobrada
// siempre debe tener su comprobante asociado.

@Entity
@Table(name = "comprobantes_pago")
public class ComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Serie serie;

    private String numero;

    @Embedded
    private MedioPago medioPago;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    public ComprobantePago() {
        this.fechaEmision = LocalDateTime.now();
    }

    public boolean requiereComprobanteVoucher() {
        return medioPago != null && medioPago.requiereComprobanteVoucher();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Serie getSerie() { return serie; }
    public void setSerie(Serie serie) { this.serie = serie; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public MedioPago getMedioPago() { return medioPago; }
    public void setMedioPago(MedioPago medioPago) { this.medioPago = medioPago; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
}
