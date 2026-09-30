package org.onions.laboratorio.msvc.pacientes.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.onions.laboratorio.msvc.pacientes.models.vo.CorreoElectronico;
import org.onions.laboratorio.msvc.pacientes.models.vo.Direccion;
import org.onions.laboratorio.msvc.pacientes.models.vo.DocumentoIdentidad;
import org.onions.laboratorio.msvc.pacientes.models.vo.Telefono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

//Agregado (raiz): Paciente.
//Contiene el vinculo con sus responsables (historial acumulativo).
//Regla del Negocio: Si el paciente es menor de edad, la ficha solo se considera completa cuando
//tiene al menos un PacienteResponsable con autorizacion firmada.
//Regla del Negocio: Nunca se eliminan responsables previos; se acumulan.
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DocumentoIdentidad documentoIdentidad;

    @NotEmpty
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String sexo;

    @Embedded
    private Telefono telefono;

    @Embedded
    private Direccion direccion;

    @Embedded
    private CorreoElectronico correoElectronico;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    //Entidad hija del agregado: vinculo con responsables (historial acumulativo)
    @JsonManagedReference
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PacienteResponsable> responsables = new ArrayList<>();

    public Paciente() {
        this.fechaRegistro = LocalDateTime.now();
    }

    //Regla derivada: edad calculada a partir de fechaNacimiento
    public Integer getEdad() {
        if (fechaNacimiento == null) return null;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean esMenorEdad() {
        Integer edad = getEdad();
        return edad != null && edad < 18;
    }

    //Regla de negocio: la ficha de un menor esta completa cuando existe al menos un vinculo firmado
    public boolean tieneResponsableAutorizado() {
        return responsables.stream()
                .anyMatch(pr -> pr.getAutorizacion() != null && pr.getAutorizacion().estaFirmada());
    }

    public void agregarResponsable(PacienteResponsable pr) {
        pr.setPaciente(this);
        this.responsables.add(pr);
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
    public void setResponsables(List<PacienteResponsable> responsables) { this.responsables = responsables; }
}
