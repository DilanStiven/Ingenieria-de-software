# Sprint 3 Retrospectiva — AgroValle Connect

> **Estado del documento:** actualizado el 17/11/2026 con HU-05, HU-09 y HU-10 completadas y validadas.
> Los campos **Pendiente** se completan en la reunión de retrospectiva con observaciones reales del
> equipo; no se atribuyen comentarios ni evidencias que no existan.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 3 (13/11/2026 – 20/11/2026) |
| **Fecha de la Retrospectiva** | Pendiente (20/11/2026) |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprint-3-retrospective.md` |

---

## 1. Contexto del Sprint

El Grupo 5 se comprometió a entregar 11 Story Points distribuidos en cinco historias de usuario
(HU-05, HU-09, HU-10, HU-11 y HU-12), con una capacidad de 24 horas y 11 horas planificadas.
Al 17/11/2026, HU-05 (3 SP, 1,5 h), HU-09 (3 SP, 2 h) y HU-10 (2 SP, 2,5 h) están completadas y
validadas. Son 8 de 11 SP y 6 h reales. HU-11 y HU-12 son las únicas historias pendientes y se
trabajarán más adelante. Resultado final del Sprint: Pendiente.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | El equipo ejecutó `mvn test` y `mvn checkstyle:check` antes del PR de HU-05, obteniendo 78 pruebas en verde y 0 violaciones de estilo, cumpliendo la Definition of Done | Capturas de terminal 17/11/2026 14:10 y 14:11 |
| 2 | HU-05 se completó en 1,5 h frente a las 2,5 h planificadas, finalizando el mismo día que comenzó (17/11/2026) | Issue HU-05 — Start date y Target date: 17/11/2026; comentario de Carlos en el tablero |
| 3 | En HU-09 se repitió la verificación de calidad: 89 pruebas ejecutadas con 0 fallos y 0 errores (1 omitida) y 0 violaciones de Checkstyle, ambas con BUILD SUCCESS | Capturas de terminal 17/11/2026 (`mvn test` finalizó 16:38:50; `mvn checkstyle:check` 16:40:42) |
| 4 | HU-09 se completó en 2 h frente a las 2,5 h planificadas, iniciando y terminando el mismo día (17/11/2026) | Issue #41 — Start date y Target date: 17/11/2026; comentario de Carlos: «Dure 2 horas haciendo las tareas de la historia 9» |
| 5 | Santiago completó HU-10 en 2,5 h frente a las 2 h planificadas; inició y terminó el 17/11/2026 | Issue #42 — Start date y Target date: 17/11/2026; comentario: «duración 2 horas y medias haciendo las tareas de la historia 10» |
| 6 | HU-10 se completó con PDF, historial/descarga y pruebas de pago no aprobado; se informó en la Daily de Discord que el PR quedó cerrado. `mvn verify` finalizó con 96 pruebas, 0 fallos y 1 omitida, y Checkstyle con 0 violaciones | Daily Scrum del 17/11/2026, Issue #42 y ejecuciones del 17/11/2026 (`mvn verify`, `mvn checkstyle:check`) |

## 3. ¿Qué no funcionó? (Stop)

| # | Observación (tercera persona) | Impacto | Evidencia |
| --- | --- | --- | --- |
| 1 | El PR #118 se abrió con rama base `main` y se cambió a `develop` poco antes de la aprobación | Riesgo de fusionar en la rama equivocada; se aparta del flujo `feature` → PR → `develop` | PR #118 — línea de tiempo («changed the base branch from main to develop») |
| 2 | Un commit intermedio de la rama (`8837fab`, «Tareas de la historia 9 realizadas») quedó con el check fallido, aunque el check final del merge pasó | Historial con un commit en rojo; se detecta tarde si no se corre el build local antes de cada push | PR #118 — línea de tiempo (commit `8837fab` con ✗; «1 check passed» al fusionar) |
| 3 | Pendiente | Pendiente | Pendiente |

## 4. ¿Qué se debería empezar a hacer? (Start)

| # | Propuesta (tercera persona) | Motivación |
| --- | --- | --- |
| 1 | Pendiente | Pendiente |

## 5. Seguimiento de las acciones del Sprint 1 y de los riesgos del Planning

| Acción / Riesgo | Qué ocurrió |
| --- | --- |
| Ejecutar `mvn test` y `mvn checkstyle:check` antes de cada PR | ✅ Cumplido en HU-05 (78 pruebas, 0 violaciones) y en HU-09 (89 pruebas, 0 fallos, 0 errores, 1 omitida; 0 violaciones) — BUILD SUCCESS (17/11/2026) |
| Escribir las pruebas junto con la implementación de cada tarea | ✅ HU-05 y HU-09 incluyeron sus pruebas en sus PR; HU-10 incluye pruebas JUnit 5 con la implementación de la historia |
| Revisión diaria del WIP Limit de *Code Review* (máx. 2) | Pendiente |
| Sprint de 1 semana y estimación de 30 min por tarea con apoyo de Copilot | Parcialmente verificado: HU-05, HU-09 y HU-10 tomaron 6 h frente a 7 h planificadas; HU-10 excedió su estimación por 0,5 h y las otras dos quedaron por debajo |
| HU-10, HU-11 y HU-12 dependientes de HU-09 | ✅ Dependencia liberada: HU-09 se fusionó en `develop` el 17/11/2026 (PR #118); HU-10 también está completada. HU-11 y HU-12 se trabajarán más adelante |
| Pago acreditado al agricultor sin esperar la entrega (HU-08 en Sprint 4) | Pendiente — se implementa en HU-11 |

## 6. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / completados | 11 / 8 (HU-05, HU-09 y HU-10 completadas) |
| Horas planificadas / reales | 11 / 6 h (HU-05: 1,5 h; HU-09: 2 h; HU-10: 2,5 h); HU-11 y HU-12 pendientes |
| Dailies registradas | Pendiente |
| Historias pendientes | 2 (HU-11 y HU-12; se trabajarán más adelante) |
| Pull Requests fusionados registrados | 2 (PR #112 — HU-05; PR #118 — HU-09) |
| Veces que se superó un WIP Limit | Pendiente |
| Impedimentos reportados / resueltos | Pendiente |

## 7. Acciones de mejora para el Sprint 4

| # | Acción (tercera persona) | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | Pendiente | Pendiente | Pendiente | Pendiente |

## 8. Trabajo en equipo

Pendiente.

## 9. Conclusión

Pendiente.
