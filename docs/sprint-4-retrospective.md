# Sprint 4 Retrospectiva — AgroValle Connect

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 |
| **Fecha de la Retrospectiva** | 23/11/2026 |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprint-4-retrospective.md` |

---

## 1. Contexto del Sprint

El incremento registrado corresponde a HU-08 y sus cuatro historias técnicas: migración con
checkpoint_viaje, servicio de llegada, endpoint protegido y pruebas. La implementación quedó
verificada por la suite local con 102 pruebas, 0 fallos y 1 omitida.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | Se mantuvo la separación por capas: entidad, repositorio, DTO, servicio, evento y controlador. | Archivos nuevos bajo `models`, `repositories`, `dtos`, `services`, `events` y `controllers` |
| 2 | Las reglas se probaron antes de cerrar el incremento: conductor asignado, conductor ajeno y estado incompatible. | `CheckpointServiceTest` con 3 pruebas |
| 3 | El endpoint se cubrió con MockMvc para autenticación y autorización por rol. | `ViajeControllerTest` con 3 pruebas |
| 4 | La suite completa siguió en verde después de integrar los cambios. | `mvnw.cmd test`: 102 pruebas, 0 fallos |

## 3. ¿Qué no funcionó? (Stop)

| # | Observación | Impacto | Evidencia |
| --- | --- | --- | --- |
| 1 | No se verificó la migración V10 contra PostgreSQL real antes de considerarla cerrada. | Riesgo de diferencias entre Hibernate y la base real. | No existe evidencia de ejecución contra PostgreSQL |
| 2 | La notificación al comprador quedó registrada por log interno y no mediante un canal persistente o externo. | Puede no satisfacer una notificación real al usuario. | `LlegadaDestinoListener` |
| 3 | No se registró un PR ni el resultado de una reunión formal de Review. | Falta trazabilidad de aprobación del incremento. | No existe enlace de PR confirmado |

## 4. ¿Qué se debería empezar a hacer? (Start)

| # | Propuesta | Motivación |
| --- | --- | --- |
| 1 | Ejecutar la migración y una prueba de integración sobre PostgreSQL real antes de cada merge. | Validar restricciones y persistencia efectiva |
| 2 | Definir el canal de notificación al comprador y persistir o publicar el mensaje por un mecanismo observable. | Cumplir la historia desde el punto de vista del usuario |
| 3 | Adjuntar el enlace del PR y el resultado de la Review al documento al cerrar el Sprint. | Mantener trazabilidad auditable |

## 5. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / completados | 3 / 3 técnicamente |
| Pruebas nuevas | 6 |
| Pruebas totales | 102 |
| Fallos / errores | 0 / 0 |
| Pruebas omitidas | 1 |
| Migraciones verificadas contra PostgreSQL real | 0 |
| Pull Requests registrados | 0 |

## 6. Acciones de mejora para el siguiente cierre

| # | Acción | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | Ejecutar V10 y pruebas de integración sobre PostgreSQL real | Equipo de desarrollo | Antes del próximo merge | Migración aplicada y endpoint probado con datos persistidos |
| 2 | Definir el alcance de la notificación al comprador | Product Owner y equipo | Próxima reunión | Canal y formato aceptados |
| 3 | Registrar PR, asistentes y acuerdo formal de Review | Scrum Master / responsable del tablero | Cierre del incremento | Review con evidencia enlazada |

## 7. Conclusión

El incremento técnico de HU-08 está implementado y supera la suite local. El cierre formal debe
esperar validación sobre PostgreSQL real, una decisión sobre el canal de notificación y la revisión
del Pull Request por el equipo.
