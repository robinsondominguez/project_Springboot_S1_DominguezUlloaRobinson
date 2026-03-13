package com.logitrack.model;

public class ReporteResumen {

    private String nombreProducto;
    private Long totalMovimientos;

    public ReporteResumen() {
    }

    public ReporteResumen(String nombreProducto, Long totalMovimientos) {
        this.nombreProducto = nombreProducto;
        this.totalMovimientos = totalMovimientos;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getTotalMovimientos() {
        return totalMovimientos;
    }

    public void setTotalMovimientos(Long totalMovimientos) {
        this.totalMovimientos = totalMovimientos;
    }
}