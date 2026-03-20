package com.logitrack.controller;

import com.logitrack.dto.request.MovimientoRequestDTO;
import com.logitrack.dto.response.MovimientoResponseDTO;
import com.logitrack.model.Movimiento;
import com.logitrack.service.MovimientoService;
import com.logitrack.service.impl.MovimientoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    @Operation(summary = "Listar todos los movimientos registrados")
    public ResponseEntity<List<MovimientoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(movimientoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un movimiento por su ID")
    public ResponseEntity<MovimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo movimiento (Entrada/Salida)")
    public ResponseEntity<MovimientoResponseDTO> crear(@Valid @RequestBody MovimientoRequestDTO dto) {
        return ResponseEntity.ok(movimientoService.crear(dto));
    }

    @GetMapping("/buscar-por-fechas")
    @Operation(summary = "Buscar movimientos por rango de fechas")
    public ResponseEntity<List<MovimientoResponseDTO>> getPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(movimientoService.buscarPorFechas(inicio, fin));
    }

    @GetMapping("/resumen")
    @Operation(summary = "Obtener reporte general de stock y movimientos")
    public ResponseEntity<Map<String, Object>> obtenerResumenGeneral() {
        return ResponseEntity.ok(movimientoService.generarReporteGeneral());
    }

    @GetMapping
    @Operation(summary = "Listar los ultimos 10 movimientos")
    public ResponseEntity<List<MovimientoResponseDTO>> listarRecientes() {
        return ResponseEntity.ok(movimientoService.listarRecientes());
    }

}