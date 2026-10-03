package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.adapter;

import feign.FeignException;
import org.onions.laboratorio.msvc.ordenesatencion.application.dto.PerfilInfo;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.PerfilPort;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.PerfilClientRest;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Perfil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PerfilRestAdapter implements PerfilPort {

    private final PerfilClientRest client;

    public PerfilRestAdapter(PerfilClientRest client) { this.client = client; }

    @Override
    public Optional<PerfilInfo> porId(Long idPerfil) {
        try {
            Perfil p = client.detalle(idPerfil);
            return Optional.of(new PerfilInfo(p.getId(), p.getNombre()));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}
