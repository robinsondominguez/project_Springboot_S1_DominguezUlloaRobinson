package com.logitrack.controller;

import com.logitrack.dto.request.ProductoRequestDTO;
import com.logitrack.dto.response.ProductoResponseDTO;
import com.logitrack.model.Producto;
import com.logitrack.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo producto")
    public ResponseEntity<ProductoResponseDTO> guardar(@RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.crear(dto));
    }

    @GetMapping("/stock-bajo")
    public List<Producto> obtenerStockBajo() {
        return productoService.obtenerStockBajo(10);
    }



}