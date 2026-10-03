package org.onions.laboratorio.msvc.pacientes.application.port;

import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;

public interface ResponsableClientPort {
    ResponsableData detalle(Long idResponsable);
    ResponsableData crear(ResponsableData responsable);
}
