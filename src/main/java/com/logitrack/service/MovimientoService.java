package com.logitrack.service;

import com.logitrack.dto.request.MovimientoRequestDTO;
import com.logitrack.dto.response.MovimientoResponseDTO;
import com.logitrack.dto.response.ReporteResumen;
import com.logitrack.model.Movimiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovimientoService {

    MovimientoResponseDTO crear(MovimientoRequestDTO dto);

    List<MovimientoResponseDTO> listarTodos();

    MovimientoResponseDTO obtenerPorId(Long id);

    List<MovimientoResponseDTO> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin);

    List<Movimiento> listarPorRango(LocalDateTime inicio, LocalDateTime fin);

    List<ReporteResumen> productosMasMovidos();

    Map<String, Object> generarReporteGeneral();
}