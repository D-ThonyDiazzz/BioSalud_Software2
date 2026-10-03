package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity;

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


    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDestinoContacto() { return destinoContacto; }
    public void setDestinoContacto(String destinoContacto) { this.destinoContacto = destinoContacto; }
}