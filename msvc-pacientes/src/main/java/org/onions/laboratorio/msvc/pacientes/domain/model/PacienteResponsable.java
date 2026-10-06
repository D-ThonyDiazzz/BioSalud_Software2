package org.onions.laboratorio.msvc.pacientes.domain.model;

import org.onions.laboratorio.msvc.pacientes.domain.vo.Autorizacion;

/** Entidad hija del agregado Paciente; referencia al Responsable solo por identificador. */
public class PacienteResponsable {
    private Long id;
    private Long idPaciente;
    private Long idResponsable;
    private String relacion;
    private Autorizacion autorizacion;

    public PacienteResponsable() {}

    public PacienteResponsable(Long id, Long idPaciente, Long idResponsable,
                               String relacion, Autorizacion autorizacion) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idResponsable = idResponsable;
        this.relacion = relacion;
        this.autorizacion = autorizacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public Long getIdResponsable() { return idResponsable; }
    public void setIdResponsable(Long idResponsable) { this.idResponsable = idResponsable; }
    public String getRelacion() { return relacion; }
    public void setRelacion(String relacion) { this.relacion = relacion; }
    public Autorizacion getAutorizacion() { return autorizacion; }
    public void setAutorizacion(Autorizacion autorizacion) { this.autorizacion = autorizacion; }
}
