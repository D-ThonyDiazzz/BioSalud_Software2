package org.onions.laboratorio.msvc.perfiles.infraestructure.client;

import org.onions.laboratorio.msvc.perfiles.domain.Analisis;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-analisis", url = "localhost:8006/api/analisis")
public interface AnalisisClientRest {

    @GetMapping("/{id}")
    Analisis detalle(@PathVariable Long id);

    @GetMapping("/analisisPorIds")
    List<Analisis> obtenerAnalisisPorIds(@RequestParam List<Long> ids);
}