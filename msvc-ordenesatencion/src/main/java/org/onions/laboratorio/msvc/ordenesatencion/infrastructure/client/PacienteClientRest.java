package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client;

import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.client.model.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-pacientes", url = "localhost:8001/api/pacientes")
public interface PacienteClientRest {

    @GetMapping("/{id}")
    Paciente detalle(@PathVariable Long id);
}
