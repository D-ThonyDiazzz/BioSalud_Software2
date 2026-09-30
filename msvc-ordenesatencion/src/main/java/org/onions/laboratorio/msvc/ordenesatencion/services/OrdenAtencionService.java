package org.onions.laboratorio.msvc.ordenesatencion.services;

import org.onions.laboratorio.msvc.ordenesatencion.models.entity.ComprobantePago;
import org.onions.laboratorio.msvc.ordenesatencion.models.entity.DetalleOrden;
import org.onions.laboratorio.msvc.ordenesatencion.models.entity.OrdenAtencion;

import java.util.List;
import java.util.Optional;

public interface OrdenAtencionService {

    List<OrdenAtencion> listar();

    Optional<OrdenAtencion> porId(Long id);

    List<OrdenAtencion> porPaciente(Long idPaciente);

    //Crea una nueva orden asignando el numero de turno consecutivo del dia
    OrdenAtencion crearOrden(OrdenAtencion orden);

    //Agrega un analisis existente al detalle de la orden (respetando precios y descuentos)
    Optional<OrdenAtencion> agregarAnalisisAOrden(Long idOrden, Long idAnalisis);

    //Agrega un perfil existente al detalle de la orden
    Optional<OrdenAtencion> agregarPerfilAOrden(Long idOrden, Long idPerfil);

    //Retira un detalle de una orden
    Optional<OrdenAtencion> quitarDetalle(Long idOrden, Long idDetalle);

    //Emite el comprobante de pago asociado
    Optional<OrdenAtencion> emitirComprobante(Long idOrden, ComprobantePago comprobante);

    Optional<OrdenAtencion> cambiarEstado(Long idOrden, String estado);

    void eliminar(Long id);

    Optional<OrdenAtencion> detalleCompleto(Long id);
}
