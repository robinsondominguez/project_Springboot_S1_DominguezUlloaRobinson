package com.logitrack.service.impl;

import com.logitrack.dto.request.ProductoRequestDTO;
import com.logitrack.dto.response.ProductoResponseDTO;
import com.logitrack.mapper.ProductoMapper;
import com.logitrack.model.Producto;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Esto elimina la necesidad de tu constructor manual
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public void actualizarStock(Long productoId, Integer cantidad, String tipoMovimiento) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if ("ENTRADA".equalsIgnoreCase(tipoMovimiento)) {
            producto.setStock(producto.getStock() + cantidad);
        } else if ("SALIDA".equalsIgnoreCase(tipoMovimiento)) {
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - cantidad);
        }

        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setStock(dto.getStock() != null ? dto.getStock() : 0);
        producto.setPrecio(dto.getPrecio());

        return productoMapper.toDTO(productoRepository.save(producto));
    }

    @Override
    public List<Producto> obtenerStockBajo(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }
}