package com.logitrack.repository;

import com.logitrack.model.DetalleMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {
}