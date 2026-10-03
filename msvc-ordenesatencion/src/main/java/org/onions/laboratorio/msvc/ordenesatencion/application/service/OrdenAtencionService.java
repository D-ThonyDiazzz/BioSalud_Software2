package org.onions.laboratorio.msvc.ordenesatencion.application.service;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;

import java.util.List;
import java.util.Optional;

public interface OrdenAtencionService {

    List<OrdenAtencionEntity> listar();

    Optional<OrdenAtencionEntity> porId(Long id);

    List<OrdenAtencionEntity> porPaciente(Long idPaciente);

    //Crea una nueva orden asignando el numero de turno consecutivo del dia
    OrdenAtencionEntity crearOrden(OrdenAtencionEntity orden);

    //Agrega un analisis existente al detalle de la orden (respetando precios y descuentos)
    Optional<OrdenAtencionEntity> agregarAnalisisAOrden(Long idOrden, Long idAnalisis);

    //Agrega un perfil existente al detalle de la orden
    Optional<OrdenAtencionEntity> agregarPerfilAOrden(Long idOrden, Long idPerfil);

    //Retira un detalle de una orden
    Optional<OrdenAtencionEntity> quitarDetalle(Long idOrden, Long idDetalle);


    Optional<OrdenAtencionEntity> cambiarEstado(Long idOrden, String estado);

    void eliminar(Long id);

    Optional<OrdenAtencionEntity> detalleCompleto(Long id);
}
