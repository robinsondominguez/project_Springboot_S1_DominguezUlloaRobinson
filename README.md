# LogiTrack — Sistema de Gestión de Inventario y Logística

# Dominguez Ulloa Robinson
## S1 Cajasan - Campuslands

API REST desarrollada con Spring Boot 3 para la gestión de inventario en bodegas. Permite registrar productos, controlar movimientos de mercancía (entradas, salidas y transferencias entre bodegas), gestionar usuarios con autenticación JWT y consultar reportes del estado del inventario.

### Problematica

La empresa LogiTrack S.A. administra varias bodegas distribuidas en distintas ciudades, encargadas de almacenar productos y gestionar movimientos de inventario (entradas, salidas, y transferencias).

Hasta ahora, el control de inventarios y auditorías se hacía manualmente en hojas de cálculo, sin trazabilidad ni control de accesos.

La dirección general busca implementar un sistema backend centralizado en Spring Boot, que permita:

Controlar todos los movimientos entre bodegas.
Registrar automáticamente los cambios (auditorías).
Proteger la información con autenticación JWT.
Ofrecer endpoints REST documentados y seguros.


### Objetivo General
Desarrollar un sistema de gestión y auditoría de bodegas que permita registrar transacciones de inventario y generar reportes auditables de los cambios realizados por cada usuario.


---

## Tecnologías

| Tecnología | Descripción |
|------------|-------------|
| Java 17+ | Lenguaje principal |
| Spring Boot 3.x | Framework base |
| Spring Security + JWT | Autenticación y autorización |
| Spring Data JPA + Hibernate | Acceso a base de datos |
| MySQL 8+ | Motor de base de datos |
| Lombok | Reducción de boilerplate |
| SpringDoc OpenAPI | Documentación Swagger |

---

## Configuración de Base de Datos

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/logitrack_db
spring.datasource.username=campus2023
spring.datasource.password=campus2023
spring.jpa.hibernate.ddl-auto=update
```

Crear la base de datos antes de iniciar:

```sql
CREATE DATABASE logitrack_db;
```

---

## Como ejecutar

```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.
Swagger UI disponible en `http://localhost:8080/swagger-ui.html`.

---

## Autenticacion

Todos los endpoints (excepto `/auth/**`) requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

Flujo:
1. `POST /auth/register` — registrar usuario con rol `ADMIN` o `EMPLEADO`
2. `POST /auth/login` — obtener el token (expira en 24 horas)
3. Incluir el token en todas las peticiones siguientes

---

## Estructura del Proyecto

```
src/main/java/com/logitrack/
|
+-- SpringProjectS1Application.java      Punto de entrada de la aplicacion
|
+-- config/
|   +-- OpenApiConfig.java               Configuracion de Swagger / OpenAPI
|
+-- controller/                          Capa de presentacion, expone los endpoints REST
|   +-- AuthController.java              Login y registro de usuarios
|   +-- ProductoController.java          CRUD de productos e inventario
|   +-- MovimientoController.java        Movimientos de mercancia
|   +-- DetalleMovimientoController.java Detalle linea por linea de cada movimiento
|   +-- BodegaController.java            CRUD de bodegas
|   +-- UsuarioController.java           Consulta y gestion de usuarios
|   +-- AuditoriaController.java         Consulta del log de auditoria
|   +-- ReporteController.java           Reportes resumen del sistema
|
+-- service/                             Interfaces de la logica de negocio
|   +-- ProductoService.java
|   +-- MovimientoService.java
|   +-- DetalleMovimientoService.java
|   +-- BodegaService.java
|   +-- UsuarioService.java
|   +-- AuditoriaService.java
|   +-- ReporteService.java
|
+-- service/impl/                        Implementaciones de la logica de negocio
|   +-- ProductoServiceImpl.java         Gestion de stock y auditoria de productos
|   +-- MovimientoServiceImpl.java       Creacion y reportes de movimientos
|   +-- DetalleMovimientoServiceImpl.java Actualizacion de stock y validacion de capacidad de bodega
|   +-- BodegaServiceImpl.java           Calculo de espacio ocupado en bodega
|   +-- UsuarioServiceImpl.java          Busqueda y persistencia de usuarios
|   +-- AuditoriaServiceImpl.java        Guardado de registros de auditoria
|   +-- ReporteServiceImpl.java          Agrega stock por bodega y productos mas movidos
|   +-- UserDetailsServiceImpl.java      Integracion de usuarios con Spring Security
|
+-- model/                               Entidades JPA (tablas de la base de datos)
|   +-- Producto.java                    id, nombre, categoria, stock, precio
|   +-- Movimiento.java                  id, fecha, tipoMovimiento, producto, cantidad, usuario, bodegaOrigen, bodegaDestino
|   +-- DetalleMovimiento.java           id, cantidad, producto, movimiento
|   +-- Bodega.java                      id, nombre, ubicacion, capacidad, encargado
|   +-- Usuario.java                     id, username, password, rol
|   +-- Auditoria.java                   id, operacion, fecha, usuario, entidad, valorAnterior, valorNuevo
|   +-- Rol.java                         Enum: ADMIN, EMPLEADO
|   +-- TipoMovimiento.java              Enum: ENTRADA, SALIDA, TRANSFERENCIA
|
+-- dto/
|   +-- request/                         Objetos que recibe la API (cuerpo del request)
|   |   +-- ProductoRequestDTO.java      nombre, categoria, stock, precio
|   |   +-- MovimientoRequestDTO.java    tipoMovimiento*, usuarioId*, productoId, cantidad, bodegaOrigenId, bodegaDestinoId
|   |   +-- DetalleRequestDTO.java       cantidad, productoId, movimientoId
|   |   +-- UsuarioRequestDTO.java       username, password, rol
|   |   +-- LoginRequest.java            username, password
|   |
|   +-- response/                        Objetos que devuelve la API (cuerpo del response)
|       +-- ProductoResponseDTO.java     id, nombre, categoria, stock, precio
|       +-- MovimientoResponseDTO.java   id, fecha, tipoMovimiento, nombreUsuario
|       +-- DetalleResponseDTO.java      id, cantidad, nombreProducto, tipoMovimiento, fechaMovimiento, bodegas
|       +-- UsuarioResponseDTO.java      id, username, rol
|       +-- AuthControllerResponseDTO.java  token JWT
|       +-- ErrorResponseDTO.java        timestamp, mensaje, detalles
|       +-- ReporteResumen.java          nombreProducto, totalMovimientos
|       +-- ReporteResumenResponseDTO.java  totalProductos, totalMovimientos, estadoSistema, stockPorBodega, productosMasMovidos
|
+-- repository/                          Interfaces de acceso a base de datos (Spring Data JPA)
|   +-- ProductoRepository.java          Incluye findByStockLessThan()
|   +-- MovimientoRepository.java        Incluye findByFechaBetween()
|   +-- DetalleMovimientoRepository.java
|   +-- BodegaRepository.java
|   +-- UsuarioRepository.java           Incluye findByUsername()
|   +-- AuditoriaRepository.java
|
+-- mapper/                              Conversion entre entidades y DTOs
|   +-- ProductoMapper.java
|   +-- MovimientoMapper.java
|   +-- DetalleMovimientoMapper.java
|
+-- exception/                           Manejo centralizado de errores
|   +-- ResourceNotFoundException.java   Excepcion para recursos no encontrados
|   +-- GlobalExceptionHandler.java      Captura todas las excepciones y devuelve ErrorResponseDTO
|
+-- security/                            Seguridad y autenticacion JWT
    +-- SecurityConfig.java              Rutas publicas (/auth/**), filtros, CORS, sesion stateless
    +-- JwtUtil.java                     Generacion y validacion de tokens JWT (expira en 24h)
    +-- JwtAuthenticationFilter.java     Filtro que lee el token en cada request
    +-- CustomUserDetailsService.java    Carga el usuario desde la BD para Spring Security
```

---

## Endpoints Principales

| Metodo | Endpoint | Descripcion | Auth |
|--------|----------|-------------|------|
| POST | `/auth/register` | Registrar usuario | No |
| POST | `/auth/login` | Login, obtener token JWT | No |
| GET | `/api/productos` | Listar productos | Si |
| POST | `/api/productos` | Crear producto | Si |
| GET | `/api/productos/stock-bajo` | Productos con stock menor a 10 | Si |
| GET | `/api/movimientos` | Listar movimientos | Si |
| POST | `/api/movimientos` | Crear movimiento | Si |
| GET | `/api/movimientos/buscar-por-fechas` | Filtrar por rango de fechas | Si |
| GET | `/api/movimientos/resumen` | Reporte general de movimientos | Si |
| GET | `/api/bodegas` | Listar bodegas | Si |
| POST | `/api/bodegas` | Crear bodega | Si |
| PUT | `/api/bodegas/{id}` | Actualizar bodega | Si |
| DELETE | `/api/bodegas/{id}` | Eliminar bodega | Si |
| GET | `/usuarios` | Listar usuarios | Si |
| GET | `/auditorias` | Ver log de auditoria | Si |
| GET | `/auditorias/usuario/{usuario}` | Auditorias filtradas por usuario | Si |
| GET | `/api/reportes/resumen` | Reporte resumen completo | Si |
| GET | `/api/detalles` | Listar detalles de movimientos | Si |
| POST | `/api/detalles` | Registrar detalle y actualizar stock | Si |

Ver documentacion detallada de cada endpoint en `ENDPOINTS.md`.

---

## Manejo de Errores

El sistema responde con un objeto estandar ante cualquier error:

```json
{
  "timestamp": "2026-03-17T10:00:00",
  "mensaje": "Descripcion del error",
  "detalles": "uri=/api/ruta"
}
```

| Codigo | Cuando ocurre |
|--------|---------------|
| 400 Bad Request | Recurso no encontrado, stock insuficiente, bodega sin espacio, datos invalidos |
| 401 Unauthorized | Token JWT ausente, expirado o invalido |
| 500 Internal Server Error | Error inesperado del servidor |

---

## Notas de Diseno

- Auditoria automatica: al crear, actualizar stock o eliminar un producto, se registra automáticamente un evento en la tabla `auditorias` con el usuario autenticado, la operacion y los valores anterior y nuevo.
- Control de capacidad: al registrar un detalle de movimiento tipo ENTRADA o TRANSFERENCIA, el sistema valida que la bodega destino tenga espacio disponible antes de confirmar.
- Bodega Exterior (ID 5): se usa como bodega virtual que representa proveedores externos (origen en entradas) y clientes (destino en salidas).

---

# Documentacion de Endpoints — LogiTrack API

Base URL: http://localhost:8080
Autenticacion: Bearer Token JWT (excepto /auth/**)
Formato: JSON (Content-Type: application/json)

---

## Autenticacion

Todos los endpoints (excepto los de /auth) requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

El token se obtiene haciendo login y expira en 24 horas.

---

## Estructura del Error Estandar

Cuando ocurre un error, el servidor responde con este objeto:

```json
{
  "timestamp": "2026-03-17T10:00:00",
  "mensaje": "Descripcion del error",
  "detalles": "uri=/ruta/que/fallo"
}
```

---

## 1. AuthController — /auth

Endpoints publicos (no requieren token). Gestion de registro e inicio de sesion.

---

### POST /auth/register

Registra un nuevo usuario en el sistema.

Request Body:
```json
{
  "username": "operador1",
  "password": "mi_password",
  "rol": "EMPLEADO"
}
```

| Campo      | Tipo   | Requerido | Descripcion                                           |
|------------|--------|-----------|-------------------------------------------------------|
| username   | String | Si        | Nombre de usuario unico                               |
| password   | String | Si        | Contrasena en texto plano (se encripta con BCrypt)    |
| rol        | String | Si        | ADMIN o EMPLEADO                                      |

Respuesta exitosa 200 OK:
```json
{
  "id": 1,
  "username": "operador1",
  "rol": "EMPLEADO"
}
```

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 400 Bad Request | El username ya existe en la base de datos |
| 500 Internal Server Error | Error inesperado del servidor |

---

### POST /auth/login

Autentica un usuario y devuelve el token JWT.

Request Body:
```json
{
  "username": "operador1",
  "password": "mi_password"
}
```

Respuesta exitosa 200 OK:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 401 Unauthorized | Credenciales incorrectas (usuario no existe o contrasena erronea) |
| 500 Internal Server Error | Error inesperado del servidor |

Nota: copiar el valor de "token" y usarlo como "Bearer <token>" en el header de todas las demas peticiones.

---

## 2. ProductoController — /api/productos

Gestion del catalogo de productos e inventario. Requiere token JWT.

---

### GET /api/productos

Lista todos los productos registrados en el sistema.

Sin parametros.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "nombre": "Tornillos M6",
    "categoria": "Ferreteria",
    "stock": 450,
    "precio": 0.15
  },
  {
    "id": 2,
    "nombre": "Cables USB Tipo-C",
    "categoria": "Electronica",
    "stock": 8,
    "precio": 4.99
  }
]
```

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 401 Unauthorized | No se envio token o el token es invalido o expirado |

---

### GET /api/productos/{id}

Obtiene un producto especifico por su ID.

Path Variable:
- {id} — ID numerico del producto. Ejemplo: /api/productos/3

Respuesta exitosa 200 OK:
```json
{
  "id": 3,
  "nombre": "Cajas de embalaje",
  "categoria": "Empaque",
  "stock": 1200,
  "precio": 0.80
}
```

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe un producto con ese ID | "Producto no encontrado" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### POST /api/productos

Crea un nuevo producto en el catalogo.

Request Body:
```json
{
  "nombre": "Extintor 5kg",
  "categoria": "Seguridad",
  "stock": 10,
  "precio": 45.00
}
```

| Campo    | Tipo       | Requerido | Descripcion                    |
|----------|------------|-----------|--------------------------------|
| nombre   | String     | Si        | Nombre del producto            |
| categoria| String     | Si        | Categoria del producto         |
| stock    | Integer    | Si        | Cantidad inicial en inventario |
| precio   | BigDecimal | Si        | Precio unitario                |

Respuesta exitosa 200 OK:
```json
{
  "id": 11,
  "nombre": "Extintor 5kg",
  "categoria": "Seguridad",
  "stock": 10,
  "precio": 45.00
}
```

Al crear un producto se registra automaticamente una entrada en Auditoria con operacion INSERT.

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 401 Unauthorized | Token ausente o invalido |
| 500 Internal Server Error | Error al persistir en base de datos |

---

### GET /api/productos/stock-bajo

Devuelve todos los productos cuyo stock es menor a 10 unidades. Util para alertas de reabastecimiento.

Sin parametros.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 4,
    "nombre": "Guantes seguridad",
    "categoria": "Proteccion",
    "stock": 5,
    "precio": 2.50
  }
]
```

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 401 Unauthorized | Token ausente o invalido |

---

## 3. MovimientoController — /api/movimientos

Registro y consulta de movimientos de inventario. Requiere token JWT.

---

### GET /api/movimientos

Lista todos los movimientos registrados.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "fecha": "2026-03-01T08:30:00",
    "tipoMovimiento": "ENTRADA",
    "nombreUsuario": "admin"
  }
]
```

---

### GET /api/movimientos/{id}

Obtiene un movimiento por su ID.

Respuesta exitosa 200 OK: mismo formato que el objeto del listado.

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe un movimiento con ese ID | "Movimiento no encontrado" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### POST /api/movimientos

Crea un nuevo movimiento de inventario.

Request Body:
```json
{
  "tipoMovimiento": "ENTRADA",
  "usuarioId": 1,
  "productoId": 2,
  "cantidad": 100,
  "bodegaOrigenId": 5,
  "bodegaDestinoId": 1
}
```

| Campo            | Tipo    | Requerido | Descripcion |
|------------------|---------|-----------|-------------|
| tipoMovimiento   | String  | Si        | ENTRADA, SALIDA o TRANSFERENCIA |
| usuarioId        | Long    | Si        | ID del usuario responsable |
| productoId       | Long    | No        | ID del producto involucrado |
| cantidad         | Integer | No        | Unidades a mover |
| bodegaOrigenId   | Long    | No        | ID de la bodega de origen |
| bodegaDestinoId  | Long    | No        | ID de la bodega de destino |

Convencion de bodegas:
- ENTRADA: bodegaOrigenId = 5 (Bodega Exterior), bodegaDestinoId = bodega interna
- SALIDA: bodegaOrigenId = bodega interna, bodegaDestinoId = 5
- TRANSFERENCIA: ambas IDs son bodegas internas

Respuesta exitosa 200 OK:
```json
{
  "id": 9,
  "fecha": "2026-03-17T10:30:00",
  "tipoMovimiento": "ENTRADA",
  "nombreUsuario": "admin"
}
```

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | usuarioId no existe | "Usuario no encontrado" |
| 400 Bad Request | productoId no existe | "Producto no encontrado" |
| 400 Bad Request | bodegaOrigenId no existe | "Bodega origen no encontrada" |
| 400 Bad Request | bodegaDestinoId no existe | "Bodega destino no encontrada" |
| 400 Bad Request | tipoMovimiento tiene valor invalido | Error de conversion del enum |
| 400 Bad Request | Campo obligatorio vacio | "El tipo de movimiento es obligatorio" / "El ID de usuario es obligatorio" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### GET /api/movimientos/buscar-por-fechas

Busca movimientos dentro de un rango de fechas.

Query Params:

| Parametro | Tipo         | Requerido | Ejemplo |
|-----------|--------------|-----------|---------|
| inicio    | ISO DateTime | Si        | 2026-03-01T00:00:00 |
| fin       | ISO DateTime | Si        | 2026-03-31T23:59:59 |

Ejemplo de URL:
```
GET /api/movimientos/buscar-por-fechas?inicio=2026-03-01T00:00:00&fin=2026-03-31T23:59:59
```

Respuesta exitosa 200 OK: lista de movimientos en el rango.

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 400 Bad Request | Formato de fecha incorrecto |
| 401 Unauthorized | Token ausente o invalido |

---

### GET /api/movimientos/resumen

Genera un reporte general con los productos mas movidos y el stock por bodega.

Respuesta exitosa 200 OK:
```json
{
  "productosTop": [
    { "nombreProducto": "Tornillos M6", "totalMovimientos": 300 }
  ],
  "resumenBodegas": {
    "Bodega Norte": 1000,
    "Bodega Sur": 500
  },
  "totalOperaciones": 8,
  "fechaReporte": "2026-03-17T10:30:00"
}
```

---

## 4. BodegaController — /api/bodegas

CRUD completo de bodegas. Requiere token JWT.

---

### GET /api/bodegas

Lista todas las bodegas.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "nombre": "Bodega Norte",
    "ubicacion": "Calle 80 #15-30, Bogota",
    "capacidad": 1000,
    "encargado": "Carlos Lopez"
  }
]
```

---

### GET /api/bodegas/{id}

Obtiene una bodega por su ID.

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe bodega con ese ID | "No Existe Este Ud" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### POST /api/bodegas

Crea una nueva bodega.

Request Body:
```json
{
  "nombre": "Bodega Occidente",
  "ubicacion": "Av. 68 #13-20, Bogota",
  "capacidad": 2000,
  "encargado": "Ana Torres"
}
```

| Campo     | Tipo    | Requerido | Descripcion |
|-----------|---------|-----------|-------------|
| nombre    | String  | Si        | Nombre de la bodega |
| ubicacion | String  | No        | Direccion fisica |
| capacidad | Integer | No        | Capacidad maxima en unidades |
| encargado | String  | No        | Nombre del responsable |

Respuesta exitosa 200 OK: objeto Bodega completo con ID asignado.

---

### PUT /api/bodegas/{id}

Actualiza todos los campos de una bodega existente.

Request Body: misma estructura que POST.

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe bodega con ese ID | "No se encontro una bodega a partir de este Id" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### DELETE /api/bodegas/{id}

Elimina una bodega por su ID.

Respuesta exitosa 200 OK: sin cuerpo de respuesta.

Errores posibles:

| Codigo | Cuando ocurre |
|--------|---------------|
| 401 Unauthorized | Token ausente o invalido |

Advertencia: no valida si la bodega tiene movimientos asociados antes de eliminar.

---

## 5. UsuarioController — /usuarios

Consulta y gestion de usuarios. Requiere token JWT.

---

### GET /usuarios

Lista todos los usuarios registrados.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "username": "admin",
    "password": "$2a$10$...",
    "rol": "ADMIN"
  }
]
```

Advertencia: este endpoint devuelve la entidad completa incluyendo el hash de la contrasena.

---

### POST /usuarios

Crea un usuario sin encriptar la contrasena. Para registro con encriptacion usar POST /auth/register.

---

### GET /usuarios/{id}

Busca un usuario por su ID.

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe usuario con ese ID | "Usuario no encontrado con ID: {id}" |
| 401 Unauthorized | Token ausente o invalido | — |

---

### GET /usuarios/buscar?username={username}

Busca un usuario por su nombre de usuario.

Query Param: username — nombre exacto del usuario.

Ejemplo: /usuarios/buscar?username=admin

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | No existe usuario con ese username | "Usuario no encontrado con nombre: {username}" |
| 401 Unauthorized | Token ausente o invalido | — |

---

## 6. AuditoriaController — /auditorias

Consulta del registro de auditoria del sistema. Requiere token JWT. Los registros se crean automaticamente al modificar productos.

---

### GET /auditorias

Lista todos los registros de auditoria.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "operacion": "INSERT",
    "fecha": "2026-03-01T08:05:00",
    "usuario": "admin",
    "entidad": "Producto",
    "valorAnterior": "N/A",
    "valorNuevo": "Nombre: Tornillos M6"
  }
]
```

| Campo         | Descripcion |
|---------------|-------------|
| operacion     | Tipo de operacion: INSERT, UPDATE_STOCK, DELETE |
| entidad       | Entidad afectada. Ejemplo: Producto |
| valorAnterior | Estado antes del cambio |
| valorNuevo    | Estado despues del cambio |

---

### GET /auditorias/usuario/{usuario}

Filtra registros de auditoria por nombre de usuario.

Ejemplo: /auditorias/usuario/admin

Respuesta exitosa 200 OK: lista filtrada de auditorias.

---

### GET /auditorias/operacion/{operacion}

Filtra registros de auditoria por tipo de operacion.

Ejemplo: /auditorias/operacion/INSERT

Valores validos: INSERT, UPDATE_STOCK, DELETE

Respuesta exitosa 200 OK: lista filtrada de auditorias.

---

## 7. ReporteController — /api/reportes

Reportes agregados del sistema. Requiere token JWT.

---

### GET /api/reportes/resumen

Retorna un resumen general del estado del inventario.

Respuesta exitosa 200 OK:
```json
{
  "totalProductos": 10,
  "totalMovimientos": 8,
  "estadoSistema": "SISTEMA LOGITRACK OPERATIVO",
  "stockPorBodega": {
    "Bodega Norte": 300,
    "Bodega Sur": 50,
    "Bodega Central": 120
  },
  "productosMasMovidos": {
    "Tornillos M6": 3,
    "Cajas de embalaje": 2
  }
}
```

| Campo               | Descripcion |
|---------------------|-------------|
| totalProductos      | Numero total de productos en catalogo |
| totalMovimientos    | Numero total de movimientos registrados |
| estadoSistema       | Estado operacional del sistema |
| stockPorBodega      | Unidades acumuladas en cada bodega segun detalles de movimientos |
| productosMasMovidos | Conteo de apariciones en detalles de movimientos por producto |

---

## 8. DetalleMovimientoController — /api/detalles

Registro del detalle de cada movimiento. Al crear un detalle, el stock del producto se actualiza automaticamente. Requiere token JWT.

---

### GET /api/detalles

Lista todos los detalles de movimientos registrados.

Respuesta exitosa 200 OK:
```json
[
  {
    "id": 1,
    "cantidad": 200,
    "nombreProducto": "Tornillos M6",
    "tipoMovimiento": "ENTRADA",
    "fechaMovimiento": "2026-03-01T08:30:00",
    "nombreBodegaOrigen": "Bodega Exterior",
    "nombreBodegaDestino": "Bodega Norte"
  }
]
```

---

### POST /api/detalles

Registra el detalle de un movimiento y actualiza el stock del producto automaticamente.

Request Body:
```json
{
  "cantidad": 50,
  "productoId": 1,
  "movimientoId": 3
}
```

| Campo        | Tipo    | Requerido | Descripcion |
|--------------|---------|-----------|-------------|
| cantidad     | Integer | Si        | Unidades a procesar |
| productoId   | Long    | Si        | ID del producto |
| movimientoId | Long    | Si        | ID del movimiento al que pertenece este detalle |

Logica de stock segun tipo de movimiento:
- ENTRADA: suma la cantidad al stock del producto
- SALIDA: resta la cantidad del stock del producto
- TRANSFERENCIA: suma la cantidad al stock del producto (registra llegada al destino)

Logica de capacidad de bodega:
- Para ENTRADA y TRANSFERENCIA se verifica que la bodega destino tenga espacio disponible.

Respuesta exitosa 200 OK: objeto DetalleResponseDTO completo.

Errores posibles:

| Codigo | Cuando ocurre | Mensaje |
|--------|---------------|---------|
| 400 Bad Request | movimientoId no existe | "Movimiento no encontrado" |
| 400 Bad Request | productoId no existe | "Producto no encontrado" |
| 400 Bad Request | Stock insuficiente para SALIDA | "Stock insuficiente" |
| 400 Bad Request | Bodega destino sin espacio | "Error! La bodega {nombre} no tiene espacio suficiente. Espacio disponible: {X}" |
| 401 Unauthorized | Token ausente o invalido | — |
| 500 Internal Server Error | Error inesperado | "Ocurrio un error interno en el servidor" |

---

## Resumen de Todos los Endpoints

| Metodo | Endpoint | Descripcion | Auth |
|--------|----------|-------------|------|
| POST | /auth/register | Registrar usuario | No |
| POST | /auth/login | Login, obtener token | No |
| GET | /api/productos | Listar productos | Si |
| GET | /api/productos/{id} | Obtener producto por ID | Si |
| POST | /api/productos | Crear producto | Si |
| GET | /api/productos/stock-bajo | Productos con stock menor a 10 | Si |
| GET | /api/movimientos | Listar movimientos | Si |
| GET | /api/movimientos/{id} | Obtener movimiento por ID | Si |
| POST | /api/movimientos | Crear movimiento | Si |
| GET | /api/movimientos/buscar-por-fechas | Movimientos por rango de fechas | Si |
| GET | /api/movimientos/resumen | Reporte general de movimientos | Si |
| GET | /api/bodegas | Listar bodegas | Si |
| GET | /api/bodegas/{id} | Obtener bodega por ID | Si |
| POST | /api/bodegas | Crear bodega | Si |
| PUT | /api/bodegas/{id} | Actualizar bodega | Si |
| DELETE | /api/bodegas/{id} | Eliminar bodega | Si |
| GET | /usuarios | Listar usuarios | Si |
| POST | /usuarios | Crear usuario (sin encriptar) | Si |
| GET | /usuarios/{id} | Buscar usuario por ID | Si |
| GET | /usuarios/buscar?username={username} | Buscar usuario por nombre | Si |
| GET | /auditorias | Listar auditorias | Si |
| GET | /auditorias/usuario/{usuario} | Auditorias por usuario | Si |
| GET | /auditorias/operacion/{operacion} | Auditorias por operacion | Si |
| GET | /api/reportes/resumen | Reporte resumen general | Si |
| GET | /api/detalles | Listar detalles de movimientos | Si |
| POST | /api/detalles | Crear detalle y actualizar stock | Si |

---

## Swagger UI

La documentacion interactiva de la API esta disponible en:

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/swagger-ui/index.html
```

No requiere token para acceder a la interfaz.
