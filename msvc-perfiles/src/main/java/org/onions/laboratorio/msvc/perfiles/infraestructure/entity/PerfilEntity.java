package org.onions.laboratorio.msvc.perfiles.infraestructure.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfiles")
public class PerfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CodigoPerfilEmbeddable codigoPerfil;

    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private List<PerfilAnalisisEntity> analisisPerfil = new ArrayList<>();

    public PerfilEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CodigoPerfilEmbeddable getCodigoPerfil() { return codigoPerfil; }
    public void setCodigoPerfil(CodigoPerfilEmbeddable codigoPerfil) { this.codigoPerfil = codigoPerfil; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<PerfilAnalisisEntity> getAnalisisPerfil() { return analisisPerfil; }
    public void setAnalisisPerfil(List<PerfilAnalisisEntity> analisisPerfil) { this.analisisPerfil = analisisPerfil; }
}