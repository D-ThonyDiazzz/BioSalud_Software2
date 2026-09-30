package org.onions.laboratorio.msvc.resultados.client;

import org.onions.laboratorio.msvc.resultados.models.Muestra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-muestras", url = "localhost:8009/api/muestras")
public interface MuestraClientRest {

    @GetMapping("/{id}")
    Muestra detalle(@PathVariable Long id);
}
