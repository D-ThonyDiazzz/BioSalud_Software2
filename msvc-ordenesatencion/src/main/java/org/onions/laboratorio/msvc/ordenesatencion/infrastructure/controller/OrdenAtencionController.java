package org.onions.laboratorio.msvc.ordenesatencion.infrastructure.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.ordenesatencion.infrastructure.entity.OrdenAtencionEntity;
import org.onions.laboratorio.msvc.ordenesatencion.application.service.OrdenAtencionService;
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
@RequestMapping("/api/ordenes")
public class OrdenAtencionController {

    @Autowired
    private OrdenAtencionService service;

    @GetMapping
    public List<OrdenAtencionEntity> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<OrdenAtencionEntity> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detalleCompleto/{id}")
    public ResponseEntity<?> detalleCompleto(@PathVariable Long id) {
        Optional<OrdenAtencionEntity> op = service.detalleCompleto(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/paciente/{idPaciente}")
    public List<OrdenAtencionEntity> porPaciente(@PathVariable Long idPaciente) {
        return service.porPaciente(idPaciente);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody OrdenAtencionEntity orden, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crearOrden(orden));
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el paciente referenciado: " + e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", e.getMessage()));
        }
    }

    @PutMapping("/agregarAnalisis/{idOrden}/{idAnalisis}")
    public ResponseEntity<?> agregarAnalisis(@PathVariable Long idOrden, @PathVariable Long idAnalisis) {
        try {
            Optional<OrdenAtencionEntity> op = service.agregarAnalisisAOrden(idOrden, idAnalisis);
            if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
            return ResponseEntity.notFound().build();
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el analisis: " + e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", e.getMessage()));
        }
    }

    @PutMapping("/agregarPerfil/{idOrden}/{idPerfil}")
    public ResponseEntity<?> agregarPerfil(@PathVariable Long idOrden, @PathVariable Long idPerfil) {
        try {
            Optional<OrdenAtencionEntity> op = service.agregarPerfilAOrden(idOrden, idPerfil);
            if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
            return ResponseEntity.notFound().build();
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el perfil: " + e.getMessage()));
        }
    }

    @DeleteMapping("/quitarDetalle/{idOrden}/{idDetalle}")
    public ResponseEntity<?> quitarDetalle(@PathVariable Long idOrden, @PathVariable Long idDetalle) {
        Optional<OrdenAtencionEntity> op = service.quitarDetalle(idOrden, idDetalle);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{idOrden}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idOrden, @RequestParam String estado) {
        Optional<OrdenAtencionEntity> op = service.cambiarEstado(idOrden, estado);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<OrdenAtencionEntity> op = service.porId(id);
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
