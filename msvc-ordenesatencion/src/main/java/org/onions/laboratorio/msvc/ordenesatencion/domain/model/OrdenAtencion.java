package org.onions.laboratorio.msvc.ordenesatencion.domain.model;


import org.onions.laboratorio.msvc.ordenesatencion.domain.vo.CanalEntrega;
import org.onions.laboratorio.msvc.ordenesatencion.domain.vo.NumeroTurno;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenAtencion {
    private Long id;
    private Long idPaciente;
    private NumeroTurno numeroTurno;
    private CanalEntrega canalEntrega;
    private BigDecimal montoTotal;
    private String estado;
    private boolean esParaMenorEdad;
    private LocalDateTime fechaRegistro;
    private List<DetalleOrden> detalles = new ArrayList<>();


    public OrdenAtencion() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = "PENDIENTE";
        this.montoTotal = BigDecimal.ZERO;
    }

    public void agregarDetalle(DetalleOrden det) {
        this.detalles.add(det);
    }

    public void quitarDetalle(Long idDetalle) {
        detalles.removeIf(d -> d.getId() != null && d.getId().equals(idDetalle));
    }

    //RN: la orden es valida si tiene al menos un detalle
    public boolean esValida() {
        return detalles != null && !detalles.isEmpty();
    }

    //Recalcula el monto total sumando los subtotales de los detalles
    public BigDecimal recalcularMontoTotal() {
        this.montoTotal = detalles.stream()
                .map(DetalleOrden::getSubtotal)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.montoTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public CanalEntrega getCanalEntrega() { return canalEntrega; }
    public void setCanalEntrega(CanalEntrega canalEntrega) { this.canalEntrega = canalEntrega; }
    public NumeroTurno getNumeroTurno() { return numeroTurno; }
    public void setNumeroTurno(NumeroTurno numeroTurno) { this.numeroTurno = numeroTurno; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isEsParaMenorEdad() { return esParaMenorEdad; }
    public void setEsParaMenorEdad(boolean esParaMenorEdad) { this.esParaMenorEdad = esParaMenorEdad; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
}
