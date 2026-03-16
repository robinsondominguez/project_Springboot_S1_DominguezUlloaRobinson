package com.logitrack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResumenResponseDTO {
    private Map<String, Integer> stockPorBodega;
    private Map<String, Integer> productosMasMovidos;
    private long totalProductos;
    private long totalMovimientos;
    private String estadoSistema;
}