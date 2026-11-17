# Sprint 3 Review — AgroValle Connect

> **Estado del documento:** plantilla preparada el 16/11/2026. Los campos **Pendiente** se completan
> al cierre del Sprint (20/11/2026) con resultados reales; no se registran resultados, pruebas ni
> capturas que no hayan ocurrido.

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

**Resultado:** Parcialmente cumplido (al 17/11/2026 se completó HU-05; las historias HU-09, HU-10, HU-11 y HU-12 están en curso).

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-05 — Contacto Directo / Intención de Compra | 3 | 3 | ✅ Done | PR #112 fusionado en `develop` el 17/11/2026 |
| HU-09 — Pago con Pasarela Virtual | 3 | Pendiente | 🔄 En curso | Pendiente |
| HU-10 — Comprobante de Pago y Factura | 2 | Pendiente | 🔄 En curso | Pendiente |
| HU-11 — Billetera del Agricultor | 2 | Pendiente | 🔄 En curso | Pendiente |
| HU-12 — Notificación de Pago Rechazado | 1 | Pendiente | 🔄 En curso | Pendiente |
| **Total** | **11** | **3** | | PR #112 |

## 3. Demo del incremento

Entorno: `http://localhost:8080` (Spring Boot + PostgreSQL). Versión: 0.0.1-SNAPSHOT (17/11/2026).

### 3.1 HU-05 — Contacto Directo

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Contacto exitoso | `POST /api/v1/contacto/mensaje` con token JWT | 200 OK + confirmación + pedido PENDIENTE_PAGO | 200 OK — pruebas JUnit 5 pasan (78 tests, 0 fallos) | PR #112 — `mvn test` BUILD SUCCESS 17/11/2026 14:10 |
| 2 | Sin token (propuesto) | Sin header `Authorization` | 401 Unauthorized | 401 — cubierto por Spring Security + prueba unitaria | PR #112 — `mvn test` BUILD SUCCESS 17/11/2026 14:10 |

### 3.2 HU-09 — Pago con Pasarela Virtual

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Pago exitoso | Pago por tarjeta o PSE de un pedido PENDIENTE_PAGO | Pago confirmado, pedido PAGADO + orden de compra | Pendiente | Pendiente |
| 2 | Pago rechazado (propuesto) | Transacción rechazada | RECHAZADO, sin orden | Pendiente | Pendiente |

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
| Pruebas automatizadas (BDD → JUnit 5) | `mvn test` | 78 pruebas ejecutadas, 0 fallos, 0 errores, 1 omitida — BUILD SUCCESS | Captura terminal 17/11/2026 14:10:36 |
| Estilo de código | `mvn checkstyle:check` | 0 Checkstyle violations — BUILD SUCCESS | Captura terminal 17/11/2026 14:11:25 |
| Pre-commit hooks (Husky) | Commit de prueba | Pendiente | Pendiente |

> **Nota:** los resultados de `mvn test` y `mvn checkstyle:check` corresponden al estado del repositorio
> tras fusionar PR #112 (HU-05). Las historias restantes se verificarán en sus respectivos PRs.

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-05 | 2,5 | 1,5 | −1 h | Completada el 17/11/2026; Carlos reportó 1 h 30 min reales |
| HU-09 | 2,5 | Pendiente | Pendiente | Pendiente |
| HU-10 | 2 | Pendiente | Pendiente | Pendiente |
| HU-11 | 2 | Pendiente | Pendiente | Pendiente |
| HU-12 | 2 | Pendiente | Pendiente | Pendiente |
| **Total** | **11** | **1,5 (parcial)** | Pendiente | Capacidad disponible: 24 h |

## 6. Trabajo no completado

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| HU-09, HU-10, HU-11, HU-12 | Sprint en curso al 17/11/2026; fecha límite 20/11/2026 | Continuar en el Sprint; se completan antes del cierre |

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Pendiente | Pendiente |

## 8. Actualización del Product Backlog

HU-05 pasa a **Done**. Las historias HU-09, HU-10, HU-11 y HU-12 permanecen en **In Progress** hasta el
cierre del Sprint el 20/11/2026. Referencia: el Sprint 4 «Entrega y Reputación» (HU-08, HU-13, HU-14,
HU-15) queda sujeto a su Planning. HU-12 se movió a este Sprint desde el Sprint 4.

## 9. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- PR #112 (HU-05): [https://github.com/DilanStiven/Ingenieria-de-software/pull/112](https://github.com/DilanStiven/Ingenieria-de-software/pull/112)
- Tablero (GitHub Projects): [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
- Planning: [`docs/sprint-3-planning.md`](sprint-3-planning.md)
- Retrospectiva: [`docs/sprint-3-retrospective.md`](sprint-3-retrospective.md)
