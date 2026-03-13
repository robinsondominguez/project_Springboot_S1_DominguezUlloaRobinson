USE logitrack_db;

-- USUARIOS

INSERT INTO usuarios (username, password, rol) VALUES
('admin', 'admin123', 'ADMIN'),
('empleado1', '123456', 'EMPLEADO'),
('empleado2', '123456', 'EMPLEADO');



-- BODEGAS

INSERT INTO bodegas (nombre, ubicacion, capacidad, encargado) VALUES
('Bodega Central', 'Bogotá', 1000, 'Carlos Pérez'),
('Bodega Norte', 'Medellín', 800, 'Ana López'),
('Bodega Sur', 'Cali', 600, 'Luis Ramírez');



-- PRODUCTOS

INSERT INTO productos (nombre, categoria, stock, precio) VALUES
('Laptop Lenovo', 'Electrónica', 20, 2500.00),
('Mouse Logitech', 'Accesorios', 100, 25.50),
('Teclado Mecánico', 'Accesorios', 50, 80.00),
('Monitor Samsung 24"', 'Electrónica', 30, 320.00),
('Disco SSD 1TB', 'Almacenamiento', 40, 150.00);



-- MOVIMIENTOS

INSERT INTO movimientos (fecha, tipo_movimiento, usuario_id, bodega_origen_id, bodega_destino_id) VALUES
(NOW(), 'ENTRADA', 1, NULL, 1),
(NOW(), 'SALIDA', 2, 1, NULL),
(NOW(), 'TRANSFERENCIA', 1, 1, 2);



-- DETALLE MOVIMIENTOS

INSERT INTO detalle_movimientos (movimiento_id, producto_id, cantidad) VALUES
(1, 1, 10),
(1, 2, 20),
(2, 3, 5),
(3, 1, 3);



-- AUDITORIA

INSERT INTO auditoria (operacion, fecha, usuario, entidad, valor_anterior, valor_nuevo) VALUES
('INSERT', NOW(), 'admin', 'producto', 'null', 'Laptop Lenovo creada'),
('UPDATE', NOW(), 'admin', 'producto', 'stock=20', 'stock=30'),
('DELETE', NOW(), 'admin', 'bodega', 'Bodega Sur', 'registro eliminado');