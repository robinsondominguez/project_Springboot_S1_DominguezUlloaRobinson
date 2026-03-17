package com.logitrack.service;

import com.logitrack.dto.request.DetalleRequestDTO;
import com.logitrack.dto.response.DetalleResponseDTO;
import java.util.List;

public interface DetalleMovimientoService {
    DetalleResponseDTO crear(DetalleRequestDTO dto);
    List<DetalleResponseDTO> listarTodos();
}