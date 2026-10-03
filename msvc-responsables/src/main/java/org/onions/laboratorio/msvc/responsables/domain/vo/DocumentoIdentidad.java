package org.onions.laboratorio.msvc.responsables.domain.vo;

public class DocumentoIdentidad {

    private String tipoDocumento;
    private String numeroDocumento;

    public DocumentoIdentidad() {}

    public DocumentoIdentidad(String tipoDocumento, String numeroDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
}