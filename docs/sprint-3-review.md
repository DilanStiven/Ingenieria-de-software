# Sprint 3 Review — AgroValle Connect

> **Estado del documento:** actualizado el 17/11/2026 con HU-05 y HU-09 completadas. Los campos
> **Pendiente** se completan al cierre del Sprint (20/11/2026) con resultados reales; no se registran
> resultados, pruebas ni capturas que no hayan ocurrido.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 3 (13/11/2026 – 20/11/2026) |
| **Fecha de la Review** | Pendiente (20/11/2026) |
| **Asistentes** | Equipo de desarrollo y Docente (en el rol de Product Owner) |
| **Archivo** | `docs/sprint-3-review.md` |

---

## 1. Sprint Goal y resultado

> **Sprint Goal:** Entregar un incremento funcional en el que un comprador pueda contactar al agricultor y
> generar su pedido sobre una oferta, pagarlo con pasarela virtual (PSE/tarjeta) y descargar el
> comprobante, el agricultor reciba su pago de inmediato y vea su saldo en su billetera, y el comprador
> reciba un aviso si su pago es rechazado.

**Resultado:** Parcialmente cumplido (al 17/11/2026 se completaron HU-05 y HU-09; las historias HU-10, HU-11 y HU-12 están en curso).

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-05 — Contacto Directo / Intención de Compra | 3 | 3 | ✅ Done | PR #112 fusionado en `develop` el 17/11/2026 |
| HU-09 — Pago con Pasarela Virtual | 3 | 3 | ✅ Done | PR #118 fusionado en `develop` el 17/11/2026 (rama `historia-9`) |
| HU-10 — Comprobante de Pago y Factura | 2 | Pendiente | 🔄 En curso | Pendiente |
| HU-11 — Billetera del Agricultor | 2 | Pendiente | 🔄 En curso | Pendiente |
| HU-12 — Notificación de Pago Rechazado | 1 | Pendiente | 🔄 En curso | Pendiente |
| **Total** | **11** | **6** | | PR #112, PR #118 |

## 3. Demo del incremento

Entorno: `http://localhost:8080` (Spring Boot + PostgreSQL). Versión: 0.0.1-SNAPSHOT (17/11/2026).

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
| 1 | Descarga de recibo | «Descargar Recibo» de un pedido pagado | PDF con detalle y monto | Pendiente | Pendiente |
| 2 | Pago no exitoso (propuesto) | Recibo de pago rechazado | Error, sin PDF | Pendiente | Pendiente |

### 3.4 HU-11 — Billetera del Agricultor

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago acreditado y saldo | «Mi Billetera» tras un pago confirmado | Pago acreditado y saldo total | Pendiente | Pendiente |
| 2 | Sin ventas (propuesto) | «Mi Billetera» sin pagos | Saldo 0 | Pendiente | Pendiente |

### 3.5 HU-12 — Notificación de Pago Rechazado

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago rechazado | Pago con tarjeta sin fondos | Mensaje «Pago Rechazado» y pedido activo 15 min | Pendiente | Pendiente |
| 2 | Reintento (propuesto) | Nuevo pago antes de 15 min | Pago sobre el mismo pedido | Pendiente | Pendiente |

## 4. Calidad del incremento

| Verificación | Comando | Resultado | Evidencia |
| --- | --- | --- | --- |
| Pruebas automatizadas (BDD → JUnit 5) | `mvn test` | 89 pruebas ejecutadas, 0 fallos, 0 errores, 1 omitida — BUILD SUCCESS | Captura terminal 17/11/2026 (finalizó 16:38:50) |
| Estilo de código | `mvn checkstyle:check` | 0 Checkstyle violations — BUILD SUCCESS | Captura terminal 17/11/2026 16:40:42 |
| Pre-commit hooks (Husky) | Commit de prueba | Pendiente | Pendiente |

> **Nota:** los resultados corresponden a la ejecución del 17/11/2026 sobre el código de HU-09
> (PR #118), que incluye lo ya fusionado de HU-05. Al cierre de HU-05 se habían registrado 78 pruebas y
> 0 violaciones (14:10 y 14:11); HU-09 suma 11 pruebas. Las historias restantes se verificarán en sus
> respectivos PRs.

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-05 | 2,5 | 1,5 | −1 h | Completada el 17/11/2026; Carlos reportó 1 h 30 min reales |
| HU-09 | 2,5 | 2 | −0,5 h | Iniciada y terminada el 17/11/2026 (issue #41: Start date y Target date); Carlos reportó 2 h en el issue |
| HU-10 | 2 | Pendiente | Pendiente | Pendiente |
| HU-11 | 2 | Pendiente | Pendiente | Pendiente |
| HU-12 | 2 | Pendiente | Pendiente | Pendiente |
| **Total** | **11** | **3,5 (parcial)** | −1,5 h en las historias completadas (5 h planificadas vs 3,5 h reales) | Capacidad disponible: 24 h |

## 6. Trabajo no completado

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| HU-10, HU-11, HU-12 | Sprint en curso al 17/11/2026; fecha límite 20/11/2026 | Continuar en el Sprint; se completan antes del cierre. Ya no dependen de HU-09, que está en `develop` |

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Pendiente | Pendiente |

## 8. Actualización del Product Backlog

HU-05 y HU-09 pasan a **Done**. Las historias HU-10, HU-11 y HU-12 permanecen en **In Progress** hasta el
cierre del Sprint el 20/11/2026. Referencia: el Sprint 4 «Entrega y Reputación» (HU-08, HU-13, HU-14,
HU-15) queda sujeto a su Planning. HU-12 se movió a este Sprint desde el Sprint 4.

Ajustes pendientes en el Backlog para HU-09 (issue #41):

- Mover el issue de *Product Backlog* a *Done* en el tablero.
- Actualizar la redacción: la historia habla de «carrito», pero el pedido ahora nace en HU-05.
- Alinear la estimación: el cuerpo del issue indica 8 SP, mientras que el campo Estimate del tablero y el
  Planning usan 3 SP.

## 9. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- PR #112 (HU-05): [https://github.com/DilanStiven/Ingenieria-de-software/pull/112](https://github.com/DilanStiven/Ingenieria-de-software/pull/112)
- PR #118 (HU-09): [https://github.com/DilanStiven/Ingenieria-de-software/pull/118](https://github.com/DilanStiven/Ingenieria-de-software/pull/118)
- Issue #41 (HU-09): [https://github.com/DilanStiven/Ingenieria-de-software/issues/41](https://github.com/DilanStiven/Ingenieria-de-software/issues/41)
- Tablero (GitHub Projects): [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
- Planning: [`docs/sprint-3-planning.md`](sprint-3-planning.md)
- Retrospectiva: [`docs/sprint-3-retrospective.md`](sprint-3-retrospective.md)
