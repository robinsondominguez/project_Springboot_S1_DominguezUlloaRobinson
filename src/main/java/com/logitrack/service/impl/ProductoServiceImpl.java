package com.logitrack.service.impl;

import com.logitrack.model.Producto;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
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
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {

        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if (productoExistente != null) {

            productoExistente.setNombre(producto.getNombre());
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setStock(producto.getStock());
            productoExistente.setPrecio(producto.getPrecio());

            return productoRepository.save(productoExistente);
        }

        return null;
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> productosConStockBajo() {
        return productoRepository.findByStockLessThan(10);
    }
}