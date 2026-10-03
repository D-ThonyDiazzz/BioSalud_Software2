package org.onions.laboratorio.msvc.pacientes.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente_responsables")
public class PacienteResponsableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private PacienteEntity paciente;
    @Column(name = "id_responsable", nullable = false)
    private Long idResponsable;
    private String relacion;
    @Embedded
    private AutorizacionEmbeddable autorizacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PacienteEntity getPaciente() { return paciente; }
    public void setPaciente(PacienteEntity paciente) { this.paciente = paciente; }
    public Long getIdResponsable() { return idResponsable; }
    public void setIdResponsable(Long idResponsable) { this.idResponsable = idResponsable; }
    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion) { this.relacion = relacion; }
    public AutorizacionEmbeddable getAutorizacion() { return autorizacion; }
    public void setAutorizacion(AutorizacionEmbeddable autorizacion) { this.autorizacion = autorizacion; }
}
