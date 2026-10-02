package org.onions.laboratorio.msvc.pacientes.infrastructure.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private DocumentoIdentidadEmbeddable documentoIdentidad;
    @Column(nullable = false)
    private String nombre;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String sexo;
    @Embedded
    private TelefonoEmbeddable telefono;
    @Embedded
    private DireccionEmbeddable direccion;
    @Embedded
    private CorreoElectronicoEmbeddable correoElectronico;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL,
            orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PacienteResponsableEntity> responsables = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DocumentoIdentidadEmbeddable getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(DocumentoIdentidadEmbeddable documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public TelefonoEmbeddable getTelefono() { return telefono; }
    public void setTelefono(TelefonoEmbeddable telefono) { this.telefono = telefono; }
    public DireccionEmbeddable getDireccion() { return direccion; }
    public void setDireccion(DireccionEmbeddable direccion) { this.direccion = direccion; }
    public CorreoElectronicoEmbeddable getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronicoEmbeddable correoElectronico) { this.correoElectronico = correoElectronico; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<PacienteResponsableEntity> getResponsables() { return responsables; }
    public void setResponsables(List<PacienteResponsableEntity> responsables) {
        this.responsables = responsables == null ? new ArrayList<>() : responsables;
    }
}
