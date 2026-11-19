# Sprint 3 Planning — AgroValle Connect

> **Alcance de este artefacto:** registra el plan al inicio del Sprint; los nombres de clases, tablas y
> tareas pueden diferir de la implementación final. Para el resultado reportado, consultar
> [Sprint 3 Review](sprint-3-review.md).

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Asignatura** | Ingeniería de Software II — UNIAJC |
| **Período / Docente** | 2-2026 / Paola Andrea Bedoya Toro |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Duración del Sprint** | 13/11/2026 – 20/11/2026 (Sprint 3 «Pedido y Pago») |
| **Fecha de la reunión de Planning** | 16/11/2026 |
| **Reparto de historias y tareas** | 16/11/2026 |
| **Archivo** | `docs/sprint-3-planning.md` |
| **Norma de calidad** | ISO/IEC 25010:2011 |

---

## 1. Sprint Goal

> **Entregar un incremento funcional en el que un comprador pueda contactar al agricultor y generar su
> pedido sobre una oferta, pagarlo con pasarela virtual (PSE/tarjeta) y descargar el comprobante, el
> agricultor reciba su pago de inmediato y vea su saldo en su billetera, y el comprador reciba un aviso
> si su pago es rechazado.**

Al finalizar el Sprint 3 el sistema permitirá:

1. Enviar un mensaje de contacto con `POST /api/v1/contacto/mensaje` y generar el pedido en estado PENDIENTE_PAGO (HU-05).
2. Pagar un pedido con PSE o tarjeta, pasarlo a PAGADO y generar la orden de compra (HU-09).
3. Descargar el comprobante de pago en PDF (HU-10).
4. Acreditar el pago al agricultor apenas se confirma y consultar su saldo en «Mi Billetera» (HU-11).
5. Mostrar «Pago Rechazado» y mantener el pedido activo 15 minutos para reintentar (HU-12).

---

## 2. Capacidad del Equipo y Selección

### 2.1 Capacidad

| Métrica | Valor |
| --- | --- |
| Velocidad estimada | **12 Story Points** |
| Integrantes | 4 |
| Dedicación | 6 h por persona por semana (igual que Sprint 1) |
| **Capacidad total** | 4 × 6 h × 1 semana = **24 h** |
| Horas planificadas en tareas | **11 h** (≈ 46 % de la capacidad); 30 min por tarea y 1 h en las pruebas, con apoyo de GitHub Copilot |
| Reserva para ceremonias e imprevistos | **13 h** (≈ 54 %): Daily Scrums, Review, Retrospectiva, revisión de PRs y correcciones |
| Historias seleccionadas | 5 (HU-05, HU-09, HU-10, HU-11, HU-12) |
| Stack | Java 17, Spring Boot, Spring Security + JWT, PostgreSQL, JUnit 5, Mockito, Checkstyle |

### 2.2 Historias seleccionadas

| ID | Historia de Usuario | Prioridad | SP | Tareas | Horas |
| --- | --- | --- | --- | --- | --- |
| HU-05 | Contacto Directo / Intención de Compra | Should Have | 3 | 4 | 2,5 h |
| HU-09 | Pago de Pedido con Pasarela Virtual (PSE/Tarjetas) | Must Have | 3 | 4 | 2,5 h |
| HU-10 | Generación de Comprobante de Pago y Factura | Should Have | 2 | 3 | 2 h |
| HU-11 | Visualización de Saldo (Billetera del Agricultor) | Must Have | 2 | 3 | 2 h |
| HU-12 | Notificación de Pago Rechazado | Could Have | 2 | 3 | 2 h |
| — | **Total** | | **12** | **17** | **11 h** |

> **Nota:** HU-12 se movió del Sprint 4 a este Sprint y conserva su estimación de 2 SP.
> El Sprint 4 queda con HU-08, HU-13, HU-14 y HU-15 (9 SP).

### 2.3 Asignación de responsables (propuesta, 16/11/2026)

| Integrante | Tareas | Horas |
| --- | --- | --- |
| Kevin Estiven Lucumi Polo | HU-05: 5.1, 5.2, 5.3, 5.4 | 2,5 h |
| Carlos Andres Rosales Lara | HU-09: 9.1, 9.2, 9.3, 9.4 | 2,5 h |
| Santiago Edilmo Hurtado Cuenu | HU-10: 10.1, 10.2, 10.3 | 2 h |
| Dilan Stiven Castillo Valencia | HU-11 y HU-12: 11.1, 11.2, 11.3, 12.1, 12.2, 12.3 | 4 h |
| **Total** | 17 tareas | **11 h** |

Cada integrante es responsable de una historia completa (Dilan, de HU-11 y HU-12). Puede reajustarse en la Daily Scrum según el avance. La auditoría de Checkstyle se hace antes de cada
Pull Request (`mvn checkstyle:check`), no como tarea aparte.

### 2.4 Orden de ejecución y dependencias

```
5.1 → 5.2 → 5.3 → 5.4                            (HU-05)
9.1 → 9.2 → 9.3 → 9.4                            (HU-09)
        ├→ 10.1 → 10.2 → 10.3                    (HU-10)
        ├→ 11.1 → 11.2 → 11.3                    (HU-11)
        └→ 12.1 → 12.2 → 12.3                    (HU-12)
```

- HU-05 depende de la oferta activa (HU-02) y de la autenticación JWT (Sprint 1).
- HU-09 depende del pedido creado en HU-05.
- HU-10, HU-11 y HU-12 dependen de HU-09.

---

## 3. HU-05 — Contacto Directo / Intención de Compra (3 SP)

**Historia:** Como Comprador, quiero enviar una solicitud de contacto directo al agricultor y generar mi
pedido sobre su oferta, para acordar condiciones de compra y logística y dejar la compra lista para pagar.

**Escenarios BDD** (el 1 es el aprobado en el Backlog; el 2 es propuesto)

```gherkin
Escenario 1 — Contacto exitoso
Given un comprador autenticado con token JWT y una oferta activa registrada con id_producto, cuya
      intención de compra genera un pedido en estado PENDIENTE_PAGO
When  envía una petición POST a /api/v1/contacto/mensaje con el id_producto y el mensaje de negociación
Then  el sistema persiste la interacción en PostgreSQL y retorna status 200 OK con la confirmación
      de notificación enviada

Escenario 2 (propuesto) — Sin token
Given que la petición no incluye el header Authorization
When  se hace POST a /api/v1/contacto/mensaje
Then  el sistema responde 401 Unauthorized
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 5.1 | Entidades `ContactoMensaje` y `Pedido` y migración Flyway `V4__create_contacto.sql` | 0,5 | Kevin | Mantenibilidad → Modificabilidad |
| 5.2 | `ContactoService`: validar oferta activa, persistir mensaje, crear pedido PENDIENTE_PAGO y confirmar notificación | 0,5 | Kevin | Adecuación Funcional → Corrección |
| 5.3 | `ContactoController`: `POST /api/v1/contacto/mensaje` protegido con JWT | 0,5 | Kevin | Compatibilidad → Interoperabilidad |
| 5.4 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Kevin | Mantenibilidad → Capacidad de ser probado |

---

## 4. HU-09 — Pago de Pedido con Pasarela Virtual (3 SP)

**Historia:** Como comerciante, quiero pagar mi carrito de compras utilizando una pasarela de pagos,
para asegurar mi pedido de forma rápida y sin usar efectivo.

**Escenarios BDD** (el 1 es el aprobado, con el Given y el Then ajustados al pedido; el 2 es propuesto)

```gherkin
Escenario 1 — Pago exitoso
Given que el comerciante tiene un pedido en estado PENDIENTE_PAGO y está en la pantalla de pago
When  ingresa una tarjeta de crédito válida o aprueba la transacción por PSE
Then  el sistema descuenta el dinero, confirma el pago exitoso, cambia el pedido a PAGADO y genera la
      orden de compra oficial

Escenario 2 (propuesto) — Pago rechazado
Given que el comerciante está en la pantalla de pago
When  la pasarela rechaza la transacción
Then  el sistema registra el pago como RECHAZADO y no genera la orden de compra
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 9.1 | Entidades `Pago` y `OrdenCompra` y migración Flyway `V5__create_pagos_ordenes.sql` | 0,5 | Carlos | Fiabilidad → Madurez |
| 9.2 | Interfaz `PasarelaPago` y adaptador simulado (sandbox) | 0,5 | Carlos | Mantenibilidad → Modularidad |
| 9.3 | `PagoService` y `PagoController` (pago, pedido a PAGADO y orden de compra) | 0,5 | Carlos | Seguridad → Integridad |
| 9.4 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Carlos | Mantenibilidad → Capacidad de ser probado |

---

## 5. HU-10 — Generación de Comprobante de Pago y Factura (2 SP)

**Historia:** Como comerciante, quiero poder descargar un comprobante digital en PDF de mi transacción,
para llevar el control contable de mi negocio.

**Escenarios BDD** (el 1 es el aprobado; el 2 es propuesto)

```gherkin
Escenario 1 — Descarga de recibo
Given que el sistema procesó un pago exitosamente
When  el comerciante accede al historial de sus pedidos pagados y selecciona "Descargar Recibo"
Then  el sistema genera y descarga un archivo PDF con los detalles de la compra y el monto pagado

Escenario 2 (propuesto) — Pago no exitoso
Given un pago rechazado o inexistente
When  el comerciante solicita su recibo
Then  el sistema no genera el PDF y responde con error
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 10.1 | `ComprobanteService`: generar el PDF con detalles y monto | 0,5 | Santiago | Adecuación Funcional → Completitud |
| 10.2 | Endpoint de historial de pedidos pagados y descarga de recibo | 0,5 | Santiago | Usabilidad → Operabilidad |
| 10.3 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Santiago | Mantenibilidad → Capacidad de ser probado |

**Implementación HU-10:** Apache PDFBox 3.0.5 genera el archivo PDF. El historial se consulta en
`GET /api/v1/pedidos/pagados` y el recibo se descarga en
`GET /api/v1/pedidos/{idPedido}/comprobante`. Ambos endpoints usan el ID del usuario autenticado
en el JWT; el pedido lo conserva desde su creación (migración `V6__associate_pedido_comprador.sql`).
Los pagos rechazados o los pedidos de otro usuario responden como no encontrados. La migración
`V6` deja `V7` para la billetera de HU-11.

---

## 6. HU-11 — Visualización de Saldo (Billetera del Agricultor) (2 SP)

**Historia:** Como agricultor, quiero recibir mi pago apenas el comprador paga y ver el saldo acumulado
de mis ventas pagadas, para poder solicitar el desembolso del dinero a mi cuenta bancaria.

**Escenarios BDD** (el 1 es el aprobado, con el Given ajustado; el 2 es propuesto)

```gherkin
Escenario 1 — Pago acreditado y saldo
Given que el comerciante completó el pago de un pedido (HU-09) y se generó su comprobante (HU-10)
When  el agricultor ingresa a la sección "Mi Billetera" en su perfil
Then  el sistema muestra el pago acreditado de inmediato y suma el saldo total disponible para retirar

Escenario 2 (propuesto) — Sin ventas
Given un agricultor sin pagos recibidos
When  ingresa a "Mi Billetera"
Then  el sistema muestra saldo 0
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 11.1 | `BilleteraService`: acreditar al agricultor el pago apenas se confirma (HU-09), con migración `V7__create_billetera.sql`, y sumar su saldo | 0,5 | Dilan | Adecuación Funcional → Corrección |
| 11.2 | Endpoint `GET` de saldo protegido con JWT | 0,5 | Dilan | Seguridad → Autenticidad |
| 11.3 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Dilan | Mantenibilidad → Capacidad de ser probado |

---

## 7. HU-12 — Notificación de Pago Rechazado (2 SP)

**Historia:** Como comerciante, quiero recibir una alerta si mi método de pago falla, para poder intentar
con otra tarjeta sin perder los productos que había reservado.

**Escenarios BDD** (el 1 es el aprobado, con «carrito» entendido como el pedido en PENDIENTE_PAGO; el 2 es propuesto)

```gherkin
Escenario 1 — Pago rechazado
Given que el comerciante intenta pagar pero la tarjeta no tiene fondos
When  la pasarela de pagos retorna un error de transacción
Then  el sistema muestra un mensaje de "Pago Rechazado" y mantiene el pedido activo durante
      15 minutos para reintentar

Escenario 2 (propuesto) — Reintento
Given un pago rechazado hace menos de 15 minutos
When  el comerciante reintenta con otra tarjeta
Then  el sistema procesa el pago sobre el mismo pedido
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 12.1 | Lógica de rechazo en `PagoService`: mensaje «Pago Rechazado» y pedido activo 15 min | 0,5 | Dilan | Fiabilidad → Tolerancia a fallos |
| 12.2 | Respuesta del endpoint de pago ante rechazo y reintento | 0,5 | Dilan | Usabilidad → Protección contra errores |
| 12.3 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Dilan | Mantenibilidad → Capacidad de ser probado |

---

## 8. Trazabilidad BDD → Pruebas Automatizadas

| HU | Escenario BDD | Resultado esperado | Tarea |
| --- | --- | --- | --- |
| HU-05 | E1 Contacto exitoso | 200 + confirmación | 5.4 |
| HU-05 | E2 Sin token (propuesto) | 401 | 5.4 |
| HU-09 | E1 Pago exitoso | pago confirmado, pedido PAGADO + orden | 9.4 |
| HU-09 | E2 Pago rechazado (propuesto) | RECHAZADO, sin orden | 9.4 |
| HU-10 | E1 Descarga de recibo | PDF con detalle y monto | 10.3 |
| HU-10 | E2 Pago no exitoso (propuesto) | error, sin PDF | 10.3 |
| HU-11 | E1 Pago acreditado y saldo | pago acreditado + saldo total | 11.3 |
| HU-11 | E2 Sin ventas (propuesto) | saldo 0 | 11.3 |
| HU-12 | E1 Pago rechazado | mensaje + pedido activo 15 min | 12.3 |
| HU-12 | E2 Reintento (propuesto) | pago sobre el mismo pedido | 12.3 |

---

## 9. Riesgos y Supuestos del Sprint

| Riesgo / Supuesto | Impacto | Mitigación |
| --- | --- | --- |
| Sprint de 1 semana | Alto | Revisar avance en cada Daily; priorizar HU-09 y HU-11 (Must Have) |
| Las tareas se estimaron en 30 min (1 h las pruebas) contando con GitHub Copilot; el código generado debe revisarse | Medio | Usar la reserva de 13 h; revisar y entender el código antes de cada PR |
| HU-10, HU-11 y HU-12 dependen de HU-09 | Alto | Iniciar HU-09 primero; PRs pequeños |
| HU-11 acredita el pago al confirmarse, sin esperar la entrega (HU-08, Sprint 4) | Medio | Documentarlo como decisión de diseño; confirmar con el Product Owner |
| Pasarela real (PSE/tarjetas) fuera de alcance | Medio | Adaptador simulado/sandbox, documentado como decisión de diseño |
| Generación de PDF requiere una librería nueva | Medio | Elegirla en la tarea 10.1 y documentarla |
| HU-09 original hablaba de «carrito»; ahora el pedido nace en HU-05 | Bajo | Ajustar la redacción del Backlog |
| HU-12 se movió del Sprint 4 y conserva 2 SP | Bajo | Mantener la estimación alineada en el Backlog |
| Pruebas y Checkstyle al final del Sprint 1 | Medio | Pruebas en el mismo PR de cada tarea; `mvn test` y `mvn checkstyle:check` antes de cada PR |

---

## 10. Referencias

- Definición de Hecho: [`docs/dod.md`](dod.md)
- Product Backlog: [`BACKLOG.md`](../BACKLOG.md)
- Guía de flujo Git/GitHub: ramas `feature/HU-XX-descripcion` → PR → `develop`

---

## 11. Registro de cambios

| Fecha | Cambio |
| --- | --- |
| 17/11/2026 | HU-05 y HU-09 completadas y fusionadas en `develop` (PR #112 y PR #118). El plan no cambió: alcance, Story Points, asignación y orden de ejecución siguen vigentes. Resultados en [Sprint 3 Review](sprint-3-review.md). |
