package org.onions.laboratorio.msvc.analisis.controllers;

import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.analisis.models.entity.Analisis;
import org.onions.laboratorio.msvc.analisis.services.AnalisisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/analisis")
public class AnalisisController {

    @Autowired
    private AnalisisService service;

    @GetMapping
    public List<Analisis> listar() {
        return service.listar();
    }

    @GetMapping("/vigentes")
    public List<Analisis> listarVigentes() {
        return service.listarVigentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Analisis> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Analisis analisis, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(analisis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Analisis analisis,
                                    @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        Optional<Analisis> op = service.actualizar(id, analisis);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/darDeBaja")
    public ResponseEntity<?> darDeBaja(@PathVariable Long id) {
        Optional<Analisis> op = service.darDeBaja(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Analisis> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/analisisPorIds")
    public ResponseEntity<?> listarPorIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
