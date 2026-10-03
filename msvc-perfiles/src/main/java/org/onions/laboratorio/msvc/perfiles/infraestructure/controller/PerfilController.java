package org.onions.laboratorio.msvc.perfiles.infraestructure.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.perfiles.domain.model.Perfil;
import org.onions.laboratorio.msvc.perfiles.application.service.PerfilService;
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
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @GetMapping
    public List<Perfil> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Perfil> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detalleConAnalisis/{id}")
    public ResponseEntity<?> detalleConAnalisis(@PathVariable Long id) {
        Optional<Perfil> op = service.detalleConAnalisis(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Perfil perfil, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(perfil));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Perfil perfil, BindingResult result,
                                    @PathVariable Long id) {
        if (result.hasErrors()) return validar(result);
        Optional<Perfil> op = service.actualizar(id, perfil);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignarAnalisis/{idPerfil}/{idAnalisis}")
    public ResponseEntity<?> asignarAnalisis(@PathVariable Long idPerfil, @PathVariable Long idAnalisis) {
        Optional<Perfil> op;
        try {
            op = service.asignarAnalisis(idPerfil, idAnalisis);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el analisis o error de comunicacion: " + e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", e.getMessage()));
        }
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/removerAnalisis/{idPerfil}/{idAnalisis}")
    public ResponseEntity<?> removerAnalisis(@PathVariable Long idPerfil, @PathVariable Long idAnalisis) {
        Optional<Perfil> op = service.removerAnalisis(idPerfil, idAnalisis);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Perfil> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminarPerfilAnalisis/{idAnalisis}")
    public ResponseEntity<?> eliminarPerfilAnalisisPorIdAnalisis(@PathVariable Long idAnalisis) {
        service.eliminarPerfilAnalisisPorIdAnalisis(idAnalisis);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
