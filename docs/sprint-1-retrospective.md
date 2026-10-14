# Sprint 1 Retrospectiva — AgroValle Connect

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Estiven Castillo CC 1089002055, Santiago Edilmo Cueno Hurtado CC 1089001065 |
| **Sprint** | Sprint 1 (24/09/2026 – 09/10/2026) |
| **Fecha de la Retrospectiva** | 09/10/2026 — 17:00 (Discord) |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprint-1-retrospective.md` |

---

## 1. Contexto del Sprint

El Grupo 5 se comprometió a entregar 10 Story Points distribuidos en tres historias de usuario
(HU-01, HU-02 y HU-04), con una capacidad de 48 horas y 38 horas planificadas. Al cierre del
Sprint, el equipo completó los 10 Story Points y trabajó 40 horas reales. El Sprint avanzó de
forma estable durante la primera semana; la segunda semana concentró las pruebas automatizadas y
las auditorías de Checkstyle, lo que elevó la carga en los últimos días.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | El equipo realizó las 7 Dailies acordadas y reportó sus avances e impedimentos de forma clara. | Bitácora de Daily Scrums |
| 2 | Kevin Lucumi destacó que el uso de Conventional Commits y de un Pull Request por tarea facilitó el seguimiento de los cambios. | Historial de GitHub |
| 3 | Se observó que la división del trabajo por capas (modelo, repositorio, servicio, controlador) permitió avanzar en paralelo sin bloqueos prolongados. | Tablero de GitHub Projects |
| 4 | El equipo consideró que los pre-commit hooks de Husky evitaron subir código con errores de estilo. | Evidencia `husky-hook.png` |

## 3. ¿Qué no funcionó? (Stop)

| # | Observación (tercera persona) | Impacto | Evidencia |
| --- | --- | --- | --- |
| 1 | El equipo identificó que las pruebas automatizadas y las auditorías de Checkstyle se dejaron para los últimos días del Sprint. | Aumento de la carga y riesgo de cerrar sin margen. | Daily #7: tareas 1.7 y 2.6 en revisión y 1.8, 2.7 y 4.4 en progreso |
| 2 | Kevin Lucumi señaló que la tarea 1.4 (`JwtService`) tomó 3 horas en lugar de las 2 estimadas por la falta de práctica con la API de jjwt. | +1 h en HU-01. | Daily #2 y Review, sección 5 |
| 3 | Se evidenció que el 05/10 la columna *Code Review* llegó a 3 tarjetas, por encima del límite de 2. | Tareas esperando aprobación. | Daily #6 |

## 4. ¿Qué se debería empezar a hacer? (Start)

| # | Propuesta (tercera persona) | Motivación |
| --- | --- | --- |
| 1 | El equipo propuso escribir las pruebas junto con cada tarea (enfoque cercano a TDD) en lugar de dejarlas al final. | Repartir la carga y detectar errores antes. |
| 2 | Santiago Cueno sugirió trabajar en pareja las tareas de Spring Security en el Sprint 2. | Reducir la curva de aprendizaje y el riesgo de estimaciones cortas. |

## 5. Análisis de los riesgos identificados en el Planning

| Riesgo del Planning | Qué ocurrió |
| --- | --- |
| Seguridad con JWT (tareas 1.4 y 2.3) | Se materializó parcialmente: la tarea 1.4 excedió la estimación en 1 hora; la 2.3 se completó dentro de lo previsto. |
| Dependencias entre integrantes | La tarea 2.4 dependió de la 2.3 (impedimento #3); se resolvió priorizando el PR de 2.3. |
| Pruebas automatizadas con mocks y Testcontainers | Las pruebas de HU-02 requirieron `@WithMockUser` para simular la autenticación (impedimento #4). |
| WIP Limits | *In Progress* se mantuvo dentro del límite; *Code Review* se excedió una vez (05/10). |
| Estimación general | 38 horas planificadas frente a 40 reales (+5 %), un margen aceptable. |

## 6. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / completados | 10 / 10 |
| Horas planificadas / reales | 38 / 40 |
| Dailies realizadas | 7 de 7 |
| Pull Requests fusionados | 20 (uno por tarea) |
| Veces que se superó un WIP Limit | 1 |
| Impedimentos reportados / resueltos | 4 / 4 |

## 7. Acciones de mejora para el Sprint 2

| # | Acción (redactada en tercera persona) | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | El equipo se comprometió a ejecutar `mvn test` y `mvn checkstyle:check` de forma local antes de abrir cada Pull Request. | Todo el equipo | Desde el primer día del Sprint 2 | Ningún PR con fallas de estilo o de pruebas. |
| 2 | Dilan Castillo asumió la revisión diaria del WIP Limit de *Code Review*. | Dilan Castillo | Desde el primer día del Sprint 2 | Máximo 2 PR en revisión en cada Daily. |
| 3 | Se acordó escribir las pruebas de cada tarea dentro del mismo Pull Request de la tarea. | Todo el equipo | Desde el primer día del Sprint 2 | Cada PR incluye sus pruebas; ninguna tarea de pruebas queda para el cierre. |

## 8. Trabajo en equipo

El equipo describió el ambiente del Sprint como colaborativo. Se destacó el apoyo mutuo al resolver
conflictos de merge y al desbloquear dependencias entre tareas, y la distribución de carga fue
percibida como equilibrada (entre 9 y 10 horas por integrante).

## 9. Conclusión

Como cierre, el equipo concluyó que la estandarización técnica del Sprint 0 (Husky, Checkstyle y
Conventional Commits) contribuyó al cumplimiento del Sprint Goal. El principal aprendizaje fue la
necesidad de integrar las pruebas automatizadas en el trabajo diario, y ese será el foco de mejora
del Sprint 2.
