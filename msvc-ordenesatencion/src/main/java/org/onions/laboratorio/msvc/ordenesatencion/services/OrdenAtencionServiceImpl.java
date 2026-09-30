package org.onions.laboratorio.msvc.ordenesatencion.services;

import org.onions.laboratorio.msvc.ordenesatencion.client.AnalisisClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.client.PacienteClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.client.PerfilClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.models.Analisis;
import org.onions.laboratorio.msvc.ordenesatencion.models.Paciente;
import org.onions.laboratorio.msvc.ordenesatencion.models.Perfil;
import org.onions.laboratorio.msvc.ordenesatencion.models.entity.ComprobantePago;
import org.onions.laboratorio.msvc.ordenesatencion.models.entity.DetalleOrden;
import org.onions.laboratorio.msvc.ordenesatencion.models.entity.OrdenAtencion;
import org.onions.laboratorio.msvc.ordenesatencion.models.vo.NumeroTurno;
import org.onions.laboratorio.msvc.ordenesatencion.repositories.OrdenAtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenAtencionServiceImpl implements OrdenAtencionService {

    @Autowired
    private OrdenAtencionRepository repository;

    @Autowired
    private PacienteClientRest pacienteClient;

    @Autowired
    private AnalisisClientRest analisisClient;

    @Autowired
    private PerfilClientRest perfilClient;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenAtencion> listar() {
        return (List<OrdenAtencion>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenAtencion> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenAtencion> porPaciente(Long idPaciente) {
        return repository.findByIdPaciente(idPaciente);
    }

    @Override
    @Transactional
    public OrdenAtencion crearOrden(OrdenAtencion orden) {
        //Valida existencia del paciente
        Paciente p = pacienteClient.detalle(orden.getIdPaciente());
        orden.setIdPaciente(p.getId());

        //RN: numero de turno consecutivo del dia
        LocalDate hoy = LocalDate.now();
        Integer siguiente = repository.maxTurnoDelDia(hoy) + 1;
        orden.setNumeroTurno(new NumeroTurno(hoy, siguiente));

        //Recalcula total con los detalles ya presentes
        orden.getDetalles().forEach(DetalleOrden::calcularSubtotal);
        orden.recalcularMontoTotal();
        return repository.save(orden);
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> agregarAnalisisAOrden(Long idOrden, Long idAnalisis) {
        Optional<OrdenAtencion> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencion orden = op.get();

            //Obtiene informacion del analisis y valida vigencia
            Analisis a = analisisClient.detalle(idAnalisis);
            if (!a.estaVigente()) {
                throw new IllegalStateException("El analisis " + a.getId() + " no esta vigente");
            }

            DetalleOrden det = new DetalleOrden();
            det.setIdAnalisis(a.getId());
            det.setNombreItem(a.getNombreAnalisis());
            det.setPrecioUnitario(a.getPrecio());

            //Sin convenios (fuera del core): no se aplica descuento
            det.setDescuentoAplicado(BigDecimal.ZERO);
            det.calcularSubtotal();

            orden.agregarDetalle(det);
            orden.recalcularMontoTotal();
            return Optional.of(repository.save(orden));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> agregarPerfilAOrden(Long idOrden, Long idPerfil) {
        Optional<OrdenAtencion> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencion orden = op.get();
            Perfil per = perfilClient.detalle(idPerfil);

            DetalleOrden det = new DetalleOrden();
            det.setIdPerfil(per.getId());
            det.setNombreItem(per.getNombre());
            //El precio del perfil se define como suma de sus analisis; aqui simplificamos con 0
            //en un flujo real, se consultaria el detalle del perfil con sus analisis
            det.setPrecioUnitario(BigDecimal.ZERO);
            det.setDescuentoAplicado(BigDecimal.ZERO);
            det.calcularSubtotal();

            orden.agregarDetalle(det);
            orden.recalcularMontoTotal();
            return Optional.of(repository.save(orden));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> quitarDetalle(Long idOrden, Long idDetalle) {
        Optional<OrdenAtencion> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencion orden = op.get();
            orden.getDetalles().removeIf(d -> d.getId() != null && d.getId().equals(idDetalle));
            orden.recalcularMontoTotal();
            return Optional.of(repository.save(orden));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> emitirComprobante(Long idOrden, ComprobantePago comprobante) {
        Optional<OrdenAtencion> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencion orden = op.get();
            if (!orden.esValida()) {
                throw new IllegalStateException("La orden " + idOrden + " no es valida: no tiene analisis ni perfiles");
            }
            comprobante.setMontoTotal(orden.getMontoTotal());
            orden.setComprobante(comprobante);
            orden.setEstado("COBRADA");
            return Optional.of(repository.save(orden));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<OrdenAtencion> cambiarEstado(Long idOrden, String estado) {
        Optional<OrdenAtencion> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencion o = op.get();
            o.setEstado(estado);
            return Optional.of(repository.save(o));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenAtencion> detalleCompleto(Long id) {
        Optional<OrdenAtencion> op = repository.findById(id);
        op.ifPresent(o -> {
            o.getDetalles().size();
            if (o.getComprobante() != null) o.getComprobante().getId();
        });
        return op;
    }
}
