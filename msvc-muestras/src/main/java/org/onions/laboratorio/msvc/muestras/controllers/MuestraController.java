package org.onions.laboratorio.msvc.muestras.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.muestras.models.entity.Muestra;
import org.onions.laboratorio.msvc.muestras.services.MuestraService;
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
@RequestMapping("/api/muestras")
public class MuestraController {

    @Autowired
    private MuestraService service;

    @GetMapping
    public List<Muestra> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Muestra> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detalleOrden/{idDetalle}")
    public List<Muestra> porDetalleOrden(@PathVariable Long idDetalle) {
        return service.porDetalleOrden(idDetalle);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Muestra muestra, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMuestra(muestra));
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el detalle de orden referenciado: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/recibir")
    public ResponseEntity<?> recibir(@PathVariable Long id) {
        Optional<Muestra> op = service.recibirMuestra(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/procesar")
    public ResponseEntity<?> procesar(@PathVariable Long id) {
        Optional<Muestra> op = service.marcarProcesada(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Muestra muestra,
                                    @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        Optional<Muestra> op = service.actualizar(id, muestra);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Muestra> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
