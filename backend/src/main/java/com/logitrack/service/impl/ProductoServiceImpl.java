package com.logitrack.service.impl;

import com.logitrack.dto.request.ProductoRequestDTO;
import com.logitrack.dto.response.ProductoResponseDTO;
import com.logitrack.mapper.ProductoMapper;
import com.logitrack.model.Auditoria;
import com.logitrack.model.Producto;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.service.AuditoriaService;
import com.logitrack.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final AuditoriaService auditoriaService;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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

        String stockAnterior = "Stock: " + producto.getStock();

        if ("ENTRADA".equalsIgnoreCase(tipoMovimiento)) {
            producto.setStock(producto.getStock() + cantidad);
        } else if ("SALIDA".equalsIgnoreCase(tipoMovimiento)) {
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(producto.getStock() - cantidad);
        }

        Producto guardado = productoRepository.save(producto);
        this.registrarCambio("UPDATE_STOCK", stockAnterior, "Stock: " + guardado.getStock());
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = productoMapper.toEntity(dto);
        Producto guardado = productoRepository.save(producto);

        this.registrarCambio("INSERT", "N/A", "Nombre: " + guardado.getNombre());

        return productoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        String datosLog = "Producto: " + producto.getNombre();

        productoRepository.deleteById(id);
        this.registrarCambio("DELETE", datosLog, "ELIMINADO");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerStockBajo(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }

    // MÉTODO PRIVADO: NO LLEVA @Override
    private void registrarCambio(String op, String anterior, String nuevo) {
        String usuario = "SISTEMA";
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Auditoria aud = new Auditoria();
        aud.setOperacion(op);
        aud.setEntidad("Producto");
        aud.setFecha(LocalDateTime.now());
        aud.setUsuario(usuario);
        aud.setValorAnterior(anterior);
        aud.setValorNuevo(nuevo);

        auditoriaService.guardar(aud);
    }
}   