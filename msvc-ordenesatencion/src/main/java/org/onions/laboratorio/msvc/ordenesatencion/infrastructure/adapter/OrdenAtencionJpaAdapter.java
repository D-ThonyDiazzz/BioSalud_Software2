package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.adapter;

import org.onions.laboratorio.msvc.ordenesatencion.application.port.OrdenAtencionRepositoryPort;
import org.onions.laboratorio.msvc.ordenesatencion.domain.model.DetalleOrden;
import org.onions.laboratorio.msvc.ordenesatencion.domain.model.OrdenAtencion;
import org.onions.laboratorio.msvc.ordenesatencion.domain.vo.CanalEntrega;
import org.onions.laboratorio.msvc.ordenesatencion.domain.vo.NumeroTurno;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.CanalEntregaEmbeddable;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.DetalleOrdenEntity;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.NumeroTurnoEmbeddable;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.repository.OrdenAtencionJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrdenAtencionJpaAdapter implements OrdenAtencionRepositoryPort {

    private final OrdenAtencionJpaRepository repository;

    public OrdenAtencionJpaAdapter(OrdenAtencionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrdenAtencion> listar() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<OrdenAtencion> porId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<OrdenAtencion> porIdPaciente(Long idPaciente) {
        return repository.findByIdPaciente(idPaciente).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public OrdenAtencion guardar(OrdenAtencion orden) {
        return toDomain(repository.save(toEntity(orden)));
    }

    @Override
    public void eliminar(Long id) { repository.deleteById(id); }

    @Override
    public int ultimoTurnoDelDia(LocalDate fecha) { return repository.maxTurnoDelDia(fecha); }

    // ---------- entidad -> dominio ----------
    private OrdenAtencion toDomain(OrdenAtencionEntity e) {
        OrdenAtencion d = new OrdenAtencion();
        d.setId(e.getId());
        d.setIdPaciente(e.getIdPaciente());
        d.setMontoTotal(e.getMontoTotal());
        d.setEstado(e.getEstado());
        d.setEsParaMenorEdad(e.isEsParaMenorEdad());
        d.setFechaRegistro(e.getFechaRegistro());
        if (e.getNumeroTurno() != null) {
            d.setNumeroTurno(new NumeroTurno(e.getNumeroTurno().getFechaTurno(), e.getNumeroTurno().getNumero()));
        }
        if (e.getCanalEntrega() != null) {
            d.setCanalEntrega(new CanalEntrega(e.getCanalEntrega().getTipo(), e.getCanalEntrega().getDestinoContacto()));
        }
        d.setDetalles(e.getDetalles().stream().map(this::toDomainDetalle)
                .collect(Collectors.toCollection(ArrayList::new)));   // lista modificable, NO toList()
        return d;
    }

    private DetalleOrden toDomainDetalle(DetalleOrdenEntity e) {
        DetalleOrden d = new DetalleOrden();
        d.setId(e.getId());
        d.setIdOrdenAtencion(e.getIdOrdenAtencion());
        d.setIdAnalisis(e.getIdAnalisis());
        d.setIdPerfil(e.getIdPerfil());
        d.setNombreItem(e.getNombreItem());
        d.setPrecioUnitario(e.getPrecioUnitario());
        d.setDescuentoAplicado(e.getDescuentoAplicado());
        d.setSubtotal(e.getSubtotal());
        return d;
    }

    // ---------- dominio -> entidad ----------
    private OrdenAtencionEntity toEntity(OrdenAtencion d) {
        OrdenAtencionEntity e = new OrdenAtencionEntity();
        e.setId(d.getId());
        e.setIdPaciente(d.getIdPaciente());
        e.setMontoTotal(d.getMontoTotal());
        e.setEstado(d.getEstado());
        e.setEsParaMenorEdad(d.isEsParaMenorEdad());
        e.setFechaRegistro(d.getFechaRegistro());
        if (d.getNumeroTurno() != null) {
            e.setNumeroTurno(new NumeroTurnoEmbeddable(d.getNumeroTurno().getFechaTurno(), d.getNumeroTurno().getNumero()));
        }
        if (d.getCanalEntrega() != null) {
            e.setCanalEntrega(new CanalEntregaEmbeddable(d.getCanalEntrega().getTipo(), d.getCanalEntrega().getDestinoContacto()));
        }
        e.setDetalles(d.getDetalles().stream().map(this::toEntityDetalle)
                .collect(Collectors.toCollection(ArrayList::new)));
        return e;
    }

    private DetalleOrdenEntity toEntityDetalle(DetalleOrden d) {
        DetalleOrdenEntity e = new DetalleOrdenEntity();
        e.setId(d.getId());
        // idOrdenAtencion NO se copia: esa columna es de solo lectura (insertable=false)
        e.setIdAnalisis(d.getIdAnalisis());
        e.setIdPerfil(d.getIdPerfil());
        e.setNombreItem(d.getNombreItem());
        e.setPrecioUnitario(d.getPrecioUnitario());
        e.setDescuentoAplicado(d.getDescuentoAplicado());
        e.setSubtotal(d.getSubtotal());
        return e;
    }
}
