package org.onions.laboratorio.msvc.muestras.infrastructure.adapter;

import feign.FeignException;
import org.onions.laboratorio.msvc.muestras.application.port.OrdenAtencionPort;
import org.onions.laboratorio.msvc.muestras.infrastructure.client.OrdenClientRest;
import org.springframework.stereotype.Component;

@Component
public class OrdenRestAdapter implements OrdenAtencionPort {

    private final OrdenClientRest client;

    public OrdenRestAdapter(OrdenClientRest client) { this.client = client; }

    @Override
    public boolean existe(Long idOrdenAtencion) {
        try {
            client.detalle(idOrdenAtencion);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}