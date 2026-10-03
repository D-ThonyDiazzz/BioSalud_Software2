package org.onions.laboratorio.msvc.ordenesatencion.application.dto;

import java.math.BigDecimal;

public class AnalisisInfo {
    private final Long id;
    private final String nombre;
    private final BigDecimal precio;
    private final boolean vigente;

    public AnalisisInfo(Long id, String nombre, BigDecimal precio, boolean vigente) {
        this.id = id; this.nombre = nombre; this.precio = precio; this.vigente = vigente;
    }
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public boolean isVigente() { return vigente; }
}
