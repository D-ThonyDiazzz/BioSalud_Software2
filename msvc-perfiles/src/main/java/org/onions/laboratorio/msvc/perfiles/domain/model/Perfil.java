package org.onions.laboratorio.msvc.perfiles.domain.model;

import jakarta.validation.constraints.NotEmpty;
import org.onions.laboratorio.msvc.perfiles.domain.Analisis;
import org.onions.laboratorio.msvc.perfiles.domain.vo.CodigoPerfil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Agregado (raiz): Perfil.
//Regla del Negocio: solo puede ofrecerse si TODOS sus analisis estan vigentes en msvc-analisis.
public class Perfil {

    private Long id;
    private CodigoPerfil codigoPerfil;

    @NotEmpty
    private String nombre;

    private String descripcion;
    private LocalDateTime fechaRegistro;
    private List<PerfilAnalisis> analisisPerfil;

    //Detalles cargados via Feign (no se persiste)
    private List<Analisis> analisis = new ArrayList<>();

    public Perfil() {
        this.fechaRegistro = LocalDateTime.now();
        this.analisisPerfil = new ArrayList<>();
    }

    public void agregarAnalisis(Long idAnalisis) {
        PerfilAnalisis pa = new PerfilAnalisis(idAnalisis);
        if (this.analisisPerfil.contains(pa)) {
            throw new IllegalStateException(
                    "El analisis " + idAnalisis + " ya forma parte del perfil.");
        }
        this.analisisPerfil.add(pa);
    }

    public void quitarAnalisis(Long idAnalisis) {
        this.analisisPerfil.removeIf(pa -> pa.getIdAnalisis().equals(idAnalisis));
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