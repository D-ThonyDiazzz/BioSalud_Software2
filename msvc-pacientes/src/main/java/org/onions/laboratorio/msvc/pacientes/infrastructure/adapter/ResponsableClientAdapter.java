package org.onions.laboratorio.msvc.pacientes.infrastructure.adapter;

import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;
import org.onions.laboratorio.msvc.pacientes.application.port.ResponsableClientPort;
import org.onions.laboratorio.msvc.pacientes.infrastructure.client.ResponsableClientRest;
import org.springframework.stereotype.Component;

@Component
public class ResponsableClientAdapter implements ResponsableClientPort {
    private final ResponsableClientRest client;

    public ResponsableClientAdapter(ResponsableClientRest client) {
        this.client = client;
    }

    @Override
    public ResponsableData detalle(Long idResponsable) {
        return client.detalle(idResponsable);
    }

    @Override
    public ResponsableData crear(ResponsableData responsable) {
        return client.crear(responsable);
    }
}
