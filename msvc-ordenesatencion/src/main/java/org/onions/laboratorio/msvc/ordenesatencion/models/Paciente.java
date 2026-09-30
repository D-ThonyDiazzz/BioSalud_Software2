package org.onions.laboratorio.msvc.ordenesatencion.models;

public class Paciente {

    private Long id;
    private String nombre;
    private DocumentoIdentidad documentoIdentidad;
    private CorreoElectronico correoElectronico;

    public Paciente() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public DocumentoIdentidad getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public CorreoElectronico getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronico correoElectronico) { this.correoElectronico = correoElectronico; }

    public static class DocumentoIdentidad {
        private String tipoDocumento;
        private String numeroDocumento;
        public String getTipoDocumento() { return tipoDocumento; }
        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
        public String getNumeroDocumento() { return numeroDocumento; }
        public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    }

    public static class CorreoElectronico {
        private String direccionCorreo;
        public String getDireccionCorreo() { return direccionCorreo; }
        public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
    }
}