package com.logitrack.controller;

import com.logitrack.model.Movimiento;
import com.logitrack.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos", description = "API para la gestión de movimientos de inventario")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public List<Movimiento> listarMovimientos() {
        return movimientoService.listarMovimientos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID")
    public Movimiento obtenerPorId(@PathVariable Long id) {
        return movimientoService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo movimiento")
    public Movimiento guardar(@RequestBody Movimiento movimiento) {
        return movimientoService.guardar(movimiento);
    }

    @GetMapping("/fechas")
    @Operation(summary = "Buscar movimientos por rango de fechas")
    public List<Movimiento> buscarPorFechas(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {

        return movimientoService.buscarPorFechas(inicio, fin);
    }
}