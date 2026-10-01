package org.onions.laboratorio.msvc.responsables.domain.model;

import org.onions.laboratorio.msvc.responsables.domain.model.vo.CorreoElectronico;
import org.onions.laboratorio.msvc.responsables.domain.model.vo.DocumentoIdentidad;
import org.onions.laboratorio.msvc.responsables.domain.model.vo.Telefono;

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

    public Responsable() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public Responsable(Long id, DocumentoIdentidad documentoIdentidad, String nombre, LocalDate fechaNacimiento, String sexo, Telefono telefono, CorreoElectronico correoElectronico, LocalDateTime fechaRegistro) {
        this.id = id;
        this.documentoIdentidad = documentoIdentidad;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro;
    }

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
}