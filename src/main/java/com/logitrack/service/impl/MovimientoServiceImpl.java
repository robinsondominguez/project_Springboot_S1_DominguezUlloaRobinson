package com.logitrack.service.impl;

import com.logitrack.dto.request.MovimientoRequestDTO;
import com.logitrack.dto.response.MovimientoResponseDTO;
import com.logitrack.dto.response.ReporteResumen;
import com.logitrack.mapper.MovimientoMapper;
import com.logitrack.model.Movimiento;
import com.logitrack.model.TipoMovimiento;
import com.logitrack.repository.BodegaRepository;
import com.logitrack.repository.MovimientoRepository;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.repository.UsuarioRepository;
import com.logitrack.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final MovimientoMapper mapper;

    @Override
    @Transactional
    public MovimientoResponseDTO crear(MovimientoRequestDTO dto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(TipoMovimiento.valueOf(dto.getTipoMovimiento().toUpperCase()));
        movimiento.setUsuario(usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        if (dto.getProductoId() != null) {
            movimiento.setProducto(productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado")));
        }

        movimiento.setCantidad(dto.getCantidad());

        if (dto.getBodegaOrigenId() != null) {
            movimiento.setBodegaOrigen(bodegaRepository.findById(dto.getBodegaOrigenId())
                    .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada")));
        }
        if (dto.getBodegaDestinoId() != null) {
            movimiento.setBodegaDestino(bodegaRepository.findById(dto.getBodegaDestinoId())
                    .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada")));
        }

        Movimiento guardado = movimientoRepository.save(movimiento);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<MovimientoResponseDTO> listarTodos() {
        return movimientoRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoResponseDTO obtenerPorId(Long id) {
        return movimientoRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    public List<MovimientoResponseDTO> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepository.findByFechaBetween(inicio, fin).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> listarPorRango(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepository.findByFechaBetween(inicio, fin);
    }

    @Override
    public List<ReporteResumen> productosMasMovidos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        Map<String, Long> conteo = new HashMap<>();

        for (Movimiento mov : movimientos) {
            if (mov.getProducto() != null) {
                String nombre = mov.getProducto().getNombre();
                Long valor = (mov.getCantidad() != null) ? mov.getCantidad().longValue() : 0L;
                conteo.put(nombre, conteo.getOrDefault(nombre, 0L) + valor);
            }
        }

        List<ReporteResumen> listaReporte = new ArrayList<>();
        for (Map.Entry<String, Long> entrada : conteo.entrySet()) {
            listaReporte.add(new ReporteResumen(entrada.getKey(), entrada.getValue()));
        }

        listaReporte.sort((r1, r2) -> r2.getTotalMovimientos().compareTo(r1.getTotalMovimientos()));
        return listaReporte;
    }

    @Override
    public Map<String, Object> generarReporteGeneral() {
        Map<String, Object> reporteFinal = new HashMap<>();

        reporteFinal.put("productosTop", productosMasMovidos());

        Map<String, Integer> stockBodegas = new HashMap<>();
        bodegaRepository.findAll().forEach(b -> {
            stockBodegas.put(b.getNombre(), b.getCapacidad() != null ? b.getCapacidad() : 0);
        });

        reporteFinal.put("resumenBodegas", stockBodegas);
        reporteFinal.put("totalOperaciones", movimientoRepository.count());
        reporteFinal.put("fechaReporte", LocalDateTime.now());

        return reporteFinal;
    }
}