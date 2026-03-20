package com.logitrack.service.impl;

import com.logitrack.dto.response.ReporteResumenResponseDTO;
import com.logitrack.model.Bodega;
import com.logitrack.model.DetalleMovimiento;
import com.logitrack.repository.DetalleMovimientoRepository;
import com.logitrack.repository.ProductoRepository;
import com.logitrack.repository.MovimientoRepository;
import com.logitrack.service.BodegaService;
import com.logitrack.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final BodegaService bodegaService;
    private final ProductoRepository productoRepository;
    private final MovimientoRepository movimientoRepository;
    private final DetalleMovimientoRepository detalleRepository;

    @Override
    public ReporteResumenResponseDTO obtenerResumenGeneral() {
        ReporteResumenResponseDTO reporte = new ReporteResumenResponseDTO();

        Map<String, Integer> stockBodegas = new HashMap<>();
        List<Bodega> todasLasBodegas = bodegaService.listarBodegas();

        for (Bodega b : todasLasBodegas) {
            Integer ocupado = bodegaService.EspacioBodega(b.getId());
            stockBodegas.put(b.getNombre(), ocupado);
        }
        reporte.setStockPorBodega(stockBodegas);

        Map<String, Integer> conteoProductos = new HashMap<>();
        List<DetalleMovimiento> todosLosDetalles = detalleRepository.findAll();

        for (DetalleMovimiento d : todosLosDetalles) {
            String nombreProd = d.getProducto().getNombre();
            conteoProductos.put(nombreProd, conteoProductos.getOrDefault(nombreProd, 0) + 1);
        }
        reporte.setProductosMasMovidos(conteoProductos);

        reporte.setTotalProductos(productoRepository.count());
        reporte.setTotalMovimientos(movimientoRepository.count());
        reporte.setEstadoSistema("SISTEMA LOGITRACK OPERATIVO");

        return reporte;
    }

    @Override
    public ReporteResumenResponseDTO obtenerResumenMovimiento() {
        
        ReporteResumenResponseDTO reporteMovimiento = new ReporteResumenResponseDTO();

        reporteMovimiento.setTotalMovimientos(movimientoRepository.count());
        reporteMovimiento.setTotalTipoMovimiento(movimientoRepository.count());
        reporteMovimiento.setEstadoSistema("SISTEMA LOGITRACK OPERATIVO");

        return reporteMovimiento;
    }
}