package org.onions.laboratorio.msvc.resultados.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.resultados.models.entity.Resultado;
import org.onions.laboratorio.msvc.resultados.services.ResultadoService;
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
@RequestMapping("/api/resultados")
public class ResultadoController {

    @Autowired
    private ResultadoService service;

    @GetMapping
    public List<Resultado> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Resultado> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/muestra/{idMuestra}")
    public List<Resultado> porMuestra(@PathVariable Long idMuestra) {
        return service.porMuestra(idMuestra);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Resultado resultado, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(resultado));
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe la muestra referenciada: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}/validar")
    public ResponseEntity<?> validar(@PathVariable Long id,
                                     @RequestParam(required = false, defaultValue = "Bioquimico") String bioquimico) {
        Optional<Resultado> op = service.validar(id, bioquimico);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/observar")
    public ResponseEntity<?> observar(@PathVariable Long id) {
        Optional<Resultado> op = service.observar(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Resultado resultado,
                                    @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        Optional<Resultado> op = service.actualizar(id, resultado);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Resultado> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/resultadosPorIds")
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
