package org.onions.laboratorio.msvc.responsables.infraestructure.controller;

import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.responsables.domain.model.Responsable;
import org.onions.laboratorio.msvc.responsables.application.service.ResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/responsables")
public class ResponsableController {

    @Autowired
    private ResponsableService service;

    @GetMapping
    public List<Responsable> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Responsable> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/documento/{numero}")
    public ResponseEntity<?> porDocumento(@PathVariable String numero) {
        Optional<Responsable> op = service.porDocumento(numero);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Responsable responsable, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(responsable));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Responsable responsable,
                                    @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        Optional<Responsable> op = service.actualizar(id, responsable);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Responsable> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/responsablesPorIds")
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