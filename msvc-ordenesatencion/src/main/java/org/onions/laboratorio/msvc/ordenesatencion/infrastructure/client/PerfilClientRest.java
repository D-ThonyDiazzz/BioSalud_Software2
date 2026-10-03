package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Perfil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-perfiles", url = "localhost:8007/api/perfiles")
public interface PerfilClientRest {

    @GetMapping("/{id}")
    Perfil detalle(@PathVariable Long id);
}
