package org.onions.laboratorio.msvc.perfiles.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.onions.laboratorio.msvc.perfiles.models.Analisis;
import org.onions.laboratorio.msvc.perfiles.models.vo.CodigoPerfil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Agregado (raiz): Perfil.
//Paquete comercial que agrupa varios analisis (ej. "Chequeo Preventivo Basico").
//Regla del Negocio: solo puede ofrecerse si TODOS sus analisis estan vigentes en msvc-analisis.
@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CodigoPerfil codigoPerfil;

    @NotEmpty
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    //Entidades hijas del agregado: analisis que componen el perfil
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private List<PerfilAnalisis> analisisPerfil;

    //Detalles de los analisis cargados via Feign (no se persiste)
    @Transient
    private List<Analisis> analisis = new ArrayList<>();

    public Perfil() {
        this.fechaRegistro = LocalDateTime.now();
        this.analisisPerfil = new ArrayList<>();
    }

    public void agregarAnalisis(PerfilAnalisis pa) {
        this.analisisPerfil.add(pa);
    }

    public void quitarAnalisis(PerfilAnalisis pa) {
        this.analisisPerfil.remove(pa);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CodigoPerfil getCodigoPerfil() { return codigoPerfil; }
    public void setCodigoPerfil(CodigoPerfil codigoPerfil) { this.codigoPerfil = codigoPerfil; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<PerfilAnalisis> getAnalisisPerfil() { return analisisPerfil; }
    public void setAnalisisPerfil(List<PerfilAnalisis> analisisPerfil) { this.analisisPerfil = analisisPerfil; }
    public List<Analisis> getAnalisis() { return analisis; }
    public void setAnalisis(List<Analisis> analisis) { this.analisis = analisis; }
}
