package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.adapter;

import feign.FeignException;
import org.onions.laboratorio.msvc.ordenesatencion.application.dto.AnalisisInfo;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.AnalisisPort;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.AnalisisClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Analisis;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AnalisisRestAdapter implements AnalisisPort {

    private final AnalisisClientRest client;

    public AnalisisRestAdapter(AnalisisClientRest client) { this.client = client; }

    @Override
    public Optional<AnalisisInfo> porId(Long idAnalisis) {
        try {
            Analisis a = client.detalle(idAnalisis);
            return Optional.of(new AnalisisInfo(a.getId(), a.getNombreAnalisis(), a.getPrecio(), a.estaVigente()));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}
