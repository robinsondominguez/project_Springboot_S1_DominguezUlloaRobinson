package com.logitrack.mapper;

import com.logitrack.dto.response.DetalleResponseDTO;
import com.logitrack.model.DetalleMovimiento;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoMapper {

    public DetalleResponseDTO toResponseDTO(DetalleMovimiento detalle) {
        if (detalle == null) return null;

        DetalleResponseDTO dto = new DetalleResponseDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());

        if (detalle.getProducto() != null) {
            dto.setNombreProducto(detalle.getProducto().getNombre());
        }

        if (detalle.getMovimiento() != null) {
            dto.setTipoMovimiento(detalle.getMovimiento().getTipoMovimiento().toString());
            dto.setFechaMovimiento(detalle.getMovimiento().getFecha());

            if (detalle.getMovimiento().getBodegaOrigen() != null) {
                dto.setNombreBodegaOrigen(detalle.getMovimiento().getBodegaOrigen().getNombre());
            }

            if (detalle.getMovimiento().getBodegaDestino() != null) {
                dto.setNombreBodegaDestino(detalle.getMovimiento().getBodegaDestino().getNombre());
            }

        }

        return dto;
    }
}