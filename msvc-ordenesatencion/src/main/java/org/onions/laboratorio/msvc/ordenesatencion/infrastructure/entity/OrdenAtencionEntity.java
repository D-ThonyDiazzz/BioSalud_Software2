package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Agregado (raiz): OrdenAtencionEntity.
//Agrupa la solicitud de atencion con su DetalleOrdenEntity como una sola unidad.
//Regla del Negocio: solo es valida si tiene al menos un analisis o perfil.
//Regla del Negocio: si el paciente es menor, debe contar con datos completos del responsable y firma.
//Regla del Negocio: los precios y descuentos se congelan al crear la orden.
//Referencia a Paciente SOLO por identificador.
@Entity
@Table(name = "ordenes_atencion")
public class OrdenAtencionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Referencias externas (msvc-pacientes)
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    @Embedded
    private NumeroTurnoEmbeddable numeroTurno;

    @Embedded
    private CanalEntregaEmbeddable canalEntrega;

    public CanalEntregaEmbeddable getCanalEntrega() { return canalEntrega; }
    public void setCanalEntrega(CanalEntregaEmbeddable canalEntrega) { this.canalEntrega = canalEntrega; }

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    //PENDIENTE, COBRADA, ATENDIDA, ENTREGADA, ANULADA
    private String estado;

    @Column(name = "es_para_menor_edad")
    private boolean esParaMenorEdad;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden")
    private List<DetalleOrdenEntity> detalles = new ArrayList<>();


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public NumeroTurnoEmbeddable getNumeroTurno() { return numeroTurno; }
    public void setNumeroTurno(NumeroTurnoEmbeddable numeroTurnoEmbeddable) { this.numeroTurno= numeroTurnoEmbeddable; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isEsParaMenorEdad() { return esParaMenorEdad; }
    public void setEsParaMenorEdad(boolean esParaMenorEdad) { this.esParaMenorEdad = esParaMenorEdad; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<DetalleOrdenEntity> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrdenEntity> detalles) { this.detalles = detalles; }
   }
