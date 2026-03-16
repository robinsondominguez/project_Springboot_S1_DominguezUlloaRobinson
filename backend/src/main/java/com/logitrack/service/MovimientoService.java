package com.logitrack.service;

import com.logitrack.dto.request.MovimientoRequestDTO;
import com.logitrack.dto.response.MovimientoResponseDTO;
import com.logitrack.model.Movimiento;
import com.logitrack.model.ReporteResumen;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovimientoService {

    List<MovimientoResponseDTO> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin);

    List<ReporteResumen> productosMasMovidos();

    MovimientoResponseDTO crear(MovimientoRequestDTO dto);

    List<MovimientoResponseDTO> listarTodos();

    MovimientoResponseDTO obtenerPorId(Long id);

    List<Movimiento> listarPorRango(LocalDateTime inicio, LocalDateTime fin);

    Map<String, Object> generarReporteGeneral();
}