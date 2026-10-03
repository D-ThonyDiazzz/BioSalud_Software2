package org.onions.laboratorio.msvc.pacientes.infrastructure.controller;

import feign.FeignException;
import org.onions.laboratorio.msvc.pacientes.application.model.ResponsableData;
import org.onions.laboratorio.msvc.pacientes.application.service.PacienteService;
import org.onions.laboratorio.msvc.pacientes.domain.model.Paciente;
import org.onions.laboratorio.msvc.pacientes.domain.model.PacienteResponsable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/documento/{numero}")
    public ResponseEntity<?> porDocumento(@PathVariable String numero) {
        return service.porDocumento(numero).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Paciente paciente) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(paciente));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Paciente paciente, @PathVariable Long id) {
        try {
            return service.actualizar(id, paciente)
                    .<ResponseEntity<?>>map(actualizado -> ResponseEntity.status(HttpStatus.CREATED).body(actualizado))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.porId(id).isEmpty()) return ResponseEntity.notFound().build();
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/asignarResponsable/{idPaciente}")
    public ResponseEntity<?> asignarResponsable(@RequestBody PacienteResponsable vinculo,
                                                @PathVariable Long idPaciente) {
        try {
            return service.asignarResponsable(idPaciente, vinculo)
                    .<ResponseEntity<?>>map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body(guardado))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(
                    "Mensaje", "No existe el responsable o fallo la comunicacion: " + ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PostMapping("/crearResponsable/{idPaciente}")
    public ResponseEntity<?> crearResponsable(@RequestBody ResponsableData responsable,
                                              @PathVariable Long idPaciente,
                                              @RequestParam(required = false, defaultValue = "TUTOR") String relacion,
                                              @RequestParam(required = false, defaultValue = "SIN FIRMA") String firma) {
        try {
            return service.crearYAsignarResponsable(idPaciente, responsable, relacion, firma)
                    .<ResponseEntity<?>>map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body(guardado))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(
                    "Mensaje", "No se pudo crear el responsable o fallo la comunicacion: " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}/responsables")
    public ResponseEntity<List<PacienteResponsable>> listarResponsables(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarResponsables(id));
    }

    @GetMapping("/detalleConResponsables/{id}")
    public ResponseEntity<?> detalleConResponsables(@PathVariable Long id) {
        return service.detalleConResponsables(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
