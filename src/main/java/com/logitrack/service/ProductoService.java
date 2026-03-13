package com.logitrack.service;

import com.logitrack.model.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> listarProductos();

    Producto obtenerPorId(Long id);

    Producto guardar(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);

    List<Producto> productosConStockBajo();

}