package com.logitrack.controller;

import com.logitrack.model.DetalleMovimiento;
import com.logitrack.repository.DetalleMovimientoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles") // Esta será tu URL en Postman
public class DetalleMovimientoController {

    private final DetalleMovimientoRepository repository;

    public DetalleMovimientoController(DetalleMovimientoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DetalleMovimiento> listar() {
        return repository.findAll();
    }

    @PostMapping
    public DetalleMovimiento guardar(@RequestBody DetalleMovimiento detalle) {
        return repository.save(detalle);
    }
}