package org.onions.laboratorio.msvc.pacientes.models.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Autorizacion {

    @Column(name = "firma_responsable")
    private String firmaResponsable;

    @Column(name = "fecha_autorizacion")
    private LocalDateTime fechaAutorizacion;

    public Autorizacion() {}

    public boolean estaFirmada() {
        return firmaResponsable != null && !firmaResponsable.isBlank();
    }

    public String getFirmaResponsable() { return firmaResponsable; }
    public void setFirmaResponsable(String firmaResponsable) { this.firmaResponsable = firmaResponsable; }
    public LocalDateTime getFechaAutorizacion() { return fechaAutorizacion; }
    public void setFechaAutorizacion(LocalDateTime fechaAutorizacion) { this.fechaAutorizacion = fechaAutorizacion; }
}
