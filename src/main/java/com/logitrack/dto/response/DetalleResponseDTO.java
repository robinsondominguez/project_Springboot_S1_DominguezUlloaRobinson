package com.logitrack.dto.response;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DetalleResponseDTO {
    private Long id;
    private Integer cantidad;
    private String nombreProducto;
    private String tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String nombreBodegaOrigen;
    private String nombreBodegaDestino;
}


