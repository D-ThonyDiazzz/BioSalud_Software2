package org.onions.laboratorio.msvc.perfiles.infraestructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_analisis")
public class PerfilAnalisisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_analisis")
    private Long idAnalisis;

    public PerfilAnalisisEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(Long idAnalisis) { this.idAnalisis = idAnalisis; }
}