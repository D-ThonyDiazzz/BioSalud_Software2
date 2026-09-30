package org.onions.laboratorio.msvc.analisis.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.onions.laboratorio.msvc.analisis.models.vo.CondicionesPrevias;
import org.onions.laboratorio.msvc.analisis.models.vo.MedioBiologico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Agregado (raiz): Analisis.
//Cada analisis es una entrada de catalogo con su propia tarifa, condiciones y medio.
//Regla del Negocio: solo los analisis "vigentes" pueden ofrecerse a los pacientes.
@Entity
@Table(name = "analisis")
public class Analisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "nombre_analisis")
    private String nombreAnalisis;

    @Column(length = 500)
    private String descripcion;

    private BigDecimal precio;

    @Embedded
    private MedioBiologico medioBiologico;

    @Embedded
    private CondicionesPrevias condicionesPrevias;

    //VIGENTE / NO_VIGENTE
    private String estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public Analisis() {
        this.estado = "VIGENTE";
        this.fechaRegistro = LocalDateTime.now();
    }

    public boolean estaVigente() {
        return "VIGENTE".equalsIgnoreCase(this.estado);
    }

    public boolean requiereAyunoEspecial() {
        return condicionesPrevias != null && condicionesPrevias.requiereAyunoEspecial();
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
