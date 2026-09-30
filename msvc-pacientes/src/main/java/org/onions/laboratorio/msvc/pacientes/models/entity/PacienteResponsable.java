package org.onions.laboratorio.msvc.pacientes.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.onions.laboratorio.msvc.pacientes.models.vo.Autorizacion;

//Entidad hija del agregado Paciente.
//Representa el vinculo entre un paciente menor de edad y una persona responsable/apoderada.
//El responsable en si vive en el microservicio msvc-responsables; aqui solo se guarda su id.
//Regla del Negocio: nunca se elimina, se acumula el historial de responsables acompanantes.
@Entity
@Table(name = "paciente_responsables")
public class PacienteResponsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    //Referencia externa a Responsable (msvc-responsables) - solo por identificador
    @Column(name = "id_responsable", nullable = false)
    private Long idResponsable;

    //padre, madre, tutor legal, etc.
    private String relacion;

    @Embedded
    private Autorizacion autorizacion;

    public PacienteResponsable() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Long getIdResponsable() { return idResponsable; }
    public void setIdResponsable(Long idResponsable) { this.idResponsable = idResponsable; }
    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion) { this.relacion = relacion; }
    public Autorizacion getAutorizacion() { return autorizacion; }
    public void setAutorizacion(Autorizacion autorizacion) { this.autorizacion = autorizacion; }
}
