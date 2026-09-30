package org.onions.laboratorio.msvc.muestras.models;

import java.math.BigDecimal;

public class DetalleOrden {

    private Long id;
    private Long idAnalisis;
    private Long idPerfil;
    private String nombreItem;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetalleOrden() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(Long idAnalisis) { this.idAnalisis = idAnalisis; }
    public Long getIdPerfil() { return idPerfil; }
    public void setIdPerfil(Long idPerfil) { this.idPerfil = idPerfil; }
    public String getNombreItem() { return nombreItem; }
    public void setNombreItem(String nombreItem) { this.nombreItem = nombreItem; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
