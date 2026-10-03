package org.onions.laboratorio.msvc.resultados.infrastructure.controller;

import feign.FeignException;
import org.onions.laboratorio.msvc.resultados.application.service.ResultadoService;
import org.onions.laboratorio.msvc.resultados.domain.model.Resultado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/resultados")
public class ResultadoController {

    private final ResultadoService service;

    public ResultadoController(ResultadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Resultado> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/muestra/{idMuestra}")
    public List<Resultado> porMuestra(@PathVariable Long idMuestra) {
        return service.porMuestra(idMuestra);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Resultado resultado) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(resultado));
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap(
                    "Mensaje", "No existe la muestra referenciada: " + ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/validar")
    public ResponseEntity<?> validar(@PathVariable Long id,
                                     @RequestParam(required = false, defaultValue = "Bioquimico") String bioquimico) {
        try {
            return service.validar(id, bioquimico).<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/observar")
    public ResponseEntity<?> observar(@PathVariable Long id) {
        return service.observar(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Resultado resultado, @PathVariable Long id) {
        try {
            return service.actualizar(id, resultado)
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

    @GetMapping("/resultadosPorIds")
    public ResponseEntity<List<Resultado>> listarPorIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }
}
