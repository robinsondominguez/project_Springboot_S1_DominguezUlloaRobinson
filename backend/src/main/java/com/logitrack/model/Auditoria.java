package com.logitrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operacion;

    private LocalDateTime fecha;

    private String usuario;

    private String entidad;

    @Column(columnDefinition = "TEXT")
    private String valorAnterior;

    @Column(columnDefinition = "TEXT")
    private String valorNuevo;

}
