package com.logitrack.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento; // ENTRADA, SALIDA, TRANSFERENCIA
    private String nombreUsuario;  // El username del modelo Usuario
}