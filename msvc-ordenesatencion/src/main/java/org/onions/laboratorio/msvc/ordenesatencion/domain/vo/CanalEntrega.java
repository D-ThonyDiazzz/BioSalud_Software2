package org.onions.laboratorio.msvc.ordenesatencion.domain.vo;


public class CanalEntrega {

    private String tipo;                 // EMAIL, WHATSAPP, PRESENCIAL...
    private String destinoContacto;
    public CanalEntrega() {}

    public CanalEntrega(String tipo, String destinoContacto) {
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