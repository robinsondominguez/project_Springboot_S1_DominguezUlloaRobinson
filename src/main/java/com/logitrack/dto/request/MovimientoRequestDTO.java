package com.logitrack.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimientoRequestDTO {

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long usuarioId;

    private Long productoId;
    private Integer cantidad;
    private Long bodegaOrigenId;
    private Long bodegaDestinoId;
}