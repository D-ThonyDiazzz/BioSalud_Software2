package org.onions.laboratorio.msvc.pacientes.domain.model.vo;

public class Telefono {
    private String prefijo;
    private String numeroTelefono;

    public Telefono() {}

    public Telefono(String prefijo, String numeroTelefono) {
        this.prefijo = prefijo;
        this.numeroTelefono = numeroTelefono;
    }

    public String getPrefijo() { return prefijo; }
    public void setPrefijo(String prefijo) { this.prefijo = prefijo; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
}
