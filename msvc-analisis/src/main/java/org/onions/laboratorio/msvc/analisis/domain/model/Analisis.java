package org.onions.laboratorio.msvc.analisis.domain.model;

import org.onions.laboratorio.msvc.analisis.domain.vo.CondicionesPrevias;
import org.onions.laboratorio.msvc.analisis.domain.vo.MedioBiologico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Agregado raiz del catalogo de analisis. No depende de Spring ni de JPA. */
public class Analisis {

    private Long id;
    private String nombreAnalisis;
    private String descripcion;
    private BigDecimal precio;
    private MedioBiologico medioBiologico;
    private CondicionesPrevias condicionesPrevias;
    private String estado;
    private LocalDateTime fechaRegistro;

    public Analisis() {
        this.estado = "VIGENTE";
        this.fechaRegistro = LocalDateTime.now();
    }

    public Analisis(Long id, String nombreAnalisis, String descripcion, BigDecimal precio,
                    MedioBiologico medioBiologico, CondicionesPrevias condicionesPrevias,
                    String estado, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombreAnalisis = nombreAnalisis;
        this.descripcion = descripcion;
        this.precio = precio;
        this.medioBiologico = medioBiologico;
        this.condicionesPrevias = condicionesPrevias;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public boolean estaVigente() {
        return "VIGENTE".equalsIgnoreCase(estado);
    }

    public boolean requiereAyunoEspecial() {
        return condicionesPrevias != null && condicionesPrevias.requiereAyunoEspecial();
    }

    public void darDeBaja() {
        this.estado = "NO_VIGENTE";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreAnalisis() { return nombreAnalisis; }
    public void setNombreAnalisis(String nombreAnalisis) { this.nombreAnalisis = nombreAnalisis; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public MedioBiologico getMedioBiologico() { return medioBiologico; }
    public void setMedioBiologico(MedioBiologico medioBiologico) { this.medioBiologico = medioBiologico; }
    public CondicionesPrevias getCondicionesPrevias() { return condicionesPrevias; }
    public void setCondicionesPrevias(CondicionesPrevias condicionesPrevias) { this.condicionesPrevias = condicionesPrevias; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
