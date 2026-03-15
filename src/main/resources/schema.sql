
CREATE DATABASE IF NOT EXISTS logitrack_db;
show databases;
USE logitrack_db;


-- TABLA USUARIOS

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);


-- TABLA BODEGAS

CREATE TABLE IF NOT EXISTS bodegas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(150),
    capacidad INT,
    encargado VARCHAR(100)
);


-- TABLA PRODUCTOS

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100),
    stock INT DEFAULT 0,
    precio DECIMAL(10,2)
);


-- TABLA MOVIMIENTOS

CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    usuario_id BIGINT,
    bodega_origen_id BIGINT,
    bodega_destino_id BIGINT,

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (bodega_origen_id) REFERENCES bodegas(id),
    FOREIGN KEY (bodega_destino_id) REFERENCES bodegas(id)
);


-- TABLA DETALLE_MOVIMIENTOS

CREATE TABLE IF NOT EXISTS detalle_movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movimiento_id BIGINT,
    producto_id BIGINT,
    cantidad INT,

    FOREIGN KEY (movimiento_id) REFERENCES movimientos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- TABLA AUDITORIA

CREATE TABLE IF NOT EXISTS auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operacion VARCHAR(20),
    fecha DATETIME,
    usuario VARCHAR(50),
    entidad VARCHAR(100),
    valor_anterior TEXT,
    valor_nuevo TEXT
);