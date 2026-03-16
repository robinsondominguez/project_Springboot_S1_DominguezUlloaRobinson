package com.logitrack.controller;

import com.logitrack.dto.request.DetalleRequestDTO;
import com.logitrack.dto.response.DetalleResponseDTO;
import com.logitrack.service.DetalleMovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
public class DetalleMovimientoController {

    private final DetalleMovimientoService detalleService;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de movimientos")
    public ResponseEntity<List<DetalleResponseDTO>> listarTodos() {
        return ResponseEntity.ok(detalleService.listarTodos());
    }

    @PostMapping
    @Operation(summary = "Registrar un detalle y actualizar stock del producto")
    public ResponseEntity<DetalleResponseDTO> crear(@RequestBody DetalleRequestDTO dto) {
        return ResponseEntity.ok(detalleService.crear(dto));
    }
}