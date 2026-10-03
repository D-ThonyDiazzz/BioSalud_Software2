package org.onions.laboratorio.msvc.ordenesatencion.application.service;

import org.onions.laboratorio.msvc.ordenesatencion.application.dto.AnalisisInfo;
import org.onions.laboratorio.msvc.ordenesatencion.application.dto.PerfilInfo;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.AnalisisPort;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.OrdenAtencionRepositoryPort;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.PacientePort;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.PerfilPort;
import org.onions.laboratorio.msvc.ordenesatencion.domain.model.DetalleOrden;
import org.onions.laboratorio.msvc.ordenesatencion.domain.model.OrdenAtencion;
import org.onions.laboratorio.msvc.ordenesatencion.domain.vo.NumeroTurno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrdenAtencionServiceImpl implements OrdenAtencionService {

    @Autowired
    private OrdenAtencionRepositoryPort repository;
    @Autowired private PacientePort pacientePort;
    @Autowired private AnalisisPort analisisPort;
    @Autowired private PerfilPort perfilPort;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenAtencion> listar() { return repository.listar(); }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenAtencion> porId(Long id) { return repository.porId(id); }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenAtencion> porPaciente(Long idPaciente) { return repository.porIdPaciente(idPaciente); }


    @Override
    @Transactional
    public OrdenAtencion crearOrden(OrdenAtencion orden) {
        if (!pacientePort.existe(orden.getIdPaciente())) {
            throw new NoSuchElementException("No existe el paciente con id"+ orden.getIdPaciente());
        }

        //RN: numero de turno consecutivo del dia
        LocalDate hoy = LocalDate.now();
        orden.setNumeroTurno(new NumeroTurno(hoy, repository.ultimoTurnoDelDia(hoy) + 1));

        orden.getDetalles().forEach(DetalleOrden::calcularSubtotal);
        orden.recalcularMontoTotal();
        return repository.guardar(orden);
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> agregarAnalisisAOrden(Long idOrden, Long idAnalisis) {
        Optional<OrdenAtencion> op = repository.porId(idOrden);
        if (op.isEmpty()) return Optional.empty();
        OrdenAtencion orden = op.get();

        AnalisisInfo a = analisisPort.porId(idAnalisis)
                .orElseThrow(() -> new NoSuchElementException("No existe el analisis con id " + idAnalisis));
        if (!a.isVigente()) {
            throw new IllegalStateException("El analisis " + a.getId() + " no esta vigente");
        }

        DetalleOrden det = new DetalleOrden();
        det.setIdAnalisis(a.getId());
        det.setNombreItem(a.getNombre());
        det.setPrecioUnitario(a.getPrecio());
        det.setDescuentoAplicado(BigDecimal.ZERO);   //Sin convenios (fuera del core)
        det.calcularSubtotal();

        orden.agregarDetalle(det);
        orden.recalcularMontoTotal();
        return Optional.of(repository.guardar(orden));
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> agregarPerfilAOrden(Long idOrden, Long idPerfil) {
        Optional<OrdenAtencion> op = repository.porId(idOrden);
        if (op.isEmpty()) return Optional.empty();
        OrdenAtencion orden = op.get();

        PerfilInfo per = perfilPort.porId(idPerfil)
                .orElseThrow(() -> new NoSuchElementException("No existe el perfil con id " + idPerfil));

        DetalleOrden det = new DetalleOrden();
        det.setIdPerfil(per.getId());
        det.setNombreItem(per.getNombre());
        //El precio del perfil se define como suma de sus analisis; aqui simplificamos con 0
        det.setPrecioUnitario(BigDecimal.ZERO);
        det.setDescuentoAplicado(BigDecimal.ZERO);
        det.calcularSubtotal();

        orden.agregarDetalle(det);
        orden.recalcularMontoTotal();
        return Optional.of(repository.guardar(orden));
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> quitarDetalle(Long idOrden, Long idDetalle) {
        Optional<OrdenAtencion> op = repository.porId(idOrden);
        if (op.isEmpty()) return Optional.empty();
        OrdenAtencion orden = op.get();
        orden.quitarDetalle(idDetalle);          // la lógica ahora vive en el dominio
        orden.recalcularMontoTotal();
        return Optional.of(repository.guardar(orden));
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> cambiarEstado(Long idOrden, String estado) {
        Optional<OrdenAtencion> op = repository.porId(idOrden);
        if (op.isEmpty()) return Optional.empty();
        OrdenAtencion o = op.get();
        o.setEstado(estado);
        return Optional.of(repository.guardar(o));
    }

    @Override
    @Transactional
    public void eliminar(Long id) { repository.eliminar(id); }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenAtencion> detalleCompleto(Long id) {
        return repository.porId(id);   // el adaptador ya carga los detalles al mapear
    }
}
