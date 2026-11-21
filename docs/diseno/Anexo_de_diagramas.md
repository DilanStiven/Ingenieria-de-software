# Anexo de diagramas

Sprint de diseño: modelo de datos, arquitectura y prototipado

Ingeniería de Software II \| Proyecto AgroValle \| Estudiante(s): Grupo 5

> **Nota de alcance:** estos diagramas son artefactos del diseño académico inicial. El modelo
> físico, el MER y el modelo de dominio representan cinco entidades (`Municipio`, `Agricultor`,
> `Producto`, `Cosecha` y `Oferta`) que no corresponden literalmente al modelo JPA actual. El
> diagrama de despliegue y las capturas son referencias de diseño, no fuente de verdad para el
> esquema vigente. El modelo JPA implementado actualmente tiene `Agricultor` y `Producto` y usa
> Java 17. La vista actual y su DDL están documentados en
> [`Documento_de_normalizacion.md`](Documento_de_normalizacion.md) y [`schema.sql`](schema.sql).

## Índice de diagramas

| **N.º** | **Diagrama**                | **Qué representa**                                             | **Origen**         |
|---------|-----------------------------|----------------------------------------------------------------|--------------------|
| 1       | Captura de pgAdmin (ERD)    | Referencia histórica del modelo académico de cinco tablas; no acredita el esquema actual | Captura del equipo |
| 2       | Modelo físico (referencia)  | Diseño propuesto de cinco tablas con tipos, PK, FK y UK        | Generado           |
| 3       | MER conceptual              | Diseño académico propuesto, previo a implementación            | Generado           |
| 4       | Modelo de dominio UML       | Diseño académico propuesto; no son cinco entidades JPA vigentes | Generado           |
| 5       | Secuencia: Registrar oferta | Recorrido de una petición por las capas del sistema            | Generado           |
| 6       | Componentes                 | Paquetes del sistema y sus dependencias                        | Generado           |
| 7       | Despliegue                  | Nodos físicos donde corre el sistema                           | Generado           |
| 8       | Flujo de pantallas          | Navegación entre las 6 pantallas                               | Generado           |
| 9 a 14  | Mockups (6 pantallas)       | Wireframes de baja fidelidad de cada pantalla                  | Generado           |

## Punto 1. Modelo de datos

### Diagrama 1. Captura de pgAdmin (ERD de la base de datos)

**Qué es:** captura de referencia compartida por el equipo, identificada como PostgreSQL 18.
Representa el modelo académico de cinco tablas; no demuestra el esquema de la aplicación
actualmente configurada en el repositorio.

**Uso y limitación:** sirve como referencia visual del diseño propuesto. No se presenta como
evidencia de que el script se ejecutó correctamente ni de que esas cinco tablas existan en la
base desplegada.

![Figura 1. Captura de pgAdmin (ERD de la base de datos)](images/diagrama-01-pgadmin-erd.png)

*Figura 1. Captura de pgAdmin (ERD de la base de datos)*

### Diagrama 2. Modelo físico (diagrama de referencia)

**Qué es:** representación del mismo modelo con los tipos de dato y las restricciones: PK (llave primaria), FK (llave foránea) y UK (valor único).

**Para qué sirve:** documentar la estructura exacta de cada tabla, y ver de un vistazo cómo se enlazan las llaves foráneas.

![Figura 2. Modelo físico (diagrama de referencia)](images/diagrama-02-modelo-fisico.png)

*Figura 2. Modelo físico (diagrama de referencia)*

### Diagrama 3. MER conceptual

**Qué es:** modelo entidad-relación en su nivel conceptual: muestra las entidades, sus atributos y las relaciones uno a muchos, sin llaves foráneas.

**Para qué sirve:** representar el negocio antes de pensar en tablas; es el diagrama que se hace primero, antes de normalizar.

![Figura 3. MER conceptual](images/diagrama-03-mer-conceptual.png)

*Figura 3. MER conceptual*

### Diagrama 4. Modelo de dominio UML

**Qué es:** diagrama de clases con las cinco entidades, sus atributos con tipos Java y las asociaciones con su multiplicidad (1 a 0..\*).

**Para qué sirve:** mostrar cómo se ve el modelo de datos desde la programación orientada a objetos; es la base de las entidades JPA.

![Figura 4. Modelo de dominio UML](images/diagrama-04-modelo-dominio-uml.png)

*Figura 4. Modelo de dominio UML*

### Modelo relacional actualmente implementado

Este DER representa las dos entidades JPA vigentes y la relación de propiedad de los productos.
Las cinco entidades de los diagramas anteriores se conservan como propuesta académica.

```mermaid
erDiagram
    AGRICULTOR ||--o{ PRODUCTOS : publica
    AGRICULTOR {
        bigint id PK
        varchar nombre
        varchar ubicacion_valle
        varchar cedula UK
    }
    PRODUCTOS {
        bigint id PK
        varchar nombre
        varchar categoria
        varchar municipio
        numeric cantidad
        numeric precio_unitario
        date fecha_cosecha
        varchar estado
        bigint agricultor_id FK
    }
```

## Punto 2. Arquitectura de software

### Diagrama 5. Secuencia: Registrar oferta

**Qué es:** diagrama de secuencia del caso de uso "Registrar oferta", desde el agricultor hasta la base de datos, pasando por vista, controlador, servicio y repositorios.

**Para qué sirve:** explicar el orden de las llamadas entre las capas y qué mensaje intercambia cada una.

![Figura 5. Secuencia: Registrar oferta](images/diagrama-05-secuencia-registrar-oferta.png)

*Figura 5. Secuencia: Registrar oferta*

### Diagrama 6. Componentes

**Qué es:** diagrama de los paquetes de la arquitectura MVC en capas (controller, service, repository, dto, mapper y model) y de quién depende cada uno.

**Para qué sirve:** mostrar la estructura estática del sistema y comprobar que cada capa solo se comunica con la contigua.

![Figura 6. Componentes](images/diagrama-06-componentes.png)

*Figura 6. Componentes*

### Diagrama 7. Despliegue

**Qué es:** diagrama de los tres nodos físicos: navegador del usuario, servidor de aplicaciones (Spring Boot) y servidor de base de datos (PostgreSQL), con sus protocolos y puertos.

**Para qué sirve:** indicar dónde se ejecuta cada pieza del sistema y cómo se comunican entre sí.

![Figura 7. Despliegue](images/diagrama-07-despliegue.png)

*Figura 7. Despliegue*

## Punto 3. Prototipado

### Diagrama 8. Flujo de pantallas

**Qué es:** diagrama de navegación entre las seis pantallas del sistema, con la acción que lleva de una a otra.

**Para qué sirve:** validar con el Product Owner el recorrido completo del usuario.

![Figura 8. Flujo de pantallas](images/diagrama-08-flujo-pantallas.png)

*Figura 8. Flujo de pantallas*

### Diagrama 9. Mockup: Login

**Qué es:** wireframe de la pantalla de inicio de sesión.

**Para qué sirve:** validar los campos de acceso y el enlace al registro.

![Figura 9. Mockup: Login](images/diagrama-09-mockup-login.png)

*Figura 9. Mockup: Login*

### Diagrama 10. Mockup: Registro de agricultor

**Qué es:** wireframe del formulario de registro de un nuevo agricultor.

**Para qué sirve:** validar los datos que se piden al crear la cuenta.

![Figura 10. Mockup: Registro de agricultor](images/diagrama-10-mockup-registro-agricultor.png)

*Figura 10. Mockup: Registro de agricultor*

### Diagrama 11. Mockup: Panel principal

**Qué es:** wireframe de la pantalla de inicio del agricultor, con las tres opciones principales y sus últimas cosechas.

**Para qué sirve:** validar el menú de acceso a las funciones del sistema.

![Figura 11. Mockup: Panel principal](images/diagrama-11-mockup-panel-principal.png)

*Figura 11. Mockup: Panel principal*

### Diagrama 12. Mockup: Registrar cosecha

**Qué es:** wireframe del formulario para registrar una cosecha.

**Para qué sirve:** validar los campos de producto, cantidad y fecha.

![Figura 12. Mockup: Registrar cosecha](images/diagrama-12-mockup-registrar-cosecha.png)

*Figura 12. Mockup: Registrar cosecha*

### Diagrama 13. Mockup: Publicar oferta

**Qué es:** wireframe del formulario para publicar una cosecha como oferta de venta.

**Para qué sirve:** validar los campos de precio y cantidad disponible.

![Figura 13. Mockup: Publicar oferta](images/diagrama-13-mockup-publicar-oferta.png)

*Figura 13. Mockup: Publicar oferta*

### Diagrama 14. Mockup: Lista de ofertas

**Qué es:** wireframe de la pantalla que lista las ofertas con filtros y paginación.

**Para qué sirve:** validar los filtros y las columnas de la tabla.

![Figura 14. Mockup: Lista de ofertas](images/diagrama-14-mockup-lista-ofertas.png)

*Figura 14. Mockup: Lista de ofertas*

## Diagramas contrastados con la implementacion actual

Los siguientes diagramas describen el código presente en el repositorio, no todos los detalles de
las capturas de referencia. Los flujos de publicación y pagos tienen implementaciones equivalentes,
pero usan el modelo JPA vigente (`Agricultor`, `Producto`, `Pedido` y `OrdenLogistica`) en vez de las
entidades académicas `Finca`, `Cosecha`, `Oferta` y `Carrito`. Las imágenes de componentes y despliegue
compartidas se documentan con sus diferencias respecto al código.

### Flujo implementado: publicar producto (HU-02)

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart TD
    H2Client["Cliente HTTP"]
    H2Jwt["JwtAuthenticationFilter valida Bearer JWT"]
    H2Controller["ProductoController POST /api/v1/productos"]
    H2Service["ProductoService valida fecha no anterior a hoy"]
    H2FarmerRepo["AgricultorRepository carga agricultor del token"]
    H2ProductRepo["ProductoRepository guarda producto"]
    H2DB[("PostgreSQL tabla productos")]
    H2Response["HTTP 201 ProductoResponseDTO"]
    H2Event["ProductoPublicadoEvent"]
    H2Listener["ProductoPublicadoListener registra evento"]

    H2Client -->|"token y datos del producto"| H2Jwt
    H2Jwt -->|"identidad autenticada"| H2Controller
    H2Controller -->|"delegacion"| H2Service
    H2Service -->|"valida propietario"| H2FarmerRepo
    H2FarmerRepo -->|"consulta JPA"| H2DB
    H2Service -->|"persiste producto"| H2ProductRepo
    H2ProductRepo -->|"INSERT"| H2DB
    H2ProductRepo -->|"producto guardado"| H2Service
    H2Service -->|"publica evento"| H2Event
    H2Event -->|"notifica observador"| H2Listener
    H2Service -->|"respuesta creada"| H2Response
    H2Response -->|"HTTP 201"| H2Client
~~~

**Diferencia con la captura:** la validación de propiedad consulta `AgricultorRepository` usando el ID
del JWT; no existe `FincaRepository`. La persistencia escribe en `productos`, no en `ofertas_cosecha`.
La fecha se valida para que no sea anterior al día actual. El evento del producto se registra en log;
no crea una oferta separada.

### Flujo implementado: contacto, pago y orden logística

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart TD
    PClient["Cliente API"]
    PContactController["ContactoController POST /api/v1/contacto/mensaje"]
    PContactService["ContactoService valida producto y cantidad"]
    POrder["PedidoRepository crea pedido pendiente"]
    PMessage["ContactoMensajeRepository guarda mensaje"]
    PEvent["SolicitudContactoCreadaEvent"]
    PListener["SolicitudContactoCreadaListener"]
    PNotification["NotificacionService guarda notificacion"]
    PNotificationRepo["NotificacionRepository"]
    PContactResponse["HTTP 200 confirmacion"]
    PPaymentController["PagoController POST /api/v1/pagos"]
    PPaymentService["PagoService procesa pago"]
    PGateway["PasarelaPagoSimulada"]
    PApproved["Pago aprobado"]
    PRejected["Pago rechazado"]
    PStock["Descuenta inventario"]
    PPurchase["Guarda Pago y OrdenCompra"]
    PLogistics["OrdenLogisticaService crea orden"]
    PWallet["BilleteraService acredita agricultor"]
    PRetry["Conserva pedido y fecha limite de 15 minutos"]
    PDB[("PostgreSQL")]

    PClient -->|"solicita contacto"| PContactController
    PContactController -->|"delegacion"| PContactService
    PContactService -->|"guarda"| POrder
    PContactService -->|"guarda"| PMessage
    PContactService -->|"publica evento"| PEvent
    PEvent -->|"transaccion antes del commit"| PListener
    PListener -->|"registra"| PNotification
    PNotification -->|"persiste"| PNotificationRepo
    PContactService -->|"confirmacion"| PContactResponse
    PContactResponse -->|"HTTP 200"| PClient
    PClient -->|"inicia pago del pedido"| PPaymentController
    PPaymentController -->|"delegacion"| PPaymentService
    PPaymentService -->|"solicita resultado"| PGateway
    PGateway -->|"aprobado"| PApproved
    PGateway -->|"rechazado"| PRejected
    PApproved -->|"actualiza"| PStock
    PApproved -->|"registra"| PPurchase
    PApproved -->|"crea alistamiento"| PLogistics
    PApproved -->|"acredita"| PWallet
    PRejected -->|"mantiene para reintento"| PRetry
    PRetry -->|"devuelve fecha limite"| PClient
    POrder -->|"JPA"| PDB
    PMessage -->|"JPA"| PDB
    PNotificationRepo -->|"JPA"| PDB
    PPaymentService -->|"JPA"| PDB
~~~

**Diferencia con la captura:** el código no tiene `CarritoRepo`, `InventarioService` ni un controlador
de orden de compra que confirme el carrito. `OrdenLogisticaController` existe para alistamiento y
rutas, no para confirmar compras. El pedido se crea al enviar el contacto y pasa a pagado solo cuando
el servicio de pago aprueba. En el rechazo se informa el plazo de reintento; esta captura no representa
ese contrato. El modelo actual tampoco define un rol de comprador separado.

### Eventos Observer que existen

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart TD
    EContact["ContactoService"]
    EContactEvent["SolicitudContactoCreadaEvent"]
    EContactListener["SolicitudContactoCreadaListener"]
    ENotification["NotificacionService"]
    ENotificationRepo["NotificacionRepository"]
    EProduct["ProductoService"]
    EProductEvent["ProductoPublicadoEvent"]
    EProductListener["ProductoPublicadoListener"]
    ELog["Log de aplicacion"]
    EDB[("PostgreSQL")]

    EContact -->|"publica"| EContactEvent
    EContactEvent -->|"TransactionalEventListener antes del commit"| EContactListener
    EContactListener -->|"persiste bandeja"| ENotification
    ENotification -->|"guarda"| ENotificationRepo
    ENotificationRepo -->|"JPA"| EDB
    EProduct -->|"publica"| EProductEvent
    EProductEvent -->|"EventListener"| EProductListener
    EProductListener -->|"registra en log"| ELog
~~~

**Diferencia con la captura HU-13:** no se encontró un endpoint para cambiar el estado de una orden
con `PATCH /ordenes/:id/estado` ni un evento que persista una notificacion por cambio de estado. El
listener de producto solo registra el evento; las notificaciones persistidas corresponden a nuevas
solicitudes de contacto.

### Arquitectura y despliegue

El diagrama actualizado de capas y dependencias esta en
[`architecture-diagram.md`](../../.github/modernize/assessment/engines/facts/architecture-diagram.md).
El diagrama de componentes está en ese archivo. La topología siguiente distingue el entorno local y
la integración continua de un despliegue cloud, que todavía no está configurado.

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart LR
    subgraph DEPDev["Desarrollo local"]
        DEPClient["Cliente HTTP"]
        DEPApp["Spring Boot Java 17"]
        DEPDatabase[("PostgreSQL local")]
    end
    subgraph DEPCI["Integracion continua"]
        DEPRepo["Repositorio GitHub"]
        DEPWorkflow["GitHub Actions"]
        DEPBuild["Maven build tests Checkstyle JaCoCo"]
    end
    DEPCloud["Render Railway Neon Supabase no configurados"]

    DEPClient -->|"HTTP local"| DEPApp
    DEPApp -->|"JDBC local"| DEPDatabase
    DEPRepo -->|"push o pull request"| DEPWorkflow
    DEPWorkflow -->|"compila y verifica"| DEPBuild
~~~

La imagen de despliegue compartida (Render/Railway y Neon/Supabase) es una propuesta. El repositorio
actual configura Spring Boot con PostgreSQL por variables de entorno y CI en GitHub Actions, pero no
tiene Dockerfile, perfil de staging ni despliegue cloud configurado. No se debe presentar la nube
como un entorno ya desplegado.
