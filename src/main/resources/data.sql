-- =============================================
--  LOGITRACK - Datos de prueba sin NULL
-- =============================================

USE logitrack_db;

-- ─────────────────────────────────────────────
--  BODEGAS
-- ─────────────────────────────────────────────
INSERT INTO bodegas (id, nombre, ubicacion) VALUES
(1, 'Bodega Norte',    'Calle 80 #15-30, Bogotá'),
(2, 'Bodega Sur',      'Av. Las Américas #60-10, Bogotá'),
(3, 'Bodega Central',  'Carrera 30 #45-20, Medellín'),
(4, 'Bodega Oriente',  'Zona Industrial Km 5, Cali'),
(5, 'Bodega Exterior', 'Proveedor / Cliente Externo');

-- Bodega 5 = "Bodega Exterior" representa el mundo exterior
-- ENTRADA: viene de Bodega Exterior (5) hacia una bodega interna
-- SALIDA:  sale de una bodega interna hacia Bodega Exterior (5)

-- ─────────────────────────────────────────────
--  PRODUCTOS
-- ─────────────────────────────────────────────
INSERT INTO productos (id, nombre, categoria, stock, precio) VALUES
(1,  'Tornillos M6',        'Ferretería',    450,   0.15),
(2,  'Cables USB Tipo-C',   'Electrónica',     8,   4.99),
(3,  'Cajas de embalaje',   'Empaque',       1200,   0.80),
(4,  'Guantes seguridad',   'Protección',       5,   2.50),
(5,  'Cinta adhesiva 50mm', 'Empaque',        320,   1.20),
(6,  'Bombillos LED 12W',   'Electricidad',   180,   3.75),
(7,  'Pallets de madera',   'Logística',       40,  18.00),
(8,  'Papel bond resma',    'Papelería',       95,   6.50),
(9,  'Aceite lubricante',   'Mantenimiento',   60,   9.80),
(10, 'Extintor 5kg',        'Seguridad',        3,  45.00);

-- ─────────────────────────────────────────────
--  MOVIMIENTOS (sin NULL)
--  ENTRADA:       bodega_origen = 5 (Exterior), bodega_destino = bodega interna
--  SALIDA:        bodega_origen = bodega interna, bodega_destino = 5 (Exterior)
--  TRANSFERENCIA: bodega_origen = interna, bodega_destino = interna
-- ─────────────────────────────────────────────
INSERT INTO movimientos (id, fecha, tipo_movimiento, usuario_id, bodega_origen_id, bodega_destino_id) VALUES
(1, '2026-03-01 08:30:00', 'ENTRADA',       1, 5, 1),
(2, '2026-03-02 10:00:00', 'ENTRADA',       2, 5, 2),
(3, '2026-03-05 14:15:00', 'SALIDA',        1, 1, 5),
(4, '2026-03-08 09:00:00', 'TRANSFERENCIA', 3, 1, 3),
(5, '2026-03-10 11:30:00', 'SALIDA',        2, 2, 5),
(6, '2026-03-12 16:00:00', 'ENTRADA',       1, 5, 4),
(7, '2026-03-14 08:45:00', 'TRANSFERENCIA', 3, 3, 2),
(8, '2026-03-15 13:20:00', 'SALIDA',        2, 4, 5);

-- ─────────────────────────────────────────────
--  DETALLE_MOVIMIENTOS
-- ─────────────────────────────────────────────
INSERT INTO detalle_movimientos (cantidad, producto_id, movimiento_id) VALUES
(200,  1, 1),
(500,  3, 1),
(50,   6, 2),
(30,   7, 3),
(100,  1, 4),
(20,   5, 4),
(50,   3, 5),
(15,   6, 6),
(100,  8, 7),
(20,   9, 7),
(10,   7, 8),
(3,   10, 8);

-- ─────────────────────────────────────────────
--  AUDITORIAS
-- ─────────────────────────────────────────────
INSERT INTO auditorias (accion, fecha, usuario, tabla, detalle_id, descripcion) VALUES
('INSERT', '2026-03-01 08:00:00', 'admin',            'usuarios',     '1', 'Creación de usuario administrador'),
('INSERT', '2026-03-01 08:05:00', 'admin',            'productos',    '1', 'Producto creado: Tornillos M6'),
('INSERT', '2026-03-01 08:30:00', 'admin',            'movimientos',  '1', 'Registro de ENTRADA masiva de suministros'),
('UPDATE', '2026-03-05 14:15:00', 'operador1',        'productos',    '7', 'Stock disminuido por SALIDA. Producto: Pallets de madera'),
('INSERT', '2026-03-08 09:00:00', 'carlos_logistica', 'movimientos',  '4', 'Transferencia entre Bodega Norte y Central'),
('DELETE', '2026-03-15 13:30:00', 'admin',            'productos',   '10', 'Producto marcado para baja: Extintor 5kg');

-- ─────────────────────────────────────────────
--  VERIFICACIÓN
-- ─────────────────────────────────────────────

-- Verificar que no haya NULLs en movimientos
SELECT id, username FROM usuarios;
SELECT id, tipo_movimiento, usuario_id, bodega_origen_id, bodega_destino_id
FROM movimientos;