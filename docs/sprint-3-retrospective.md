# Sprint 3 Retrospectiva — AgroValle Connect

> **Estado del documento:** actualizado el 18/11/2026 con HU-05, HU-09, HU-10, HU-11 y HU-12
> implementadas y validadas localmente. La fecha corresponde a esta actualización documental; la
> Las reuniones de Planning y Retrospectiva se realizaron el 16/11/2026 y quedaron registradas.
> El Sprint 3 permanece abierto hasta completar la integración y revisión pendientes.
> Los campos **Pendiente** se completan en la reunión de retrospectiva con observaciones reales del
> equipo; no se atribuyen comentarios ni evidencias que no existan.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 3 (13/11/2026 – 20/11/2026) |
| **Fecha de la Retrospectiva** | 16/11/2026 (reunión formal realizada) |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprint-3-retrospective.md` |

---

## 1. Contexto del Sprint

El Grupo 5 se comprometió a entregar 12 Story Points distribuidos en cinco historias de usuario
(HU-05, HU-09, HU-10, HU-11 y HU-12), con una capacidad de 24 horas y 11 horas planificadas.
Al 18/11/2026, las cinco historias están implementadas y validadas localmente (12/12 SP, según el
Planning). Las horas reportadas suman 9 h. HU-11 ya está integrada y en Done; HU-12 aún requiere PR,
revisión e integración. El Sprint permanece abierto.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | El equipo ejecutó `mvn test` y `mvn checkstyle:check` antes del PR de HU-05, obteniendo 78 pruebas en verde y 0 violaciones de estilo, cumpliendo la Definition of Done | Capturas de terminal 17/11/2026 14:10 y 14:11 |
| 2 | HU-05 se completó en 1,5 h frente a las 2,5 h planificadas, finalizando el mismo día que comenzó (17/11/2026) | Issue HU-05 — Start date y Target date: 17/11/2026; comentario de Carlos en el tablero |
| 3 | En HU-09 se repitió la verificación de calidad: 89 pruebas ejecutadas con 0 fallos y 0 errores (1 omitida) y 0 violaciones de Checkstyle, ambas con BUILD SUCCESS | Capturas de terminal 17/11/2026 (`mvn test` finalizó 16:38:50; `mvn checkstyle:check` 16:40:42) |
| 4 | HU-09 se completó en 2 h frente a las 2,5 h planificadas, iniciando y terminando el mismo día (17/11/2026) | Issue #41 — Start date y Target date: 17/11/2026; comentario de Carlos: «Dure 2 horas haciendo las tareas de la historia 9» |
| 5 | Santiago completó HU-10 en 2,5 h frente a las 2 h planificadas; inició y terminó el 17/11/2026 | Issue #42 — Start date y Target date: 17/11/2026; comentario: «duración 2 horas y medias haciendo las tareas de la historia 10» |
| 6 | HU-10 se completó con PDF, historial/descarga y pruebas de pago no aprobado; se informó en la Daily de Discord que el PR quedó cerrado. `mvn verify` finalizó con 96 pruebas, 0 fallos y 1 omitida, y Checkstyle con 0 violaciones | Daily Scrum del 17/11/2026, Issue #42 y ejecuciones del 17/11/2026 (`mvn verify`, `mvn checkstyle:check`) |
| 7 | HU-11 integró la migración V7, la acreditación inmediata al aprobar el pago y el endpoint JWT `GET /api/v1/billetera/saldo`; la verificación terminó con 102 pruebas, 0 fallos, 0 errores y 1 omitida, y Checkstyle sin violaciones | Implementación local del 18/11/2026 (`mvn verify` y `mvn checkstyle:check`); pruebas de arranque de Spring Boot, migración nueva y actualización desde V6 |
| 8 | Dilan reportó completar HU-12 en alrededor de 1,5 h, por debajo de las 2 h planificadas; el flujo permite conservar y reintentar el mismo pedido durante 15 minutos | Mensaje de Daily compartido el 18/11/2026; issue #44; `PagoServiceTest` y `PagoControllerTest` |
| 9 | La validación completa posterior a HU-12 pasó con 118 pruebas, 0 fallos, 1 omitida y 0 violaciones de Checkstyle | Ejecución local `mvn verify checkstyle:check`, 18/11/2026 |

## 3. ¿Qué no funcionó? (Stop)

| # | Observación (tercera persona) | Impacto | Evidencia |
| --- | --- | --- | --- |
| 1 | El PR #118 se abrió con rama base `main` y se cambió a `develop` poco antes de la aprobación | Riesgo de fusionar en la rama equivocada; se aparta del flujo `feature` → PR → `develop` | PR #118 — línea de tiempo («changed the base branch from main to develop») |
| 2 | Un commit intermedio de la rama (`8837fab`, «Tareas de la historia 9 realizadas») quedó con el check fallido, aunque el check final del merge pasó | Historial con un commit en rojo; se detecta tarde si no se corre el build local antes de cada push | PR #118 — línea de tiempo (commit `8837fab` con ✗; «1 check passed» al fusionar) |
| 3 | Las tareas #105–#110 están en Done. El PR #124 de HU-11 fue aprobado y fusionado en `develop`, y la historia #43 aparece en Done; HU-12 (#44) continúa en Code Review sin PR | El cierre formal depende de crear, revisar e integrar el PR de HU-12 | Capturas del PR #124 y de la columna Done compartidas el 18/11/2026 |

## 4. ¿Qué se debería empezar a hacer? (Start)

| # | Propuesta (tercera persona) | Motivación |
| --- | --- | --- |
| 1 | Crear y completar el PR de HU-12 y actualizar la historia #44 después de su fusión | Cerrar la historia cuando código, pruebas, revisión y estado del tablero coincidan |
| 2 | Mantener HU-12 alineada en 2 SP en el Planning y en el issue #44 | Mantener consistentes la estimación del Backlog y las métricas de Sprint |

## 5. Seguimiento de las acciones del Sprint 1 y de los riesgos del Planning

| Acción / Riesgo | Qué ocurrió |
| --- | --- |
| Ejecutar `mvn test` y `mvn checkstyle:check` antes de cada PR | ✅ Cumplido en HU-05 (78 pruebas, 0 violaciones) y en HU-09 (89 pruebas, 0 fallos, 0 errores, 1 omitida; 0 violaciones) — BUILD SUCCESS (17/11/2026) |
| Escribir las pruebas junto con la implementación de cada tarea | ✅ HU-05, HU-09 y HU-10 incluyeron pruebas JUnit 5 en sus PR; HU-11 cubrió acreditación y saldo 0; HU-12 cubrió rechazo, plazo y reintento con JUnit 5 |
| Revisión diaria del WIP Limit de *Code Review* (máx. 2) | Pendiente |
| Sprint de 1 semana y estimación de 30 min por tarea con apoyo de Copilot | Verificado parcialmente con las horas reportadas: las cinco historias suman 9 h frente a 11 h planificadas; HU-10 excedió su estimación por 0,5 h y las otras cuatro quedaron por debajo |
| HU-10, HU-11 y HU-12 dependientes de HU-09 | ✅ Dependencia liberada: HU-09 se fusionó en `develop` el 17/11/2026 (PR #118). HU-11 se fusionó en `develop` mediante PR #124 el 18/11; HU-12 sigue pendiente de PR e integración |
| Pago acreditado al agricultor sin esperar la entrega (HU-08 en Sprint 4) | ✅ Implementado en HU-11 al aprobar el pago, sin esperar la entrega |

## 6. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / implementados | 12 / 12 (HU-05, HU-09, HU-10, HU-11 y HU-12); HU-11 está integrada y HU-12 sigue pendiente de PR |
| Horas planificadas / reales reportadas | 11 h / 9 h (HU-05: 1,5 h; HU-09: 2 h; HU-10: 2,5 h; HU-11: 1,5 h; HU-12: 1,5 h) |
| Dailies registradas | 3 (dos el 17/11/2026 y una el 18/11/2026) |
| Historias pendientes de implementación local | 0; HU-12 aún requiere PR, revisión e integración |
| Pull Requests fusionados registrados | 3 (PR #112 — HU-05; PR #118 — HU-09; PR #124 — HU-11) |
| Veces que se superó un WIP Limit | Pendiente |
| Impedimentos reportados / resueltos | Pendiente |

## 7. Acciones de mejora antes del cierre del Sprint 3

| # | Acción (tercera persona) | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | Abrir y completar la revisión/integración de HU-12 cuando se autorice subir los cambios; actualizar issues #44 y #108–#110 | Dilan Stiven Castillo Valencia | Antes del cierre del Sprint, 20/11/2026 | PR revisado, tareas e historia en Done y evidencia enlazada |
| 2 | Mantener enlazada en la issue #43 la evidencia del PR #124 fusionado | Dilan Stiven Castillo Valencia | Antes del cierre del Sprint, 20/11/2026 | Issue #43 en Done y PR #124 referenciado |
| 3 | Distinguir la validación local en H2 de una ejecución real en PostgreSQL | Equipo Scrum | Antes del cierre técnico | Evidencia registrada con el entorno utilizado |
| 4 | Mantener la publicación Git pendiente hasta recibir autorización expresa | Dilan Stiven Castillo Valencia | Cuando el equipo autorice | Commit y push realizados de forma rastreable |

## 8. Trabajo en equipo

La ejecución mostró colaboración técnica al repartir historias completas y reutilizar las dependencias
liberadas por HU-09. HU-11 y HU-12 fueron implementadas por Dilan Stiven Castillo Valencia sobre el
flujo existente de pagos y quedaron respaldadas por pruebas locales y Checkstyle. Dilan reportó que
terminó las tareas de HU-12 en aproximadamente 1,5 h y que no tuvo impedimentos.

## 9. Conclusión

Las cinco historias comprometidas están implementadas y validadas (12/12 SP); HU-11 ya está integrada
en `develop` y aparece en Done. El cierre formal del Sprint aún requiere crear, revisar e integrar el
PR de HU-12 y actualizar su estado en el tablero.
