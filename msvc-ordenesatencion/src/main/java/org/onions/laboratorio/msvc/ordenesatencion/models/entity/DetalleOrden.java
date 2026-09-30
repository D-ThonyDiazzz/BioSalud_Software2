package org.onions.laboratorio.msvc.ordenesatencion.models.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

//Entidad hija del agregado OrdenAtencion.
//Representa cada analisis o perfil solicitado en una orden, con precios congelados
//al momento de creacion de la orden (RN: los precios no cambian retroactivamente).
@Entity
@Table(name = "detalles_orden")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Puede referenciar a un analisis o a un perfil (uno de los dos)
    @Column(name = "id_analisis")
    private Long idAnalisis;

    @Column(name = "id_perfil")
    private Long idPerfil;

    //Nombre snapshot (congelado)
    @Column(name = "nombre_item")
    private String nombreItem;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "descuento_aplicado")
    private BigDecimal descuentoAplicado;

    @Column(name = "subtotal")
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
}
