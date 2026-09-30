package org.onions.laboratorio.msvc.pacientes.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Responsable {

    private Long id;
    private DocumentoIdentidad documentoIdentidad;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String sexo;
    private Telefono telefono;
    private CorreoElectronico correoElectronico;
    private LocalDateTime fechaRegistro;

    public Responsable() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DocumentoIdentidad getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public Telefono getTelefono() { return telefono; }
    public void setTelefono(Telefono telefono) { this.telefono = telefono; }
    public CorreoElectronico getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronico correoElectronico) { this.correoElectronico = correoElectronico; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }


    public static class DocumentoIdentidad {
        private String tipoDocumento;
        private String numeroDocumento;
        public String getTipoDocumento() { return tipoDocumento; }
        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
        public String getNumeroDocumento() { return numeroDocumento; }
        public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    }

    public static class Telefono {
        private String prefijo;
        private String numeroTelefono;
        public String getPrefijo() { return prefijo; }
        public void setPrefijo(String prefijo) { this.prefijo = prefijo; }
        public String getNumeroTelefono() { return numeroTelefono; }
        public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    }

    public static class CorreoElectronico {
        private String direccionCorreo;
        public String getDireccionCorreo() { return direccionCorreo; }
        public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
    }
}