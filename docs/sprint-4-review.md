# Sprint 4 Review — AgroValle Connect

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 |
| **Fecha de la Review** | 23/11/2026 |
| **Asistentes** | Pendiente de registro formal en una reunión con el Product Owner |
| **Archivo** | `docs/sprint-4-review.md` |

---

## 1. Sprint Goal y resultado

**Sprint Goal:** Entregar el incremento de actualizacion de estado de entrega que permita al conductor
registrar la llegada de un viaje al destino y notificar internamente al comprador.

**Resultado:** Cumplido técnicamente para HU-08. El incremento incluye la persistencia de checkpoints,
la actualizacion del estado de la orden, la protección del endpoint y las pruebas JUnit 5/MockMvc.

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-08 — Actualización de Estado de Entrega | 3 | 3 | ✅ Done técnicamente | Migración V10, CheckpointService, ViajeController y 6 pruebas nuevas |
| **Total** | **3** | **3** | | |

## 3. Demo del incremento

Entorno: aplicación Spring Boot + PostgreSQL; la ejecución HTTP completa contra PostgreSQL no se
realizó en esta revisión. La protección HTTP y las reglas de negocio se verificaron con pruebas
MockMvc y JUnit 5.

### 3.1 HU-08.1 — Tabla checkpoint_viaje con migración

| Escenario | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- |
| Registrar una migración V10 | Crear checkpoint_viaje e índice por viaje y fecha | Implementada la tabla, clave externa, restricción de estados e índice | `src/main/resources/db/migration/V10__create_checkpoint_viaje.sql` |
| Persistir estados posteriores a EN_RUTA | Permitir EN_DESTINO y ENTREGADO sin romper HU-07 | Extendidos los estados y la restricción de ruta completa | `OrdenLogistica.java` |

### 3.2 HU-08.2 — CheckpointService

| Escenario BDD | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- |
| Conductor asignado registra llegada | Viaje pasa a EN_DESTINO y se crea el checkpoint | Implementado | `CheckpointServiceTest` |
| Conductor no asignado intenta registrar llegada | 403 Forbidden | Implementado y probado | `CheckpointServiceTest` |
| Viaje no está EN_RUTA | 409 Conflict | Implementado y probado | `CheckpointServiceTest` |

### 3.3 HU-08.3 — Endpoint protegido

```bash
PATCH /api/v1/viajes/{id}/llegada
Authorization: Bearer <JWT>
```

| Escenario | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- |
| Transportista autenticado | 200 OK con viaje, estado, ubicación y fecha | Implementado | `ViajeControllerTest` |
| Sin autenticación | 401 Unauthorized | Implementado y probado | `ViajeControllerTest` |
| Agricultor autenticado | 403 Forbidden | Implementado y probado | `ViajeControllerTest` |

### 3.4 HU-08.4 — Pruebas JUnit 5

Las pruebas cubren conductor asignado, conductor ajeno, estado incompatible, autenticación y
autorización por rol.

## 4. Calidad del incremento

| Verificación | Comando | Resultado |
| --- | --- | --- |
| Pruebas enfocadas | `mvnw.cmd -Dtest=CheckpointServiceTest,ViajeControllerTest test` | 6 pruebas, 0 fallos |
| Suite completa | `mvnw.cmd test` | 102 pruebas, 0 fallos, 1 omitida |
| Compilación | Maven Compiler | 104 fuentes principales compiladas; Java 17 |

**Limitaciones:** no se confirmó la migración ni el endpoint contra una instancia PostgreSQL real
durante la revisión.

## 5. Esfuerzo planificado vs. real

| Tarea | Horas planificadas | Resultado |
| --- | ---: | --- |
| HU-08.1 — Migración y entidad | 0,5 h | Completada |
| HU-08.2 — CheckpointService y notificación | 0,75 h | Completada |
| HU-08.3 — Endpoint protegido con JWT | 0,5 h | Completada |
| HU-08.4 — Pruebas JUnit 5 y MockMvc | 0,5 h | Completada |
| **Total** | **2,25 h** | **Completada técnicamente** |

Los tiempos reales por integrante no fueron registrados y no se atribuyen en este documento.

## 6. Trabajo no completado

| Tarea | Motivo | Decisión |
| --- | --- | --- |
| Validación persistente con PostgreSQL real | No se dispuso de una ejecución controlada en el registro de esta revisión | Ejecutar antes del cierre formal |
| Notificación externa al comprador | El incremento implementa una notificación interna por evento y log | Definir canal real si el alcance lo requiere |
| PR o enlace de integración GitHub | No se creó ni se confirmó un PR durante este trabajo | Registrar el enlace al fusionar |

## 7. Retroalimentación recibida

Pendiente de reunión formal con el docente en el rol de Product Owner.

## 8. Actualización del Product Backlog

HU-08 pasa a **Done técnicamente**. Debe permanecer en revisión hasta completar la validación
contra PostgreSQL real y registrar el PR aprobado.

## 9. Enlaces

- Repositorio: [https://github.com/DilanStiven/Ingenieria-de-software](https://github.com/DilanStiven/Ingenieria-de-software)
- Tablero: [https://github.com/users/DilanStiven/projects/2](https://github.com/users/DilanStiven/projects/2)
