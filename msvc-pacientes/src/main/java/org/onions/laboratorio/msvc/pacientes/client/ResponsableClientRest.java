package org.onions.laboratorio.msvc.pacientes.client;

import org.onions.laboratorio.msvc.pacientes.models.Responsable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-responsables", url = "localhost:8002/api/responsables")
public interface ResponsableClientRest {

    @GetMapping("/{id}")
    Responsable detalle(@PathVariable Long id);

    @PostMapping
    Responsable crear(@RequestBody Responsable responsable);
}
