package org.onions.laboratorio.msvc.ordenesatencion.application.service;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.AnalisisClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.PacienteClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.PerfilClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Analisis;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Paciente;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Perfil;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.DetalleOrdenEntity;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.vo.NumeroTurnoEmbeddable;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.repository.OrdenAtencionRepository;
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
    public List<OrdenAtencionEntity> listar() {
        return (List<OrdenAtencionEntity>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenAtencionEntity> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenAtencionEntity> porPaciente(Long idPaciente) {
        return repository.findByIdPaciente(idPaciente);
    }

    @Override
    @Transactional
    public OrdenAtencionEntity crearOrden(OrdenAtencionEntity orden) {
        //Valida existencia del paciente
        Paciente p = pacienteClient.detalle(orden.getIdPaciente());
        orden.setIdPaciente(p.getId());

        //RN: numero de turno consecutivo del dia
        LocalDate hoy = LocalDate.now();
        Integer siguiente = repository.maxTurnoDelDia(hoy) + 1;
        orden.setNumeroTurno(new NumeroTurnoEmbeddable(hoy, siguiente));

        //Recalcula total con los detalles ya presentes
        orden.getDetalles().forEach(DetalleOrdenEntity::calcularSubtotal);
        orden.recalcularMontoTotal();
        return repository.save(orden);
    }

    @Override
    @Transactional
    public Optional<OrdenAtencionEntity> agregarAnalisisAOrden(Long idOrden, Long idAnalisis) {
        Optional<OrdenAtencionEntity> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencionEntity orden = op.get();

            //Obtiene informacion del analisis y valida vigencia
            Analisis a = analisisClient.detalle(idAnalisis);
            if (!a.estaVigente()) {
                throw new IllegalStateException("El analisis " + a.getId() + " no esta vigente");
            }

            DetalleOrdenEntity det = new DetalleOrdenEntity();
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
    public Optional<OrdenAtencionEntity> agregarPerfilAOrden(Long idOrden, Long idPerfil) {
        Optional<OrdenAtencionEntity> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencionEntity orden = op.get();
            Perfil per = perfilClient.detalle(idPerfil);

            DetalleOrdenEntity det = new DetalleOrdenEntity();
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
    public Optional<OrdenAtencionEntity> quitarDetalle(Long idOrden, Long idDetalle) {
        Optional<OrdenAtencionEntity> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencionEntity orden = op.get();
            orden.getDetalles().removeIf(d -> d.getId() != null && d.getId().equals(idDetalle));
            orden.recalcularMontoTotal();
            return Optional.of(repository.save(orden));
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<OrdenAtencionEntity> cambiarEstado(Long idOrden, String estado) {
        Optional<OrdenAtencionEntity> op = repository.findById(idOrden);
        if (op.isPresent()) {
            OrdenAtencionEntity o = op.get();
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
    public Optional<OrdenAtencionEntity> detalleCompleto(Long id) {
        Optional<OrdenAtencionEntity> op = repository.findById(id);
        op.ifPresent(o -> {
            o.getDetalles().size();
        });
        return op;
    }
}
