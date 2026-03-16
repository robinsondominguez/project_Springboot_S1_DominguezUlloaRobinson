package com.logitrack.mapper;

import com.logitrack.dto.request.ProductoRequestDTO;
import com.logitrack.dto.response.ProductoResponseDTO;
import com.logitrack.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponseDTO toDTO(Producto producto) {
        if (producto == null) return null;

        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());

        return dto;
    }

    public Producto toEntity(ProductoRequestDTO dto) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());

        return producto;
    }
}