package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CanalEntregaEmbeddable {

    @Column(name = "canal_tipo")
    private String tipo;                 // EMAIL, WHATSAPP, PRESENCIAL...

    @Column(name = "canal_destino")
    private String destinoContacto;

    public CanalEntregaEmbeddable() {}

    public CanalEntregaEmbeddable(String tipo, String destinoContacto) {
        this.tipo = tipo;
        this.destinoContacto = destinoContacto;
    }

    public boolean esCanalDigital() {   // los valores son una suposición mía, ajústalos
        return "EMAIL".equalsIgnoreCase(tipo) || "WHATSAPP".equalsIgnoreCase(tipo);
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDestinoContacto() { return destinoContacto; }
    public void setDestinoContacto(String destinoContacto) { this.destinoContacto = destinoContacto; }
}