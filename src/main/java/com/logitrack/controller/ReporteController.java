package com.logitrack.controller;

import com.logitrack.dto.response.ReporteResumenResponseDTO; // Usa tu DTO
import com.logitrack.service.ReporteService; // Importa el servicio de reportes

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "API de reportes del sistema")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/resumen")
    @Operation(summary = "Reporte resumen general: stock por bodega y productos más movidos")
    public ResponseEntity<ReporteResumenResponseDTO> obtenerResumenGeneral() {
        return ResponseEntity.ok(reporteService.obtenerResumenGeneral());
    }
}