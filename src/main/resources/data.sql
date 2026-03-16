-- =============================================
--  LOGITRACK - Datos de prueba sin NULL
-- =============================================

USE logitrack_db_DominguezRobinson;

-- ─────────────────────────────────────────────
--  BODEGAS
-- ─────────────────────────────────────────────
    INSERT INTO bodegas (id, nombre, ubicacion, capacidad, encargado) VALUES
    (1, 'Bodega Norte',    'Calle 80 #15-30, Bogotá',            5000, 'Carlos López'),
    (2, 'Bodega Sur',      'Av. Las Américas #60-10, Bogotá',    3000, 'Ana Torres'),
    (3, 'Bodega Central',  'Carrera 30 #45-20, Medellín',        4000, 'Luis Pérez'),
    (4, 'Bodega Oriente',  'Zona Industrial Km 5, Cali',         2000, 'María Gómez'),
    (5, 'Bodega Exterior', 'Proveedor / Cliente Externo',        99999, 'Sistema');

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

