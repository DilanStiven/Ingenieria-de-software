# Documento Consolidado de Diagramas UML — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Estándar de Trazabilidad:** ISO/IEC/IEEE 29148:2018 & ISO/IEC 25010  

---

## 1. Justificación Técnica y Estándar de Representación

Para garantizar la mantenibilidad, auditabilidad y trazabilidad de los artefactos de software exigidos por el modelo de calidad **ISO/IEC 25010** y el estándar de especificación **ISO/IEC/IEEE 29148:2018**, este documento consolida la vista de diseño estructural e interaccional del sistema AgroValle Connect en sintaxis nativa **Mermaid.js**.

- **Renderizado Nativo en GitHub:** Permite visualizar los diagramas vectoriales sin depender de binarios pesados ni plugins de terceros.
- **Trazabilidad con el Código Fuente:** Al residir en texto plano dentro del repositorio, los diagramas evolucionan con cada Pull Request y commit del equipo.
- **Alineación con la Arquitectura en 4 Capas:** Especifica la separación estricta entre capa de presentación REST, seguridad, lógica de negocio y persistencia Spring Data JPA hacia PostgreSQL.

---

## 2. Diagramas de Secuencia UML (Interacción Cronológica en 4 Capas)

Los diagramas de secuencia especifican el flujo cronológico y los mensajes sincrónicos/asincrónicos intercambiados entre las capas de la arquitectura Spring Boot.

### Diagrama 1: Autenticación e Inicio de Sesión JWT (HU-07)

Especifica el flujo de autenticación, verificación de credenciales con hash BCrypt y generación del token JWT firmado.

```mermaid
sequenceDiagram
 autonumber
 actor Cliente as "Comprador / Agricultor"
 participant Controller as AuthController
 participant Service as AuthService
 participant Repo as UsuarioRepository
 participant DB as "PostgreSQL (BD)"
 Cliente->>Controller: POST /api/v1/auth/login (LoginRequestDTO)
 activate Controller
 Controller->>Service: autenticarUsuario(dto)
 activate Service
 Service->>Repo: findByCorreo(correo)
 activate Repo
 Repo->>DB: SELECT * FROM usuarios_productores WHERE correo = ?
 activate DB
 DB-->>Repo: Registro usuario / Hash BCrypt
 deactivate DB
 Repo-->>Service: Optional<Usuario>
 deactivate Repo
 alt Credenciales inválidas
 Service-->>Controller: throw CredencialesInvalidasException
 Controller-->>Cliente: HTTP 401 Unauthorized
 else Credenciales válidas
 Service->>Service: generarTokenJWT(usuario)
 Service-->>Controller: JwtResponseDTO(tokenJWT)
 deactivate Service
 Controller-->>Cliente: HTTP 200 OK (tokenJWT)
 end
 deactivate Controller
```

---

### Diagrama 2: Publicación de Oferta de Cosecha (HU-02)

Valida la identidad del agricultor autenticado, verifica la pertenencia de la finca y persiste la oferta de cosecha con control de excepciones de autorización.

```mermaid
sequenceDiagram
 autonumber
 actor Cliente as Agricultor
 participant Controller as ProductoController
 participant Service as ProductoService
 participant FincaRepo as FincaRepository
 participant ProdRepo as ProductoRepository
 participant DB as "PostgreSQL (BD)"
 Cliente->>Controller: POST /api/v1/productos (PublicarProductoDTO)
 activate Controller
 Controller->>Service: publicarCosecha(dto, usuarioId)
 activate Service
 Service->>FincaRepo: findByIdAndPropietarioId(fincaId, agricultorId)
 activate FincaRepo
 FincaRepo->>DB: SELECT * FROM fincas WHERE id = ?
 activate DB
 DB-->>FincaRepo: Datos Finca
 deactivate DB
 FincaRepo-->>Service: Optional<Finca>
 deactivate FincaRepo
 alt Finca no pertenece al agricultor
 Service-->>Controller: throw FincaNoAutorizadaException
 Controller-->>Cliente: HTTP 403 Forbidden
 else Datos válidos y propiedad verificada
 Service->>ProdRepo: save(ofertaCosecha)
 activate ProdRepo
 ProdRepo->>DB: INSERT INTO ofertas_cosecha (...)
 activate DB
 DB-->>ProdRepo: OfertaCosecha (ID)
 deactivate DB
 ProdRepo-->>Service: OfertaCosecha
 deactivate ProdRepo
 Service-->>Controller: ProductoResponseDTO
 deactivate Service
 Controller-->>Cliente: HTTP 201 Created (ProductoResponseDTO)
 end
 deactivate Controller
```

---

### Diagrama 3: Consulta de Precios Promedio Regionales (HU-03)

Ejecuta el cálculo estadístico de precios históricos de mercado en una ventana de 24 horas sobre transacciones reales consolidadas en el Valle del Cauca.

```mermaid
sequenceDiagram
 autonumber
 actor Cliente as "Usuario / Comprador"
 participant Controller as PrecioController
 participant Service as PrecioService
 participant Repo as TransaccionRepository
 participant DB as "PostgreSQL (BD)"
 Cliente->>Controller: GET /api/v1/precios/promedio?producto=Cafe
 activate Controller
 Controller->>Service: calcularPrecioPromedio("Cafe")
 activate Service
 Service->>Repo: findTransaccionesEnVentana("Cafe", 24h)
 activate Repo
 Repo->>DB: SELECT precio_unitario FROM transacciones ...
 activate DB
 DB-->>Repo: List<BigDecimal> precios
 deactivate DB
 Repo-->>Service: List<BigDecimal>
 deactivate Repo
 Service->>Service: calcularPromedioBigDecimal(precios)
 Service-->>Controller: PrecioPromedioDTO("Cafe", 2500.00)
 deactivate Service
 Controller-->>Cliente: HTTP 200 OK (2500.00 COP/kg)
 deactivate Controller
```

---

## 3. Diagramas de Colaboración / Comunicación

Enfatizan la estructura organizativa de los componentes y la numeración secuencial de mensajes entre objetos del dominio.

### Diagrama de Colaboración 1: Confirmación de Orden y Reserva de Inventario (HU-12)

Ilustra la verificación de idempotencia, obtención de ítems del carrito, descuento transaccional de inventario y creación atómica de la orden.

```mermaid
flowchart TD
 C["1: Cliente (Comprador)"]
 CTRL[":OrdenController"]
 SERV[":OrdenService"]
 INV[":InventarioService"]
 OREPO[":OrdenRepository"]
 CREPO[":CarritoRepository"]
 DB[("PostgreSQL")]
 C -- "1: POST /api/v1/ordenes" --> CTRL
 CTRL -- "2: confirmarOrden(carritoId)" --> SERV
 SERV -- "3: verificarIdempotencia()" --> SERV
 SERV -- "4: obtenerCarrito(carritoId)" --> CREPO
 CREPO -- "5: SELECT * FROM carrito" --> DB
 DB -. "6: datosCarrito" .-> CREPO
 CREPO -. "7: CarritoEntidad" .-> SERV
 SERV -- "8: descontarStock(items)" --> INV
 INV -- "9: UPDATE ofertas_cosecha" --> DB
 SERV -- "10: save(nuevaOrden)" --> OREPO
 OREPO -- "11: INSERT INTO ordenes" --> DB
 DB -. "12: OrdenPersistida" .-> OREPO
 SERV -- "13: vaciarCarrito(carritoId)" --> CREPO
 CREPO -- "14: DELETE FROM carrito_items" --> DB
 SERV -. "15: OrdenResponseDTO" .-> CTRL
 CTRL -. "16: HTTP 201 Created" .-> C
```

---

### Diagrama de Colaboración 2: Cambio de Estado de Despacho y Notificación (Patrón Observer — HU-13)

Modela la transición de estado de una orden logística y la notificación desacoplada al comprador mediante el patrón GoF **Observer** (`ApplicationEventPublisher` y `@EventListener`).

```mermaid
flowchart TD
 A["1: Agricultor"]
 CTRL[":OrdenController"]
 SERV[":OrdenService"]
 OREPO[":OrdenRepository"]
 PUB[":ApplicationEventPublisher"]
 OBS[":NotificacionCompradorListener (Observer)"]
 DB[("PostgreSQL")]
 A -- "1: PATCH /api/v1/ordenes/10/estado" --> CTRL
 CTRL -- "2: actualizarEstado(10, LISTO_PARA_DESPACHO)" --> SERV
 SERV -- "3: findById(10)" --> OREPO
 OREPO -- "4: SELECT * FROM ordenes" --> DB
 DB -. "5: OrdenEntidad" .-> OREPO
 OREPO -. "6: OrdenEntidad" .-> SERV
 SERV -- "7: validarTransicionEstado()" --> SERV
 SERV -- "8: save(ordenActualizada)" --> OREPO
 OREPO -- "9: UPDATE ordenes SET estado = 'LISTO'" --> DB
 SERV -- "10: publishEvent(EstadoOrdenCambiadoEvent)" --> PUB
 PUB -- "11: @EventListener onEstadoOrdenCambiado()" --> OBS
 OBS -- "12: INSERT INTO notificaciones" --> DB
 SERV -. "13: OrdenEstadoDTO" .-> CTRL
 CTRL -. "14: HTTP 200 OK" .-> A
```

---

## 4. Diagrama de Componentes de la Arquitectura MVC en 4 Capas

Representa la estructura física y lógica de los módulos de software, sus interfaces y dependencias cruzadas entre presentación, seguridad/middleware, dominio y persistencia.

```mermaid
componentDiagram
 package "Capa de Presentacion (REST API)" {
 [AuthController]
 [ProductoController]
 [OrdenController]
 }
 package "Capa de Seguridad & Middleware" {
 [Spring Security Filter]
 [JwtAuthenticationFilter]
 }
 package "Capa de Logica de Negocio (Domain Services)" {
 [AuthService]
 [ProductoService]
 [OrdenService]
 [FleteStrategyContext]
 }
 package "Capa de Acceso a Datos (Spring Data JPA)" {
 [UsuarioRepository]
 [ProductoRepository]
 [OrdenRepository]
 }
 database "PostgreSQL Cloud" {
 [Base de Datos Staging]
 }
 [AuthController] ..> [AuthService]
 [ProductoController] ..> [ProductoService]
 [OrdenController] ..> [OrdenService]
 
 [Spring Security Filter] --> [JwtAuthenticationFilter]
 [JwtAuthenticationFilter] ..> [AuthController]
 [AuthService] ..> [UsuarioRepository]
 [ProductoService] ..> [ProductoRepository]
 [OrdenService] ..> [OrdenRepository]
 [OrdenService] ..> [FleteStrategyContext]
 [UsuarioRepository] --> [Base de Datos Staging]
 [ProductoRepository] --> [Base de Datos Staging]
 [OrdenRepository] --> [Base de Datos Staging]
```

---

## 5. Diagrama de Despliegue Físico de Infraestructura

Mapea la distribución física en la nube, los nodos de cómputo y el flujo automatizado de Entrega y Despliegue Continuo (CD).

```mermaid
deploymentDiagram
 node "Cliente Web / Mobile" as client {
 [Navegador Web / Postman]
 }
 node "GitHub Platform" as github {
 [GitHub Repository (main branch)]
 [GitHub Actions Runner (CD Pipeline)]
 }
 node "PaaS Cloud Infrastructure (Render / Railway)" as paas {
 node "Docker Container (Spring Boot App)" {
 [Java 17 Runtime Environment]
 [AgroValle Backend (.jar)]
 }
 }
 node "PostgreSQL Cloud DB (Neon.tech)" as dbnode {
 database "agrovalle_staging" as db
 }
 client -- "HTTP REST (Port 8080 / SSL)" --> paas
 github -- "Webhook Event (DEPLOY_HOOK_URL)" --> paas
 paas -- "JDBC Connection (Port 5432)" --> dbnode
```
