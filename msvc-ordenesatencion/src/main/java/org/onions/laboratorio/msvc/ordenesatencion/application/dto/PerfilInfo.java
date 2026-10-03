package org.onions.laboratorio.msvc.ordenesatencion.application.dto;

public class PerfilInfo {
    private final Long id;
    private final String nombre;

    public PerfilInfo(Long id, String nombre) { this.id = id; this.nombre = nombre; }
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}
