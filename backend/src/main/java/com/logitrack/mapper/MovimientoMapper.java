package com.logitrack.mapper;

import com.logitrack.dto.response.MovimientoResponseDTO;
import com.logitrack.model.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public MovimientoResponseDTO toDTO(Movimiento movimiento) {
        if (movimiento == null) return null;

        MovimientoResponseDTO dto = new MovimientoResponseDTO();
        dto.setId(movimiento.getId());
        dto.setFecha(movimiento.getFecha());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento().toString());

        // Solo pasamos los nombres o IDs para no cargar toda la entidad
        if (movimiento.getUsuario() != null) {
            dto.setNombreUsuario(movimiento.getUsuario().getUsername());
        }

        return dto;
    }
}