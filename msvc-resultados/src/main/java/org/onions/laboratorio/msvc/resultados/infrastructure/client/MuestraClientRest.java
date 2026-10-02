package org.onions.laboratorio.msvc.resultados.infrastructure.client;

import org.onions.laboratorio.msvc.resultados.infrastructure.client.model.MuestraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-muestras", url = "localhost:8009/api/muestras")
public interface MuestraClientRest {
    @GetMapping("/{id}")
    MuestraResponse detalle(@PathVariable Long id);
}
