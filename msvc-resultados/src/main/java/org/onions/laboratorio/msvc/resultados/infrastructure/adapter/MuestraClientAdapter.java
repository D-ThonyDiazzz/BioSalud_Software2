package org.onions.laboratorio.msvc.resultados.infrastructure.adapter;

import org.onions.laboratorio.msvc.resultados.application.port.MuestraClientPort;
import org.onions.laboratorio.msvc.resultados.infrastructure.client.MuestraClientRest;
import org.onions.laboratorio.msvc.resultados.infrastructure.client.model.MuestraResponse;
import org.springframework.stereotype.Component;

@Component
public class MuestraClientAdapter implements MuestraClientPort {

    private final MuestraClientRest client;

    public MuestraClientAdapter(MuestraClientRest client) {
        this.client = client;
    }

    @Override
    public Long obtenerIdMuestra(Long idMuestra) {
        MuestraResponse muestra = client.detalle(idMuestra);
        if (muestra == null || muestra.getId() == null) {
            throw new IllegalArgumentException("No existe la muestra referenciada");
        }
        return muestra.getId();
    }
}
