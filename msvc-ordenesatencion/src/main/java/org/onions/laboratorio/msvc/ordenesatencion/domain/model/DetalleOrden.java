package org.onions.laboratorio.msvc.ordenesatencion.domain.model;


import java.math.BigDecimal;

public class DetalleOrden {

    private Long id;
    private Long idOrdenAtencion;
    private Long idAnalisis;
    private Long idPerfil;
    private String nombreItem;
    private BigDecimal precioUnitario;
    private BigDecimal descuentoAplicado;
    private BigDecimal subtotal;

    public DetalleOrden() {
        this.descuentoAplicado = BigDecimal.ZERO;
    }

    //Recalcula el subtotal en base a precio y descuento aplicado
    public BigDecimal calcularSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        BigDecimal desc = descuentoAplicado != null ? descuentoAplicado : BigDecimal.ZERO;
        this.subtotal = precioUnitario.subtract(desc);
        return this.subtotal;
    }

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
    public BigDecimal getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(BigDecimal descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public Long getIdOrdenAtencion() {
        return idOrdenAtencion;
    }
    public void setIdOrdenAtencion(Long idOrdenAtencion) {
        this.idOrdenAtencion = idOrdenAtencion;
    }
}
