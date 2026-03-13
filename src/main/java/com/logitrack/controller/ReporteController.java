package com.logitrack.controller;

import com.logitrack.model.ReporteResumen;
import com.logitrack.service.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "API de reportes del sistema")
public class ReporteController {

    private final MovimientoService movimientoService;

    public ReporteController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/productos-mas-movidos")
    @Operation(summary = "Reporte de productos con más movimientos")
    public List<ReporteResumen> productosMasMovidos() {
        return movimientoService.productosMasMovidos();
    }
}