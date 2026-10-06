package org.onions.laboratorio.msvc.pacientes.domain.model;

import org.onions.laboratorio.msvc.pacientes.domain.vo.CorreoElectronico;
import org.onions.laboratorio.msvc.pacientes.domain.vo.Direccion;
import org.onions.laboratorio.msvc.pacientes.domain.vo.DocumentoIdentidad;
import org.onions.laboratorio.msvc.pacientes.domain.vo.Telefono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/** Agregado raiz de la ficha de paciente. No contiene dependencias de persistencia. */
public class Paciente {
    private Long id;
    private DocumentoIdentidad documentoIdentidad;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String sexo;
    private Telefono telefono;
    private Direccion direccion;
    private CorreoElectronico correoElectronico;
    private LocalDateTime fechaRegistro;
    private List<PacienteResponsable> responsables = new ArrayList<>();

    public Paciente() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public Paciente(Long id, DocumentoIdentidad documentoIdentidad, String nombre,
                    LocalDate fechaNacimiento, String sexo, Telefono telefono,
                    Direccion direccion, CorreoElectronico correoElectronico,
                    LocalDateTime fechaRegistro, List<PacienteResponsable> responsables) {
        this.id = id;
        this.documentoIdentidad = documentoIdentidad;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro;
        this.responsables = responsables == null ? new ArrayList<>() : new ArrayList<>(responsables);
    }

    public Integer getEdad() {
        if (fechaNacimiento == null) return null;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean esMenorEdad() {
        Integer edad = getEdad();
        return edad != null && edad < 18;
    }

    public boolean tieneResponsableAutorizado() {
        return responsables.stream().anyMatch(vinculo -> vinculo.getAutorizacion() != null
                && vinculo.getAutorizacion().estaFirmada());
    }

    public void agregarResponsable(PacienteResponsable vinculo) {
        if (vinculo == null) throw new IllegalArgumentException("El vinculo responsable es obligatorio");
        vinculo.setIdPaciente(id);
        responsables.add(vinculo);
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
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public CorreoElectronico getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronico correoElectronico) { this.correoElectronico = correoElectronico; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<PacienteResponsable> getResponsables() { return responsables; }
    public void setResponsables(List<PacienteResponsable> responsables) {
        this.responsables = responsables == null ? new ArrayList<>() : new ArrayList<>(responsables);
    }
}
