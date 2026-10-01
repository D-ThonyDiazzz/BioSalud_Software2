package org.onions.laboratorio.msvc.muestras.infrastructure.client;

import org.onions.laboratorio.msvc.muestras.infrastructure.client.model.OrdenAtencion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-ordenesatencion", url = "localhost:8008/api/ordenes")
public interface OrdenClientRest {

    @GetMapping("/detalleCompleto/{id}")
    OrdenAtencion detalleCompleto(@PathVariable Long id);
}
