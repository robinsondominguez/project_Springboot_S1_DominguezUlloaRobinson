package com.logitrack.controller;

import com.logitrack.model.Auditoria;
import com.logitrack.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@Tag(name = "Auditoría", description = "API para consultar auditorías del sistema")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las auditorías")
    public List<Auditoria> listarAuditorias() {
        return auditoriaService.listarAuditorias();
    }

    @GetMapping("/usuario/{usuario}")
    @Operation(summary = "Buscar auditorías por usuario")
    public List<Auditoria> buscarPorUsuario(@PathVariable String usuario) {
        return auditoriaService.buscarPorUsuario(usuario);
    }

    @GetMapping("/operacion/{operacion}")
    @Operation(summary = "Buscar auditorías por tipo de operación")
    public List<Auditoria> buscarPorOperacion(@PathVariable String operacion) {
        return auditoriaService.buscarPorOperacion(operacion);
    }
}