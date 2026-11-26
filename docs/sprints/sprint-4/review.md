# Sprint 4 Review — AgroValle Connect

> **Estado del documento:** la Review se realizó, según confirmación del equipo. Las secciones
> iniciales conservan el corte técnico verificable en `develop` al 24/11/2026; la actualización
> posterior registra el cierre técnico local. Falta incorporar la minuta, asistentes y
> retroalimentación de la sesión.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 (23/11/2026 – 25/11/2026) |
| **Versión objetivo del incremento** | `v1.0.0` (tag pendiente de aprobación e integración en `main`) |
| **Fecha de la Review** | 25/11/2026 (realizada, según confirmación del equipo) |
| **Asistentes** | Pendiente de transcribir desde la minuta |
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

## 3. Verificación funcional y demostración pendiente al corte del 24/11/2026

La Review se realizó, según confirmación del equipo. En la documentación consultada no se encontró
evidencia de la demostración manual ni de una ejecución contra un servidor PostgreSQL real; la
evidencia por historia que sigue corresponde a pruebas automatizadas.

### 3.1 HU-08 — Actualización de Estado de Entrega

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Llegada del transportista asignado al destino | `PATCH /api/v1/viajes/{id}/llegada` con JWT | Registrar checkpoint, cambiar el viaje a `EN_DESTINO` y notificar al comprador | Las pruebas cubren checkpoint y publicación del evento; el listener solo registra un log, no se comprobó entrega de notificación | PR #152 |
| 2 | Conductor no asignado al viaje | Misma petición con JWT de otro transportista | 403 Forbidden | Cubierto en las pruebas de `CheckpointService` | Evidencia del registro inicial de HU-08 |
| 3 | Viaje en estado distinto de `EN_RUTA` | Misma petición con JWT válido | 409 Conflict | Cubierto en las pruebas de `CheckpointService` | Evidencia del registro inicial de HU-08 |
| 4 | Petición sin autenticación | Misma petición sin JWT | 401 Unauthorized | Cubierto en las pruebas de `ViajeController` | Evidencia del registro inicial de HU-08 |
| 5 | Agricultor autenticado intenta registrar la llegada | Misma petición con rol de agricultor | 403 Forbidden | Cubierto en las pruebas de `ViajeController` | Evidencia del registro inicial de HU-08 |
| 6 | Respuesta de llegada exitosa | `PATCH /api/v1/viajes/{id}/llegada` con JWT del transportista asignado | 200 OK con viaje, estado, ubicación y fecha | Endpoint y protección cubiertos por `ViajeControllerTest`; la ejecución real contra PostgreSQL no está acreditada | Evidencia del registro inicial de HU-08 |

El diseño inicial describía la tabla `checkpoint_viaje`, su clave foránea, restricción de estados e
índice por viaje y fecha. El servicio debía guardar el checkpoint y cambiar el viaje a `EN_DESTINO`.
El registro inicial reportó 3 pruebas de servicio y 3 pruebas MockMvc. La versión de migración que
aparece en ese borrador (V10) no debe tomarse como la versión integrada, que fue ajustada durante la
integración de PR #152.

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

### 3.5 Registro inicial de HU-08 (23/11/2026)

La ficha preliminar de Review, fechada el 23/11/2026, tenía como objetivo de HU-08 que el conductor
registrara la llegada al destino y notificara internamente al comprador. Declaró 3 SP completados
técnicamente y describió el incremento como implementación de la migración y entidad de checkpoints,
el servicio de llegada, el endpoint protegido y sus pruebas JUnit 5/MockMvc. En ese corte se
reportaron 6 pruebas enfocadas y 102 pruebas en la suite, con 0 fallos, 0 errores y 1 omitida; la
compilación reportó 104 fuentes principales y Java 17. Son datos del incremento inicial; la suite
posterior de Sprint 4 se registra en la sección 4.

La demostración HTTP completa contra PostgreSQL no se había ejecutado. Tampoco se había registrado
un PR ni el resultado de una Review formal en esa primera ficha. Posteriormente, HU-08 se integró
mediante los commits `e2abc94` y `63c972b`, con el ajuste de migración registrado en PR #152. Esto
actualiza el estado de integración, pero no acredita por sí solo la validación con PostgreSQL real
ni una demostración formal.

| Tarea técnica inicial | Horas estimadas en el borrador |
| --- | ---: |
| Migración y entidad de checkpoint | 0,5 |
| `CheckpointService` y notificación | 0,75 |
| Endpoint protegido con JWT | 0,5 |
| Pruebas JUnit 5 y MockMvc | 0,5 |
| **Total estimado** | **2,25** |

Estas son estimaciones preliminares, no horas trabajadas ni una actualización del acta de Planning.

El borrador inicial identificó además que la migración no se había validado contra PostgreSQL real
y que la notificación era interna. La revisión actualizada conserva ambos puntos como limitaciones
pendientes. La versión de migración que aparece en el borrador inicial (V10) fue reemplazada durante
la integración; para el estado integrado se deben consultar los commits `e2abc94`, `63c972b` y el
ajuste registrado en PR #152, no el número de versión del borrador.

## 4. Calidad del incremento

| Verificación | Comando | Resultado | Evidencia |
| --- | --- | --- | --- |
| Pruebas limpias, migraciones y estilo | `.\mvnw.cmd clean verify checkstyle:check` | BUILD SUCCESS; 171 pruebas, 0 fallos, 0 errores, 1 omitida; Flyway aplicó 22 migraciones en H2; 0 violaciones Checkstyle | Ejecución local posterior al cierre técnico (25/11/2026) |
| Pruebas automatizadas | `./mvnw verify` | 160 pruebas ejecutadas, 0 fallos, 0 errores, 1 omitida — BUILD SUCCESS (24/11/2026) | Ejecución local sobre `develop` actualizado |
| Estilo de código | `./mvnw checkstyle:check` | 0 violaciones — BUILD SUCCESS (24/11/2026) | Ejecución local |
| Pre-commit hooks (Husky) | Commit de prueba | Pendiente | Pendiente |

> **Nota:** las pruebas automatizadas corren con H2 en modo PostgreSQL; no representan una
> validación del flujo sobre un servidor PostgreSQL real.

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-08 | No desglosadas | 1,5 | No calculable | Tiempo real informado por el usuario el 25/11/2026 |
| HU-13 | 2,5 | 1 h 35 min | −55 min | Aclaración del usuario: tiempo total de las tareas de HU-13; el issue #45 reportó 2 h |
| HU-14 | No desglosadas | 1,5 | No calculable | Tiempo real informado por el usuario el 25/11/2026 |
| HU-15 | No desglosadas | 1,5 | No calculable | Tiempo real informado por el usuario el 25/11/2026 |
| **Total** | **9** | **6 h 5 min** | **−2 h 55 min** | Capacidad estimada: 24 h; la planificación por historia no quedó registrada para HU-08, HU-14 y HU-15 |

## 6. Trabajo pendiente al corte del 24/11/2026

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| Demostración integrada de HU-08 → HU-13 | No se encontró una transición de `EN_DESTINO` a `ENTREGADA`; las pruebas de HU-13 preparan el estado final directamente | Definir la transición/criterio de entrega e incorporar una prueba integrada antes de declarar el flujo completo |
| Notificación de HU-08 al comprador | El listener del evento solo escribe un log y el evento se publica sin identificador del comprador | Conectar un canal de notificación real, incluir destinatario y probar su recepción |
| Validación de base de datos real | La ejecución documentada de `verify` utiliza H2 | Si el criterio de aceptación exige PostgreSQL real, ejecutar y registrar esa validación |

Las dos primeras brechas se cerraron técnicamente después de este corte; ver la actualización de
la sección 9. La validación en PostgreSQL real continúa pendiente.

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Pendiente | Pendiente |

## 8. Actualización del Product Backlog

En el corte del 24/11/2026, HU-08, HU-13, HU-14 y HU-15 estaban integradas en `develop`, pero aún
faltaban la transición `EN_DESTINO` → `ENTREGADA` y la notificación persistida al comprador. Esas
brechas se cerraron técnicamente después, según la actualización de la sección 9; la evidencia de
verificación corresponde a la ejecución local registrada allí.

## 9. Actualización posterior — cierre técnico de HU-08

Después del corte documentado arriba, se completó el flujo de entrega:

- El transportista asignado registra la llegada con `PATCH /api/v1/viajes/{id}/llegada`, que
  transiciona el viaje a `EN_DESTINO` y publica el evento con los datos del comprador/pedido.
- El listener persiste la notificación de llegada después del commit; el comprador autenticado
  puede consultarla en `GET /api/v1/notificaciones/comprador`.
- El mismo transportista confirma la entrega en `PATCH /api/v1/viajes/{id}/entrega`. Se registra
  el checkpoint `ENTREGADA` y se libera el vehículo.
- HU-13 y HU-14 requieren `ENTREGADA`, por lo que las calificaciones dependen del cierre de HU-08.
- La migración V22 amplía los estados permitidos y agrega el destinatario comprador a
  `notificacion`, sin modificar migraciones anteriores.

La primera suite limpia detectó expectativas V21 desactualizadas en dos pruebas de migración. Se
actualizaron para esperar V22 y verificar la columna `comprador_id`. La nueva ejecución limpia
terminó con BUILD SUCCESS: 171 pruebas, 0 fallos, 0 errores y 1 omitida; Flyway aplicó las 22
migraciones y Checkstyle reportó 0 violaciones. JaCoCo registró 90% en instrucciones y 72% en
ramas. No se ha acreditado una ejecución contra PostgreSQL real.

## 10. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- PR #150 (HU-13): [https://github.com/DilanStiven/Ingenieria-de-software/pull/150](https://github.com/DilanStiven/Ingenieria-de-software/pull/150)
- Issue #45 (HU-13): [https://github.com/DilanStiven/Ingenieria-de-software/issues/45](https://github.com/DilanStiven/Ingenieria-de-software/issues/45)
- Tablero (GitHub Projects): [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
- Planning: [`planning.md`](planning.md)
- Daily Scrum: [`../../bitacora-daily-scrum.md`](../../bitacora-daily-scrum.md)
- Retrospectiva: [`retrospective.md`](retrospective.md)
