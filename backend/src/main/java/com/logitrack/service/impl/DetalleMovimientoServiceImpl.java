package com.logitrack.service.impl;

import com.logitrack.dto.request.DetalleRequestDTO;
import com.logitrack.dto.response.DetalleResponseDTO;
import com.logitrack.mapper.DetalleMovimientoMapper;
import com.logitrack.model.*;
import com.logitrack.repository.DetalleMovimientoRepository;
import com.logitrack.repository.MovimientoRepository;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.service.BodegaService;
import com.logitrack.service.DetalleMovimientoService;
import com.logitrack.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleMovimientoServiceImpl implements DetalleMovimientoService {

    private final DetalleMovimientoRepository repository;
    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;
    private final ProductoService productoService;
    private final DetalleMovimientoMapper mapper;
    private final BodegaService bodegaService;

    @Override
    @Transactional
    public DetalleResponseDTO crear(DetalleRequestDTO dto) {

        Movimiento mov = movimientoRepository.findById(dto.getMovimientoId()).orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        Producto producto = productoRepository.findById(dto.getProductoId()).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (mov.getTipoMovimiento() == TipoMovimiento.ENTRADA || mov.getTipoMovimiento() == TipoMovimiento.TRANSFERENCIA) {

            Bodega destino = mov.getBodegaDestino();

            if (destino != null) {
                Integer ocupacionActual = bodegaService.EspacioBodega(destino.getId());

                if (ocupacionActual + dto.getCantidad() > destino.getCapacidad()) {
                    throw new RuntimeException("¡Error! La bodega " + destino.getNombre() +
                            " no tiene espacio suficiente. Espacio disponible: " +
                            (destino.getCapacidad() - ocupacionActual));
                }
            }
        }

        DetalleMovimiento detalle = new DetalleMovimiento();
        detalle.setCantidad(dto.getCantidad());
        detalle.setMovimiento(mov);
        detalle.setProducto(producto);

        DetalleMovimiento guardado = repository.save(detalle);

        productoService.actualizarStock(dto.getProductoId(), dto.getCantidad(), mov.getTipoMovimiento().toString());

        return mapper.toResponseDTO(guardado);
    }

    @Override
    public List<DetalleResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}