package org.onions.laboratorio.msvc.ordenesatencion.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MedioPago {

    //efectivo, tarjeta, transferencia, etc.
    @Column(name = "pago_forma")
    private String formaPago;

    @Column(name = "pago_transaccion")
    private String codigoTransaccion;

    public MedioPago() {}

    public MedioPago(String formaPago, String codigoTransaccion) {
        this.formaPago = formaPago;
        this.codigoTransaccion = codigoTransaccion;
    }

    public boolean requiereComprobanteVoucher() {
        return formaPago != null && ("TARJETA".equalsIgnoreCase(formaPago) || "TRANSFERENCIA".equalsIgnoreCase(formaPago));
    }

    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
    public String getCodigoTransaccion() { return codigoTransaccion; }
    public void setCodigoTransaccion(String codigoTransaccion) { this.codigoTransaccion = codigoTransaccion; }
}
