package org.onions.laboratorio.msvc.ordenesatencion.models.entity;

import jakarta.persistence.*;
import org.onions.laboratorio.msvc.ordenesatencion.models.vo.NumeroTurno;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Agregado (raiz): OrdenAtencion.
//Agrupa la solicitud de atencion con su DetalleOrden como una sola unidad.
//Regla del Negocio: solo es valida si tiene al menos un analisis o perfil.
//Regla del Negocio: si el paciente es menor, debe contar con datos completos del responsable y firma.
//Regla del Negocio: los precios y descuentos se congelan al crear la orden.
//Referencia a Paciente SOLO por identificador.
@Entity
@Table(name = "ordenes_atencion")
public class OrdenAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Referencias externas (msvc-pacientes)
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    @Embedded
    private NumeroTurno numeroTurno;

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
    private List<DetalleOrden> detalles = new ArrayList<>();


    public OrdenAtencion() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = "PENDIENTE";
        this.montoTotal = BigDecimal.ZERO;
    }

    public void agregarDetalle(DetalleOrden det) {
        this.detalles.add(det);
    }

    public void quitarDetalle(DetalleOrden det) {
        this.detalles.remove(det);
    }

    //RN: la orden es valida si tiene al menos un detalle
    public boolean esValida() {
        return detalles != null && !detalles.isEmpty();
    }

    //Recalcula el monto total sumando los subtotales de los detalles
    public BigDecimal recalcularMontoTotal() {
        this.montoTotal = detalles.stream()
                .map(DetalleOrden::getSubtotal)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return this.montoTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public NumeroTurno getNumeroTurno() { return numeroTurno; }
    public void setNumeroTurno(NumeroTurno numeroTurno) { this.numeroTurno = numeroTurno; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isEsParaMenorEdad() { return esParaMenorEdad; }
    public void setEsParaMenorEdad(boolean esParaMenorEdad) { this.esParaMenorEdad = esParaMenorEdad; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
   }
