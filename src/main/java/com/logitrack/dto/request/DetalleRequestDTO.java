package com.logitrack.dto.request;

import lombok.Data;

@Data
public class DetalleRequestDTO {
    private Integer cantidad;
    private Long productoId;
    private Long movimientoId;

    private String nombreProducto;
    private String tipoMovimiento;
    private String bodegaOrigen;
    private String bodegaDestino;
}