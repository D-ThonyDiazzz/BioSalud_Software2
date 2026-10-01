package org.onions.laboratorio.msvc.responsables.infraestructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

//Agregado (raiz): Responsable.
//Es un agregado independiente porque un mismo responsable puede estar
//vinculado a varios pacientes distintos a lo largo del tiempo.
@Entity
@Table(name = "responsables")
public class ResponsableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DocumentoIdentidadEmbeddable documentoIdentidad;

    @NotEmpty
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String sexo;

    @Embedded
    private TelefonoEmbeddable telefono;

    @Embedded
    private CorreoElectronicoEmbeddable correoElectronico;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public ResponsableEntity() {
        this.fechaRegistro = LocalDateTime.now();
    }

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

    public CorreoElectronicoEmbeddable getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(CorreoElectronicoEmbeddable correoElectronico) { this.correoElectronico = correoElectronico; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}