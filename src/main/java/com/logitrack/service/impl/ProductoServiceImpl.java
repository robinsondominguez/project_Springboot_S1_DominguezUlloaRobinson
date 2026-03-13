package com.logitrack.service.impl;

import com.logitrack.exception.ResourceNotFoundException;
import com.logitrack.model.Producto;
import com.logitrack.model.Auditoria;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.service.ProductoService;
import com.logitrack.service.AuditoriaService;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final AuditoriaService auditoriaService;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.auditoriaService = auditoriaService;
    }

    private String obtenerUsuarioActual() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto guardar(Producto producto) {

        Producto nuevo = productoRepository.save(producto);

        Auditoria auditoria = new Auditoria();
        auditoria.setOperacion("INSERT");
        auditoria.setEntidad("Producto");
        auditoria.setUsuario(obtenerUsuarioActual());
        auditoria.setFecha(LocalDateTime.now());
        auditoria.setValorNuevo(nuevo.toString());

        auditoriaService.guardar(auditoria);

        return nuevo;
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {

        Producto productoExistente = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));;

        if (productoExistente != null) {

            String valorAnterior = productoExistente.toString();

            productoExistente.setNombre(producto.getNombre());
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setStock(producto.getStock());
            productoExistente.setPrecio(producto.getPrecio());

            Producto actualizado = productoRepository.save(productoExistente);

            Auditoria auditoria = new Auditoria();
            auditoria.setOperacion("UPDATE");
            auditoria.setEntidad("Producto");
            auditoria.setUsuario(obtenerUsuarioActual());
            auditoria.setFecha(LocalDateTime.now());
            auditoria.setValorAnterior(valorAnterior);
            auditoria.setValorNuevo(actualizado.toString());

            auditoriaService.guardar(auditoria);

            return actualizado;
        }

        return null;
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));;

        productoRepository.deleteById(id);

        if (producto != null) {

            Auditoria auditoria = new Auditoria();
            auditoria.setOperacion("DELETE");
            auditoria.setEntidad("Producto");
            auditoria.setUsuario(obtenerUsuarioActual());
            auditoria.setFecha(LocalDateTime.now());
            auditoria.setValorAnterior(producto.toString());

            auditoriaService.guardar(auditoria);
        }
    }

    @Override
    public List<Producto> productosConStockBajo() {
        return productoRepository.findByStockLessThan(10);
    }
}