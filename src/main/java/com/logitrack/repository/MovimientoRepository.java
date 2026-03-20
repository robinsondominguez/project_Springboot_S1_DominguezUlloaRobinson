package com.logitrack.repository;

import com.logitrack.model.Movimiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Movimiento> findByUsuarioId(Long usuarioId);

    List<Movimiento> findTop10ByMovimientoIdOrderByFechaDesc();
}
