package org.onions.laboratorio.msvc.resultados.infrastructure.client.model;

public class MuestraResponse {

    private Long id;
    private Long idDetalleOrden;
    private String codigoRotulado;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdDetalleOrden() { return idDetalleOrden; }
    public void setIdDetalleOrden(Long idDetalleOrden) { this.idDetalleOrden = idDetalleOrden; }
    public String getCodigoRotulado() { return codigoRotulado; }
    public void setCodigoRotulado(String codigoRotulado) { this.codigoRotulado = codigoRotulado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
