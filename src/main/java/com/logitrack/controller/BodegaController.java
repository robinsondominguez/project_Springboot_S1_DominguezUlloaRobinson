package com.logitrack.controller;

import com.logitrack.model.Bodega;
import com.logitrack.service.BodegaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bodegas")
@Tag(name = "Bodegas", description = "API para la gestión de bodegas")
public class BodegaController {

    private final BodegaService bodegaService;

    public BodegaController(BodegaService bodegaService) {
        this.bodegaService = bodegaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las bodegas")
    public List<Bodega> listarBodegas() {
        return bodegaService.listarBodegas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener bodega por ID")
    public Bodega obtenerPorId(@PathVariable Long id) {
        return bodegaService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva bodega")
    public Bodega guardar(@RequestBody Bodega bodega) {
        return bodegaService.guardar(bodega);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una bodega existente")
    public Bodega actualizar(@PathVariable Long id, @RequestBody Bodega bodega) {
        return bodegaService.actualizar(id, bodega);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una bodega")
    public void eliminar(@PathVariable Long id) {
        bodegaService.eliminar(id);
    }
}