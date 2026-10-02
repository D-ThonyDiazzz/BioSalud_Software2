package org.onions.laboratorio.msvc.pacientes.infrastructure.client;

import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-responsables", url = "localhost:8002/api/responsables")
public interface ResponsableClientRest {
    @GetMapping("/{id}")
    ResponsableData detalle(@PathVariable Long id);

    @PostMapping
    ResponsableData crear(@RequestBody ResponsableData responsable);
}
