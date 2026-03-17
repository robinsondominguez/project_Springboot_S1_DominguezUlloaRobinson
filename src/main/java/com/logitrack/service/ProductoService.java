package com.logitrack.service;

import com.logitrack.dto.request.ProductoRequestDTO;
import com.logitrack.dto.response.ProductoResponseDTO;
import com.logitrack.model.Auditoria;
import com.logitrack.model.Producto;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crear(ProductoRequestDTO dto);
    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO obtenerPorId(Long id);
    void actualizarStock(Long productoId, Integer cantidad, String tipoMovimiento);
    List<Producto> obtenerStockBajo(Integer limite);
    void eliminar(Long id);
}