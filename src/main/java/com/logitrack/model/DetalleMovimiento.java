package com.logitrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "movimiento_id")
    private Movimiento movimiento;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
