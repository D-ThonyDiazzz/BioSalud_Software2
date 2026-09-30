package org.onions.laboratorio.msvc.ordenesatencion.client;

import org.onions.laboratorio.msvc.ordenesatencion.models.Analisis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-analisis", url = "localhost:8006/api/analisis")
public interface AnalisisClientRest {

    @GetMapping("/{id}")
    Analisis detalle(@PathVariable Long id);
}
