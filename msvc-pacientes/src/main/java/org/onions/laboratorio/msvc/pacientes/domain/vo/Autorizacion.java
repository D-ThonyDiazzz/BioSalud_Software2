package org.onions.laboratorio.msvc.pacientes.domain.vo;

import java.time.LocalDateTime;

public class Autorizacion {
    private String firmaResponsable;
    private LocalDateTime fechaAutorizacion;

    public Autorizacion() {}

    public Autorizacion(String firmaResponsable, LocalDateTime fechaAutorizacion) {
        this.firmaResponsable = firmaResponsable;
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public boolean estaFirmada() {
        return firmaResponsable != null && !firmaResponsable.isBlank();
    }

    public String getFirmaResponsable() { return firmaResponsable; }
    public void setFirmaResponsable(String firmaResponsable) { this.firmaResponsable = firmaResponsable; }
    public LocalDateTime getFechaAutorizacion() { return fechaAutorizacion; }
    public void setFechaAutorizacion(LocalDateTime fechaAutorizacion) { this.fechaAutorizacion = fechaAutorizacion; }
}
