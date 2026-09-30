package org.onions.laboratorio.msvc.pacientes.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.onions.laboratorio.msvc.pacientes.models.Responsable;
import org.onions.laboratorio.msvc.pacientes.models.entity.Paciente;
import org.onions.laboratorio.msvc.pacientes.models.entity.PacienteResponsable;
import org.onions.laboratorio.msvc.pacientes.services.PacienteService;
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
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Paciente> op = service.porId(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/documento/{numero}")
    public ResponseEntity<?> porDocumento(@PathVariable String numero) {
        Optional<Paciente> op = service.porDocumento(numero);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(paciente));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Paciente paciente,
                                    @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) return validar(result);
        Optional<Paciente> op = service.actualizar(id, paciente);
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Paciente> op = service.porId(id);
        if (op.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //ASIGNAR RESPONSABLE EXISTENTE
    @PutMapping("/asignarResponsable/{idPaciente}")
    public ResponseEntity<?> asignarResponsable(@RequestBody PacienteResponsable vinculo,
                                                @PathVariable Long idPaciente) {
        Optional<PacienteResponsable> op;
        try {
            op = service.asignarResponsable(idPaciente, vinculo);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No existe el responsable con ese id o "
                            + "error en la comunicacion: " + e.getMessage()));
        }
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    //CREAR RESPONSABLE NUEVO Y ASIGNARLO
    @PostMapping("/crearResponsable/{idPaciente}")
    public ResponseEntity<?> crearResponsable(@RequestBody Responsable responsable,
                                              @PathVariable Long idPaciente,
                                              @RequestParam(required = false, defaultValue = "TUTOR") String relacion,
                                              @RequestParam(required = false, defaultValue = "SIN FIRMA") String firma) {
        Optional<PacienteResponsable> op;
        try {
            op = service.crearYAsignarResponsable(idPaciente, responsable, relacion, firma);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("Mensaje", "No se pudo crear el responsable o "
                            + "error en la comunicacion: " + e.getMessage()));
        }
        if (op.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(op.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/responsables")
    public ResponseEntity<List<PacienteResponsable>> listarResponsables(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarResponsables(id));
    }

    @GetMapping("/detalleConResponsables/{id}")
    public ResponseEntity<?> detalleConResponsables(@PathVariable Long id) {
        Optional<Paciente> op = service.detalleConResponsables(id);
        if (op.isPresent()) return ResponseEntity.ok(op.get());
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
