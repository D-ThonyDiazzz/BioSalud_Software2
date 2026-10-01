package org.onions.laboratorio.msvc.muestras.infrastructure.client.model;

import java.util.List;

public class OrdenAtencion {

    private Long id;
    private Long idPaciente;
    private String estado;
    private List<DetalleOrden> detalles;

    public OrdenAtencion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
}
