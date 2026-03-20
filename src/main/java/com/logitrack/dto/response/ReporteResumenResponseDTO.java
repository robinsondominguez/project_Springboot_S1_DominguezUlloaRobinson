package com.logitrack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResumenResponseDTO {
    private Long totalProductos;
    private Long totalMovimientos;
    private String estadoSistema;
    private Map<String, Integer> stockPorBodega;
    private Map<String, Integer> productosMasMovidos;

    public void setTotalTipoMovimiento(long count) {
    }
}