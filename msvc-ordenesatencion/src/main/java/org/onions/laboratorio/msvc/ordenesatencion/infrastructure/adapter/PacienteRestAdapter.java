package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.adapter;

import feign.FeignException;
import org.onions.laboratorio.msvc.ordenesatencion.application.port.PacientePort;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.PacienteClientRest;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;

@Component
public class PacienteRestAdapter implements PacientePort {

    private final PacienteClientRest client;

    public PacienteRestAdapter(PacienteClientRest client) { this.client = client; }

    @Override
    public boolean existe(Long idPaciente) {
        try {
            client.detalle(idPaciente);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}