package org.onions.laboratorio.msvc.analisis.infrastructure.controller;

import org.onions.laboratorio.msvc.analisis.application.service.AnalisisService;
import org.onions.laboratorio.msvc.analisis.domain.model.Analisis;
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
@RequestMapping("/api/analisis")
public class AnalisisController {

    private final AnalisisService service;

    public AnalisisController(AnalisisService service) {
        this.service = service;
    }

    @GetMapping
    public List<Analisis> listar() { return service.listar(); }

    @GetMapping("/vigentes")
    public List<Analisis> listarVigentes() { return service.listarVigentes(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return service.porId(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Analisis analisis) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(analisis));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Analisis analisis, @PathVariable Long id) {
        try {
            return service.actualizar(id, analisis)
                    .<ResponseEntity<?>>map(actualizado -> ResponseEntity.status(HttpStatus.CREATED).body(actualizado))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/darDeBaja")
    public ResponseEntity<?> darDeBaja(@PathVariable Long id) {
        return service.darDeBaja(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.porId(id).isEmpty()) return ResponseEntity.notFound().build();
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/analisisPorIds")
    public ResponseEntity<List<Analisis>> listarPorIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }
}
