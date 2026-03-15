package com.logitrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_movimientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movimiento_id")
    private Movimiento movimiento;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
