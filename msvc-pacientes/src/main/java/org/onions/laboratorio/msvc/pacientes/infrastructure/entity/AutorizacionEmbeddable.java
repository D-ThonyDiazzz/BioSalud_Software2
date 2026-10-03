package org.onions.laboratorio.msvc.pacientes.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class AutorizacionEmbeddable {
    @Column(name = "firma_responsable")
    private String firmaResponsable;
    @Column(name = "fecha_autorizacion")
    private LocalDateTime fechaAutorizacion;

    public AutorizacionEmbeddable() {}
    public AutorizacionEmbeddable(String firmaResponsable, LocalDateTime fechaAutorizacion) {
        this.firmaResponsable = firmaResponsable;
        this.fechaAutorizacion = fechaAutorizacion;
    }
    public String getFirmaResponsable() { return firmaResponsable; }
    public void setFirmaResponsable(String firmaResponsable) { this.firmaResponsable = firmaResponsable; }
    public LocalDateTime getFechaAutorizacion() { return fechaAutorizacion; }
    public void setFechaAutorizacion(LocalDateTime fechaAutorizacion) { this.fechaAutorizacion = fechaAutorizacion; }
}
