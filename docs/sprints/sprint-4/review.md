# Sprint 4 Review — AgroValle Connect

> **Estado del documento:** borrador previo a la Review formal del 25/11/2026. Registra el estado
> verificable en `develop` al 24/11/2026; la demostración y retroalimentación de la reunión siguen
> pendientes.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 (23/11/2026 – 25/11/2026) |
| **Fecha de la Review** | Pendiente (25/11/2026) |
| **Asistentes** | Participantes previstos: equipo de desarrollo y docente; asistencia pendiente de confirmar |
| **Archivo** | `docs/sprints/sprint-4/review.md` |

---

## 1. Sprint Goal y resultado

> **Sprint Goal:** Entregar un incremento funcional en el que el comprador pueda actualizar el estado
> de entrega de su pedido, calificar al agricultor y al producto tras recibirlo, y el sistema registre
> la reseña actualizando el perfil del agricultor.

**Resultado al 24/11/2026:** las cuatro historias se integraron en `develop` (9/9 SP). La
validación automatizada pasa. La revisión de código encontró dos brechas en HU-08/HU-13:

1. No se encontró una operación de aplicación que lleve la orden de `EN_DESTINO` a `ENTREGADA`:
   `CheckpointService` establece el primer estado y `ResenaService` solo acepta el segundo. Las
   pruebas de HU-13 preparan la orden directamente como `ENTREGADA`; no ejercitan la transición de
   HU-08. La reseña no queda habilitada por el recorrido actual de HU-08.
2. `CheckpointService` publica un evento sin `compradorId`; el listener solo escribe un log de
   aplicación. No se encontró entrega de una notificación al comprador ni una prueba que la
   verifique.

Por lo anterior, el flujo integrado no está demostrado de extremo a extremo y no se debe declarar
completo el Sprint Goal funcional.

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-08 — Actualización de Estado de Entrega | 3 | 3 | ✅ Integrada | Commits `e2abc94`, `63c972b`; ajuste de migración en PR #152 |
| HU-13 — Calificación del Producto y del Agricultor | 2 | 2 | ✅ Integrada | PR #150 fusionado en `develop` el 23/11/2026 |
| HU-14 — Calificación del Servicio de Transporte | 2 | 2 | ✅ Integrada | PR #148 fusionado en `develop` el 23/11/2026 |
| HU-15 — Visualización de Reputación Promedio del Productor | 2 | 2 | ✅ Integrada | PR #151 fusionado en `develop` el 23/11/2026 |
| **Total** | **9** | **9** | | PRs #148, #150, #151 y #152 |

## 3. Verificación funcional y demostración pendiente

La demostración manual del incremento está pendiente para la Review del 25/11/2026. La evidencia
por historia que sigue corresponde a pruebas automatizadas; no acredita una demostración manual ni
una ejecución contra un servidor PostgreSQL real.

### 3.1 HU-08 — Actualización de Estado de Entrega

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Llegada del transportista asignado al destino | `PATCH /api/v1/viajes/{id}/llegada` con JWT | Registrar checkpoint, cambiar el viaje a `EN_DESTINO` y notificar al comprador | Las pruebas cubren checkpoint y publicación del evento; el listener solo registra un log, no se comprobó entrega de notificación | PR #152 |

### 3.2 HU-13 — Calificación del Producto y del Agricultor

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Calificación exitosa | `POST /api/v1/ordenes/{ordenId}/resena` con JWT, 5 estrellas y comentario «Excelente tomate» | Reseña guardada y promedio del agricultor actualizado si la orden está `ENTREGADA` | Prueba del servicio y del endpoint presentes; la integración con HU-08 requiere armonizar `EN_DESTINO` y `ENTREGADA` | PR #150 |
| 2 | Sin token (propuesto) | Sin header `Authorization` | 401 Unauthorized | Cubierto por Spring Security y `ResenaControllerTest` | PR #150 |

### 3.3 HU-14 — Calificación del Servicio de Transporte

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Calificación del viaje | `POST /api/v1/viajes/{id}/calificacion` con JWT, puntualidad y estado de cajas | Asociar la calificación al transportista del viaje | Cubierto por `CalificacionTransporteControllerTest` y `CalificacionTransporteServiceTest` | PR #148 |

### 3.4 HU-15 — Visualización de Reputación Promedio del Productor

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Consulta de reputación | `GET /api/v1/agricultores/{id}/reputacion` | Devolver promedio calculado; devolver 0 si no existen reseñas | Cubierto por `ReputacionControllerTest`, `ReputacionServiceTest` y `ReputacionServiceIntegrationTest` | PR #151 |

## 4. Calidad del incremento

| Verificación | Comando | Resultado | Evidencia |
| --- | --- | --- | --- |
| Pruebas automatizadas | `./mvnw verify` | 160 pruebas ejecutadas, 0 fallos, 0 errores, 1 omitida — BUILD SUCCESS (24/11/2026) | Ejecución local sobre `develop` actualizado |
| Estilo de código | `./mvnw checkstyle:check` | 0 violaciones — BUILD SUCCESS (24/11/2026) | Ejecución local |
| Pre-commit hooks (Husky) | Commit de prueba | Pendiente | Pendiente |

> **Nota:** las pruebas automatizadas corren con H2 en modo PostgreSQL; no representan una
> validación del flujo sobre un servidor PostgreSQL real.

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-08 | Pendiente | Pendiente | Pendiente | No se encontró un registro de horas verificable |
| HU-13 | 2,5 | 2 | −0,5 h | Kevin reportó 2 horas en issue #45 |
| HU-14 | Pendiente | Pendiente | Pendiente | No se encontró un registro de horas verificable |
| HU-15 | Pendiente | Pendiente | Pendiente | No se encontró un registro de horas verificable |
| **Total** | **9** | **Pendiente** | Pendiente | Capacidad estimada: 24 h |

## 6. Trabajo no completado

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| Demostración integrada de HU-08 → HU-13 | No se encontró una transición de `EN_DESTINO` a `ENTREGADA`; las pruebas de HU-13 preparan el estado final directamente | Definir la transición/criterio de entrega e incorporar una prueba integrada antes de declarar el flujo completo |
| Notificación de HU-08 al comprador | El listener del evento solo escribe un log y el evento se publica sin identificador del comprador | Conectar un canal de notificación real, incluir destinatario y probar su recepción |
| Validación de base de datos real | La ejecución documentada de `verify` utiliza H2 | Si el criterio de aceptación exige PostgreSQL real, ejecutar y registrar esa validación |

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Pendiente | Pendiente |

## 8. Actualización del Product Backlog

HU-08, HU-13, HU-14 y HU-15 están integradas en `develop`. La confirmación del Sprint Goal queda
sujeta a implementar la transición `EN_DESTINO` → `ENTREGADA` y la entrega real de la notificación
al comprador, que no están presentes en el flujo actual.

## 9. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- PR #150 (HU-13): [https://github.com/DilanStiven/Ingenieria-de-software/pull/150](https://github.com/DilanStiven/Ingenieria-de-software/pull/150)
- Issue #45 (HU-13): [https://github.com/DilanStiven/Ingenieria-de-software/issues/45](https://github.com/DilanStiven/Ingenieria-de-software/issues/45)
- Tablero (GitHub Projects): [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
- Planning: [`planning.md`](planning.md)
- Daily Scrum: [`daily-scrum.md`](daily-scrum.md)
- Retrospectiva: [`retrospective.md`](retrospective.md)
