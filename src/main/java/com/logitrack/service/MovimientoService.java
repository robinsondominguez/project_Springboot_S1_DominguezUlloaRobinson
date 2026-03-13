package com.logitrack.service;

import com.logitrack.model.Movimiento;
import com.logitrack.model.ReporteResumen;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {

    List<Movimiento> listarMovimientos();

    Movimiento obtenerPorId(Long id);

    Movimiento guardar(Movimiento movimiento);

    List<Movimiento> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin);

    List<ReporteResumen> productosMasMovidos();

}