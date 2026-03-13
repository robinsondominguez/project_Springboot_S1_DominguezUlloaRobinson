package com.logitrack.repository;

import com.logitrack.model.Movimiento;
import com.logitrack.model.ReporteResumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("""
      SELECT new com.logitrack.model.ReporteResumen(
      p.nombre,
      COUNT(m.id)
      )
      FROM Movimiento m
      JOIN m.producto p
      GROUP BY p.nombre
      ORDER BY COUNT(m.id) DESC
      """)
    List<ReporteResumen> productosMasMovidos();

}