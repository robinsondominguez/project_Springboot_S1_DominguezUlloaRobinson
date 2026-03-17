
CREATE DATABASE IF NOT EXISTS logitrack_db;

USE logitrack_db;

 -- drop database logitrack_db;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20)
);

CREATE TABLE bodegas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(255)
);

CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    stock INT DEFAULT 0,
    precio DECIMAL(10, 2)
);

CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20),
    usuario_id BIGINT,
    bodega_origen_id BIGINT,
    bodega_destino_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (bodega_origen_id) REFERENCES bodegas(id),
    FOREIGN KEY (bodega_destino_id) REFERENCES bodegas(id)
);

CREATE TABLE detalle_movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    producto_id BIGINT,
    movimiento_id BIGINT,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (movimiento_id) REFERENCES movimientos(id)
);

CREATE TABLE auditorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    accion VARCHAR(50),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(50),
    tabla VARCHAR(50),
    detalle_id VARCHAR(100),
    descripcion TEXT
);

select * from usuarios;
SELECT * FROM logitrack_db.usuarios;