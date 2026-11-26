# Diagramas UML — AgroValle Connect

**Institución Universitaria Antonio José Camacho**

**Asignatura:** Ingeniería de Software II | **Período:** 2-2026

**Proyecto Integrador:** AgroValle Connect
**Ubicación:** `docs/diseno/architecture/diagramas-uml.md`

## Alcance y fuente de verdad

Estos diagramas describen el código y el despliegue vigentes. Cuando el escenario de la guía no
coincide con lo implementado, se muestra el flujo real y se deja explícita la brecha; no se presenta
un diseño futuro como si ya existiera. La guía indica `docs/architecture/`, pero este repositorio
conserva los artefactos de arquitectura en `docs/diseno/` por indicación de la docente.

## 1. Secuencia: autenticación e inicio de sesión JWT (HU-07)

`POST /api/v1/auth/login` normaliza el correo, verifica la contraseña con BCrypt y devuelve el JWT
en `AgricultorResponseDTO`.

```mermaid
sequenceDiagram
    actor Cliente
    participant API as AgricultorController
    participant Servicio as AgricultorService
    participant Repo as AgricultorRepository
    participant DB as PostgreSQL
    participant BCrypt as PasswordEncoder
    participant JWT as JwtService

    Cliente->>API: POST /api/v1/auth/login (LoginRequestDTO)
    API->>Servicio: autenticar(dto)
    Servicio->>Servicio: normalizarCorreo(correo)
    Servicio->>Repo: findByCorreoIgnoreCase(correo)
    Repo->>DB: consultar agricultor
    DB-->>Repo: agricultor encontrado o vacío
    Repo-->>Servicio: Optional<Agricultor>
    Servicio->>BCrypt: matches(password, passwordHash)
    BCrypt-->>Servicio: true o false
    alt Credenciales inválidas
        Servicio-->>API: CredencialesInvalidasException
        API-->>Cliente: HTTP 401 Unauthorized
    else Credenciales válidas
        Servicio->>JWT: generarToken(agricultorId)
        JWT-->>Servicio: token firmado
        Servicio-->>API: AgricultorResponseDTO
        API-->>Cliente: HTTP 200 OK (datos y JWT)
    end
```

## 2. Secuencia: publicación de cosecha/producto (HU-02)

El agricultor se obtiene del principal autenticado; no se consulta `FincaRepository` ni se crea una
entidad `Oferta`. La publicación crea un `Producto` asociado al agricultor.

```mermaid
sequenceDiagram
    actor Agricultor
    participant Filtro as JwtAuthenticationFilter
    participant API as ProductoController
    participant Servicio as ProductoService
    participant Agricultores as AgricultorRepository
    participant Productos as ProductoRepository
    participant DB as PostgreSQL
    participant Eventos as ApplicationEventPublisher
    participant Listener as ProductoPublicadoListener

    Agricultor->>Filtro: POST /api/v1/productos + JWT + DTO
    Filtro->>API: identidad autenticada
    API->>Servicio: publicar(dto, agricultorId)
    Servicio->>Servicio: validar fecha de cosecha
    Servicio->>Agricultores: findById(agricultorId)
    Agricultores->>DB: consultar agricultor
    DB-->>Agricultores: agricultor
    Agricultores-->>Servicio: Agricultor
    Servicio->>Productos: save(producto)
    Productos->>DB: INSERT producto
    DB-->>Productos: producto persistido
    Productos-->>Servicio: Producto
    Servicio->>Eventos: publishEvent(ProductoPublicadoEvent)
    Eventos-->>Listener: entregar evento
    Listener-->>Listener: registrar evento en log
    Servicio-->>API: ProductoResponseDTO
    API-->>Agricultor: HTTP 201 Created
```

## 3. Secuencia: consulta de precios promedio regionales (HU-03)

`GET /api/v1/precios/promedio` calcula el promedio de transacciones de las últimas 24 horas. Si no
hay datos para ese período, el servicio comunica que el precio no está disponible.

```mermaid
sequenceDiagram
    actor Cliente
    participant API as PrecioController
    participant Servicio as PrecioService
    participant Repo as PrecioRepository
    participant DB as PostgreSQL

    Cliente->>API: GET /api/v1/precios/promedio?producto=...
    API->>Servicio: obtenerPromedio(producto)
    Servicio->>Servicio: definir ventana de 24 horas
    Servicio->>Repo: calcularPromedio(producto, desde, hasta)
    Repo->>DB: agregar transacciones del período
    DB-->>Repo: promedio y cantidad
    Repo-->>Servicio: PromedioPrecio
    alt Sin transacciones
        Servicio-->>API: PrecioNoDisponibleException
        API-->>Cliente: error de precio no disponible
    else Con transacciones
        Servicio->>Servicio: redondear a 2 decimales
        Servicio-->>API: PrecioPromedioResponseDTO
        API-->>Cliente: HTTP 200 OK (COP/kg)
    end
```

## 4. Colaboración: pago de pedido, inventario y rechazo (HU-09/HU-12)

El flujo de aprobación registra la compra y descuenta inventario (HU-09); la rama de rechazo conserva
el pedido durante 15 minutos para reintentar (HU-12 del Backlog). El PDF usa HU-12 para un escenario
distinto de confirmación de orden/carrito. El repositorio procesa un `Pedido` existente y no usa
carrito ni idempotencia.

```mermaid
flowchart TD
    Comprador["1: Comprador autenticado"]
    API["2: PagoController"]
    Pago["3: PagoService"]
    PedidoRepo["4: PedidoRepository"]
    ProductoRepo["5: ProductoRepository"]
    Pasarela["6: PasarelaPago"]
    PagoRepo["7: PagoRepository"]
    OrdenRepo["8: OrdenCompraRepository"]
    Logistica["9: OrdenLogisticaService"]
    Billetera["10: BilleteraService"]
    Precio["11: PrecioService"]
    DB[("PostgreSQL")]
    Decision{"Pago aprobado"}
    Reintento["Pago rechazado; conservar pedido y fecha límite"]
    Aprobado["Confirmar compra"]
    Respuesta["Respuesta PagoResponseDTO"]

    Comprador -->|"POST /api/v1/pagos"| API
    API -->|"procesar(pedidoId, compradorId, medio)"| Pago
    Pago -->|"buscarPorIdConBloqueo"| PedidoRepo
    PedidoRepo -->|"cargar pedido"| DB
    Pago -->|"validar propietario, estado y vigencia"| Pago
    Pago -->|"buscar producto con bloqueo y validar stock"| ProductoRepo
    ProductoRepo -->|"cargar producto"| DB
    Pago -->|"procesarPago(monto, medio)"| Pasarela
    Pasarela -->|"resultado aprobado o rechazado"| Pago
    Pago -->|"guardar pago"| PagoRepo
    PagoRepo -->|"persistir"| DB
    Pago -->|"evaluar resultado"| Decision
    Decision -->|"No"| Reintento
    Reintento -->|"actualizar estado y fecha límite"| PedidoRepo
    PedidoRepo -->|"persistir pedido"| DB
    Reintento --> Respuesta
    Decision -->|"Sí"| Aprobado
    Aprobado -->|"marcar pedido pagado"| PedidoRepo
    Aprobado -->|"descontar cantidad y actualizar estado"| ProductoRepo
    ProductoRepo -->|"persistir producto"| DB
    Aprobado -->|"registrar transacción"| Precio
    Precio -->|"guardar precio"| DB
    Aprobado -->|"crear orden de compra"| OrdenRepo
    OrdenRepo -->|"persistir"| DB
    Aprobado -->|"crear orden logística"| Logistica
    Logistica -->|"persistir orden"| DB
    Aprobado -->|"acreditar agricultor"| Billetera
    Billetera -->|"actualizar saldo"| DB
    Aprobado --> Respuesta
    Respuesta --> API
    API -->|"HTTP 200"| Comprador
```

## Flujo de entrega conectado (HU-08, HU-13 y HU-14)

HU-08 sigue la secuencia `ALISTADO` → `EN_RUTA` → `EN_DESTINO` → `ENTREGADA`. El transportista
asignado registra la llegada mediante `PATCH /api/v1/viajes/{id}/llegada`; el evento asociado
persiste una notificación para el comprador después del commit, consultable en
`GET /api/v1/notificaciones/comprador`. El transportista asignado confirma el cierre con
`PATCH /api/v1/viajes/{id}/entrega`, que registra el checkpoint final y libera el vehículo.
HU-13 y HU-14 aceptan calificaciones únicamente cuando la orden está `ENTREGADA`.

## 5. Colaboración / Observer vigente: notificación de solicitud de contacto (HU-05)

El PDF asigna HU-13 al cambio de estado de despacho con notificación Observer, pero el Backlog
aprobado define HU-13 como calificación del producto y del agricultor. HU-08 y HU-13 ya se
implementaron con esos alcances del Backlog. Este diagrama muestra el Observer vigente para una
solicitud de contacto (HU-05), donde se persiste una notificación para el agricultor antes del
commit; no representa el escenario de cambio de despacho solicitado en la guía.

```mermaid
flowchart TD
    Cliente["1: Comprador autenticado"]
    API["2: ContactoController"]
    Servicio["3: ContactoService"]
    ProductoRepo["4: ProductoRepository"]
    PedidoRepo["5: PedidoRepository"]
    MensajeRepo["6: ContactoMensajeRepository"]
    Publicador["7: ApplicationEventPublisher"]
    Evento["8: SolicitudContactoCreadaEvent"]
    Listener["9: SolicitudContactoCreadaListener"]
    Notificacion["10: NotificacionService"]
    NotificacionRepo["11: NotificacionRepository"]
    DB[("PostgreSQL")]

    Cliente -->|"POST /api/v1/contacto/mensaje"| API
    API -->|"enviarMensaje(...)"| Servicio
    Servicio -->|"validar producto y cantidad"| ProductoRepo
    ProductoRepo -->|"consultar"| DB
    Servicio -->|"crear pedido"| PedidoRepo
    PedidoRepo -->|"persistir"| DB
    Servicio -->|"guardar mensaje"| MensajeRepo
    MensajeRepo -->|"persistir"| DB
    Servicio -->|"publishEvent"| Publicador
    Publicador -->|"entregar evento antes del commit"| Evento
    Evento -->|"TransactionalEventListener"| Listener
    Listener -->|"registrar solicitud"| Notificacion
    Notificacion -->|"guardar"| NotificacionRepo
    NotificacionRepo -->|"persistir"| DB
    Servicio -->|"confirmación"| API
    API -->|"HTTP 200"| Cliente
```

## 6. Secuencia: calificación del servicio de transporte (HU-14)

El endpoint recibe la calificación de puntualidad y el estado de las cajas. El servicio carga el
viaje con bloqueo, comprueba que la identidad autenticada sea el comprador del pedido asociado y
deriva al conductor desde el vehículo asignado; el cliente no envía un identificador de conductor.
La calificación solo se acepta después de que HU-08 completa la transición a `ENTREGADA`. El
servicio carga el viaje con bloqueo, comprueba que la identidad autenticada sea el comprador del
pedido asociado y deriva al conductor desde el vehículo asignado; el cliente no envía un
identificador de conductor.

```mermaid
sequenceDiagram
    actor Comprador
    participant Filtro as JwtAuthenticationFilter
    participant API as CalificacionTransporteController
    participant Servicio as CalificacionTransporteService
    participant Ordenes as OrdenLogisticaRepository
    participant Calificaciones as CalificacionTransporteRepository
    participant DB as PostgreSQL

    Comprador->>Filtro: POST /api/v1/viajes/{id}/calificacion + JWT + métricas
    Filtro->>API: identidad autenticada
    API->>Servicio: registrar(viajeId, compradorId, puntualidad, estadoCajas)
    Servicio->>Ordenes: buscarPorIdParaActualizar(viajeId)
    Ordenes->>DB: cargar y bloquear orden logística
    DB-->>Ordenes: viaje, pedido y vehículo asignado
    Ordenes-->>Servicio: OrdenLogistica
    Servicio->>Servicio: validar compradorId == pedido.compradorId
    alt El comprador no coincide o el viaje no tiene pedido
        Servicio-->>API: OrdenNoEncontradaException
        API-->>Comprador: HTTP 404 Not Found
    else Comprador autorizado
        Servicio->>Servicio: validar ruta, conductor y duplicados
        Servicio->>Calificaciones: save(calificación con conductor del vehículo)
        Calificaciones->>DB: INSERT calificacion_transporte
        DB-->>Calificaciones: calificación persistida
        Calificaciones-->>Servicio: CalificacionTransporte
        Servicio-->>API: CalificacionTransporteResponseDTO
        API-->>Comprador: HTTP 201 Created
    end
```

## 7. Secuencia: consulta de reputación del agricultor (HU-15)

La consulta es pública y comprueba primero que el agricultor exista. Luego calcula el promedio
desde sus reseñas persistidas, lo redondea a dos decimales y devuelve `0.00` cuando todavía no
tiene reseñas. La prueba BDD de HU-15.3 cubre diez órdenes calificadas y verifica el promedio
resultante de `4.80`.

```mermaid
sequenceDiagram
    actor Visitante
    participant API as ReputacionController
    participant Servicio as ReputacionService
    participant Agricultores as AgricultorRepository
    participant Resenas as ResenaRepository
    participant DB as PostgreSQL

    Visitante->>API: GET /api/v1/agricultores/{id}/reputacion
    API->>Servicio: obtenerReputacion(agricultorId)
    Servicio->>Agricultores: existsById(agricultorId)
    Agricultores->>DB: comprobar agricultor
    DB-->>Agricultores: existe / no existe
    alt Agricultor inexistente
        Servicio-->>API: AgricultorNoEncontradoException
        API-->>Visitante: HTTP 404 Not Found
    else Agricultor existente
        Servicio->>Resenas: calcularPromedioPorAgricultor(agricultorId)
        Resenas->>DB: AVG(estrellas) de sus reseñas
        DB-->>Resenas: promedio o null
        Resenas-->>Servicio: promedio
        Servicio->>Servicio: redondear a dos decimales; null se convierte en 0.00
        Servicio-->>API: ReputacionResponseDTO
        API-->>Visitante: HTTP 200 OK
    end
```

## 8. Componentes de la arquitectura en capas

```mermaid
flowchart LR
    Cliente["Cliente HTTP"]
    Seguridad["Spring Security / JwtAuthenticationFilter"]
    Controladores["Presentación: controllers"]
    DTOs["Contratos REST: dtos"]
    Servicios["Negocio: services"]
    Modelos["Dominio y persistencia: models"]
    Repositorios["Acceso a datos: repositories"]
    Eventos["ApplicationEventPublisher"]
    Listeners["Listeners de eventos"]
    Estrategias["Estrategias de flete"]
    DB[("PostgreSQL")]

    Cliente -->|"HTTP / JSON"| Seguridad
    Seguridad --> Controladores
    Controladores --> DTOs
    Controladores --> Servicios
    Servicios --> Modelos
    Servicios --> Repositorios
    Repositorios --> DB
    Servicios --> Eventos
    Eventos --> Listeners
    Listeners --> Servicios
    Servicios --> Estrategias
```

## 9. Despliegue de staging

La aplicación y su base de datos de staging están desplegadas en Render. PostgreSQL también está
alojado en Render; no se usa Neon. El workflow ejecuta las verificaciones antes de invocar el Deploy
Hook.

```mermaid
flowchart LR
    Cliente["Cliente HTTP / Postman"]
    Repo["Repositorio GitHub: main"]
    CI["GitHub Actions: verify-and-test"]
    CD["GitHub Actions: deploy-to-staging"]
    Hook["Deploy Hook de Render"]
    App["Render Web Service: contenedor Docker / Java 17 / Spring Boot"]
    DB[("Render PostgreSQL")]

    Cliente -->|"HTTPS"| App
    Repo -->|"push a main"| CI
    CI -->|"si pasa"| CD
    CD -->|"DEPLOY_HOOK_URL"| Hook
    Hook -->|"iniciar despliegue"| App
    App -->|"JDBC / PostgreSQL"| DB
```

## Brechas frente a la guía

- **Ubicación:** la guía especifica `docs/architecture/`; los artefactos se mantienen en
  `docs/diseno/` por indicación de la docente.
- **Identificadores HU-12/HU-13:** la guía y el Backlog aprobado asignan historias distintas a esos
  números. Se mantienen los flujos reales del Backlog: HU-12 es rechazo de pago y HU-13 es
  calificación. HU-08, HU-13, HU-14 y HU-15 ya están implementadas en Sprint 4 con los alcances del
  Backlog; los escenarios de confirmación de carrito y cambio de despacho de la guía no son los
  flujos funcionales aprobados para HU-12 y HU-13.
- **Notificación de llegada:** la implementación actual persiste una notificación para el
  comprador después del commit y expone la bandeja autenticada; la Review conserva el corte previo
  al cierre técnico y su actualización posterior.
- **Despliegue:** se refleja Render con PostgreSQL en Render, no Neon.tech.
