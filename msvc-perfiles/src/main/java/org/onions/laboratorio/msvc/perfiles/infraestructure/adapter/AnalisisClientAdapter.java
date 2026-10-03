package org.onions.laboratorio.msvc.perfiles.infraestructure.adapter;

import org.onions.laboratorio.msvc.perfiles.application.port.AnalisisClientPort;
import org.onions.laboratorio.msvc.perfiles.domain.Analisis;
import org.onions.laboratorio.msvc.perfiles.infraestructure.client.AnalisisClientRest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalisisClientAdapter implements AnalisisClientPort {

    private final AnalisisClientRest client;

    public AnalisisClientAdapter(AnalisisClientRest client) {
        this.client = client;
    }

    @Override
    public Analisis porId(Long id) {
        return client.detalle(id);
    }

    @Override
    public List<Analisis> porIds(List<Long> ids) {
        return client.obtenerAnalisisPorIds(ids);
    }
}