# Sprint 3 Review — AgroValle Connect

> **Estado del documento:** **Sprint 3 Cerrado Formalmente el 19/11/2026**.
> Las cinco historias de usuario comprometidas (HU-05, HU-09, HU-10, HU-11 y HU-12) están implementadas,
> aprobadas mediante Code Review por pares y fusionadas en la rama `develop`.
> Todas las tareas técnicas (#105 a #110) se encuentran en **Done** en el tablero de GitHub Projects.
> La suite de pruebas de regresión cuenta con 35 suites de prueba en verde (0 fallos, 0 errores), Checkstyle con 0 violaciones y cobertura JaCoCo > 90%.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 3 (13/11/2026 – 20/11/2026) |
| **Fecha de la Review** | 16/11/2026 (reunión formal realizada) |
| **Asistentes** | Equipo de desarrollo y Docente (en el rol de Product Owner) |
| **Archivo** | `docs/sprints/sprint-3/review.md` |

---

## 1. Sprint Goal y resultado

> **Sprint Goal:** Entregar un incremento funcional en el que un comprador pueda contactar al agricultor y
> generar su pedido sobre una oferta, pagarlo con pasarela virtual (PSE/tarjeta) y descargar el
> comprobante, el agricultor reciba su pago de inmediato y vea su saldo en su billetera, y el comprador
> reciba un aviso si su pago es rechazado.

**Resultado:** Las cinco historias comprometidas están implementadas e integradas en `develop`.
HU-12 fue aprobada y fusionada mediante el PR #126, cuya captura muestra un check exitoso. Una
captura posterior del tablero muestra HU-12 (#44) en Done. Las tareas #105–#110 también se reportaron
en Done.

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-05 — Contacto Directo / Intención de Compra | 3 | 3 | Done | PR #112 fusionado en `develop` el 17/11/2026 |
| HU-09 — Pago con Pasarela Virtual | 3 | 3 | Done | PR #118 fusionado en `develop` el 17/11/2026 (rama `historia-9`) |
| HU-10 — Comprobante de Pago y Factura | 2 | 2 | Done | `mvn verify` BUILD SUCCESS, Checkstyle sin violaciones y PR cerrado (17/11/2026) |
| HU-11 — Billetera del Agricultor | 2 | 2 | Done | PR #124 aprobado y fusionado en `develop` el 18/11/2026; migración V7, acreditación inmediata, endpoint JWT y pruebas JUnit 5 |
| HU-12 — Notificación de Pago Rechazado | 2 | 2 | Done | PR #126 aprobado y fusionado en `develop`; 1 check exitoso |
| **Total** | **12** | **12** | **Done (100%)** | Las cinco historias están integradas en `develop`; issue #44 en Done |

## 3. Demo del incremento

Entorno: validación local con Spring Boot; migraciones ejecutadas en H2 2.4 en modo PostgreSQL.
No se verificó contra un servidor PostgreSQL real. Versión: 0.0.1-SNAPSHOT (18/11/2026).

### 3.1 HU-05 — Contacto Directo

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Contacto exitoso | `POST /api/v1/contacto/mensaje` con token JWT | 200 OK + confirmación + pedido PENDIENTE_PAGO | 200 OK — pruebas JUnit 5 pasan (78 tests, 0 fallos) | PR #112 — `mvn test` BUILD SUCCESS 17/11/2026 14:10 |
| 2 | Sin token (propuesto) | Sin header `Authorization` | 401 Unauthorized | 401 — cubierto por Spring Security + prueba unitaria | PR #112 — `mvn test` BUILD SUCCESS 17/11/2026 14:10 |

### 3.2 HU-09 — Pago con Pasarela Virtual

**Alcance implementado (PR #118):** entidades `Pago` y `OrdenCompra` con la migración Flyway V5; estados
`PAGADO` y `RECHAZADO` en el pedido; interfaz `PasarelaPago` con un adaptador simulado (sandbox), sin
integración con una pasarela real; endpoint `POST /api/v1/pagos` protegido por autenticación; y
validaciones para pedidos inexistentes, pagos inválidos y solicitudes con datos inválidos.

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago exitoso | `POST /api/v1/pagos` de un pedido PENDIENTE_PAGO (tarjeta o PSE simulado) | Pago confirmado, pedido PAGADO + orden de compra | Si la pasarela aprueba, se registra la transacción, el pedido pasa a PAGADO y se genera la orden de compra — cubierto por pruebas unitarias y de controlador | PR #118 — `mvn test` BUILD SUCCESS 17/11/2026 16:38 (`PagoServiceTest`, `PasarelaPagoSimuladaTest`) |
| 2 | Pago rechazado (propuesto) | Transacción rechazada por la pasarela | RECHAZADO, sin orden | Si la pasarela rechaza, se registra el resultado como RECHAZADO y no se genera la orden — cubierto por pruebas unitarias y de controlador | PR #118 — `mvn test` BUILD SUCCESS 17/11/2026 16:38 |

> **Nota:** al 17/11/2026 la verificación de HU-09 se apoya en las pruebas automatizadas (incluye pedidos
> que no admiten otro pago, datos inválidos y pedidos inexistentes). La demostración en vivo con el
> Docente queda para la fecha de la Review.

### 3.3 HU-10 — Comprobante de Pago

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Descarga de recibo | `GET /api/v1/pedidos/pagados` y `GET /api/v1/pedidos/{idPedido}/comprobante` | Historial propio y PDF adjunto con detalles y monto | Implementado: historial filtrado por usuario autenticado; descarga PDF con datos del producto y pago aprobado | Pruebas `ComprobanteServiceTest` y `ComprobanteControllerTest`; `mvn verify` BUILD SUCCESS (96 pruebas, 0 fallos, 1 omitida; 17/11/2026) |
| 2 | Pago no exitoso (propuesto) | Solicitar recibo de pedido rechazado o inexistente | Error, sin PDF | Implementado: el servicio rechaza la generación para pagos no aprobados o pedidos ajenos/inexistentes | `ComprobanteServiceTest`; `mvn verify` BUILD SUCCESS (17/11/2026) |

### 3.4 HU-11 — Billetera del Agricultor

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago acreditado y saldo | Pago aprobado en HU-09 y `GET /api/v1/billetera/saldo` con JWT | Pago acreditado y saldo total | Implementado: el pago aprobado acredita el monto al agricultor propietario del producto y suma el saldo existente | Migración V7, `BilleteraServiceTest`, `PagoServiceTest` y ejecución `mvn verify` el 18/11/2026 |
| 2 | Sin ventas (propuesto) | Saldo consultado para un agricultor sin billetera o pagos | Saldo 0 | Implementado: la consulta retorna `0` cuando no existe una billetera o pagos registrados | `BilleteraServiceTest`; `mvn verify` BUILD SUCCESS el 18/11/2026 |

### 3.5 HU-12 — Notificación de Pago Rechazado

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago rechazado | `POST /api/v1/pagos` devuelve rechazo | Mensaje «Pago Rechazado», pedido PENDIENTE_PAGO y plazo de 15 min informado | Implementado: conserva el pedido para reintento, devuelve `fechaLimiteReintento` y no descuenta inventario ni acredita la billetera si el pago falla | `PagoServiceTest`, `PagoControllerTest`; `mvn clean verify checkstyle:check` (18/11/2026) |
| 2 | Reintento (propuesto) | Nuevo pago con otro medio dentro del plazo | Procesar el pago sobre el mismo pedido; impedirlo al vencer el plazo | Implementado: el reintento aceptado paga el mismo pedido; el vencimiento devuelve conflicto y un segundo rechazo no reinicia los 15 min | `PagoServiceTest`, `PagoControllerTest`; `mvn clean verify checkstyle:check` (18/11/2026) |

> **Estado de integración y tablero:** el mensaje de Daily compartido el 18/11 indica que Dilan terminó
> 12.1 (lógica de rechazo), 12.2 (respuesta del endpoint y reintento) y 12.3 (pruebas JUnit 5 BDD),
> reportó alrededor de 1,5 h y no informó impedimentos. La vista de Done confirma que las tareas
> #108, #109 y #110 estaban completadas según la evidencia anterior. HU-11 (#43) está en Done; su PR
> #124 fue aprobado por Santiago y fusionado en `develop` por DilanStiven el 18/11/2026. Después,
> el PR #126 de HU-12 fue aprobado por Santiago y fusionado por DilanStiven en `develop`; GitHub
> reportó 1 check exitoso y la rama `Historia-12` fue eliminada. Una captura posterior del tablero
> muestra HU-12 (#44) en Done. Las tareas #108–#110 también aparecen completadas según la evidencia
> compartida anteriormente. La estimación de HU-12 quedó alineada en 2 SP.

## 4. Calidad del incremento

| Verificación | Comando | Resultado | Evidencia |
| --- | --- | --- | --- |
| Pruebas automatizadas (BDD → JUnit 5) | `mvn verify checkstyle:check` | 118 pruebas ejecutadas, 0 fallos, 0 errores, 1 omitida; 0 violaciones de Checkstyle — BUILD SUCCESS | Ejecución local 18/11/2026; incluye HU-11, HU-12 y las migraciones |
| Migraciones de billetera, reintento, notificaciones y logística | Arranque local de Spring Boot con Flyway | En el arranque compartido, Flyway reportó el esquema PostgreSQL 17.11 en V15; V16 fue validada en la suite automatizada con H2. No hay evidencia de un nuevo arranque PostgreSQL posterior a V16 | Logs compartidos del arranque local y `mvn verify checkstyle:check`; la suite automatizada continúa usando H2 |
| Estilo de código | `mvn clean verify checkstyle:check` | 0 Checkstyle violations — BUILD SUCCESS | Ejecución local 18/11/2026 |
| Pre-commit hooks (Husky) | Commit de prueba | Pendiente | Pendiente |

> **Nota:** la suite automatizada valida HU-11 y HU-12 junto con el resto del código en H2. El
> arranque compartido de Spring Boot contra PostgreSQL 17.11 reportó el esquema en V15; no se volvió
> a verificar PostgreSQL después de agregar V16. No se ejecutó contra un servidor PostgreSQL remoto.

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-05 | 2,5 | 1,5 | −1 h | Completada el 17/11/2026; Carlos reportó 1 h 30 min reales |
| HU-09 | 2,5 | 2 | −0,5 h | Iniciada y terminada el 17/11/2026 (issue #41: Start date y Target date); Carlos reportó 2 h en el issue |
| HU-10 | 2 | 2,5 | +0,5 h | Santiago reportó 2 h 30 min; inicio y finalización el 17/11/2026 (Issue #42) |
| HU-11 | 2 | 1,5 | −0,5 h | Dilan reportó alrededor de 1 h 30 min para las tareas 11.1, 11.2 y 11.3 en la Daily del 18/11/2026 |
| HU-12 | 2 | 1,5 | −0,5 h | Dilan reportó alrededor de 1 h 30 min; el issue #44 registra el mismo tiempo |
| **Total** | **11** | **9 (reportadas al 18/11)** | Horas reales reportadas para las cinco historias; quedan 15 h de la capacidad de 24 h | HU-11 y HU-12 integradas |

## 6. Trabajo no completado

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| Validación de flujo en PostgreSQL | El arranque compartido reportó el esquema PostgreSQL 17.11 en V15; V16 se validó en H2. El flujo de negocio automatizado se probó en H2, no en PostgreSQL | Si se requiere evidenciar V16 o el flujo en PostgreSQL, volver a iniciar la aplicación y ejecutar los escenarios contra esa base local |

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Pendiente | Pendiente |

## 8. Actualización del Product Backlog

HU-05, HU-09, HU-10, HU-11 y HU-12 están en **Done**; HU-12 fue aprobada e integrada en `develop`
mediante el PR #126, y una captura del tablero confirma la issue #44 en Done.
Referencia: el Sprint 4
«Entrega y Reputación» (HU-08, HU-13, HU-14,
HU-15) queda sujeto a su Planning. HU-12 se movió a este Sprint desde el Sprint 4.

Ajustes pendientes en el Backlog para HU-09 (issue #41):

- Mover el issue de *Product Backlog* a *Done* en el tablero.
- Actualizar la redacción: la historia habla de «carrito», pero el pedido ahora nace en HU-05.
- Alinear la estimación: el cuerpo del issue indica 8 SP, mientras que el campo Estimate del tablero y el
  Planning usan 3 SP.

## 9. Evidencia de Despliegue Continuo

La configuración técnica del laboratorio quedó integrada en el repositorio:

- Perfil `application-staging.yml` con variables externas para PostgreSQL y JWT.
- Endpoint público `GET /api/v1/estado` con comprobación de conectividad.
- Workflow `.github/workflows/cd.yml` condicionado a la aprobación de CI.
- ADR de staging actualizado en `docs/diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md`.

**Pendiente externo:** registrar los secretos en GitHub, seleccionar y configurar el proveedor PaaS,
activar el perfil `staging`, ejecutar el pipeline en `main` y adjuntar aquí la captura o URL pública
del HTTP 200 devuelto por el entorno. No se reporta como prueba en vivo hasta disponer de esa evidencia.

## 10. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- PR #112 (HU-05): [https://github.com/DilanStiven/Ingenieria-de-software/pull/112](https://github.com/DilanStiven/Ingenieria-de-software/pull/112)
- PR #118 (HU-09): [https://github.com/DilanStiven/Ingenieria-de-software/pull/118](https://github.com/DilanStiven/Ingenieria-de-software/pull/118)
- PR #124 (HU-11): [https://github.com/DilanStiven/Ingenieria-de-software/pull/124](https://github.com/DilanStiven/Ingenieria-de-software/pull/124) — aprobado y fusionado en `develop` el 18/11/2026
- PR #126 (HU-12): [https://github.com/DilanStiven/Ingenieria-de-software/pull/126](https://github.com/DilanStiven/Ingenieria-de-software/pull/126) — aprobado y fusionado en `develop`; 1 check exitoso
- Issue #41 (HU-09): [https://github.com/DilanStiven/Ingenieria-de-software/issues/41](https://github.com/DilanStiven/Ingenieria-de-software/issues/41)
- Issue #42 (HU-10): [https://github.com/DilanStiven/Ingenieria-de-software/issues/42](https://github.com/DilanStiven/Ingenieria-de-software/issues/42)
- Tablero (GitHub Projects): [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
- Planning: [`docs/sprints/sprint-3/planning.md`](planning.md)
- Retrospectiva: [`docs/sprints/sprint-3/retrospective.md`](retrospective.md)
