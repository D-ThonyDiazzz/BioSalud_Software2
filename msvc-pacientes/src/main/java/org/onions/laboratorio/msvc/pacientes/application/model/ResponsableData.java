package org.onions.laboratorio.msvc.pacientes.application.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Contrato de datos intercambiado con msvc-responsables. */
public class ResponsableData {
    private Long id;
    private DocumentoIdentidadData documentoIdentidad;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String sexo;
    private TelefonoData telefono;
    private CorreoElectronicoData correoElectronico;
    private LocalDateTime fechaRegistro;

    public ResponsableData() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DocumentoIdentidadData getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(DocumentoIdentidadData documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public TelefonoData getTelefono() { return telefono; }
    public void setTelefono(TelefonoData telefono) { this.telefono = telefono; }
    public CorreoElectronicoData getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronicoData correoElectronico) { this.correoElectronico = correoElectronico; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public static class DocumentoIdentidadData {
        private String tipoDocumento;
        private String numeroDocumento;
        public String getTipoDocumento() { return tipoDocumento; }
        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
        public String getNumeroDocumento() { return numeroDocumento; }
        public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    }

    public static class TelefonoData {
        private String prefijo;
        private String numeroTelefono;
        public String getPrefijo() { return prefijo; }
        public void setPrefijo(String prefijo) { this.prefijo = prefijo; }
        public String getNumeroTelefono() { return numeroTelefono; }
        public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    }

    public static class CorreoElectronicoData {
        private String direccionCorreo;
        public String getDireccionCorreo() { return direccionCorreo; }
        public void setDireccionCorreo(String direccionCorreo) { this.direccionCorreo = direccionCorreo; }
    }
}
