package com.logitrack.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoRequestDTO {
    private String nombre;
    private String categoria;
    private Integer stock;
    private BigDecimal precio;
}