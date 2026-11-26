# Sprint 4 Retrospectiva — AgroValle Connect

> **Estado del documento:** la retrospectiva se realizó, según confirmación del equipo. Falta
> transcribir la minuta con asistentes, observaciones y decisiones; no se atribuyen comentarios ni
> evidencias que no estén en los documentos consultados.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 (23/11/2026 – 25/11/2026) |
| **Fecha de la Retrospectiva** | 25/11/2026 (realizada, según confirmación del equipo) |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprints/sprint-4/retrospective.md` |

---

## 1. Contexto del Sprint

El Grupo 5 se comprometió a entregar 9 Story Points distribuidos en cuatro historias de usuario
(HU-08, HU-13, HU-14 y HU-15), con una capacidad estimada de 24 horas. Al 24/11/2026, las cuatro
historias estaban integradas en `develop`; el usuario aclaró posteriormente los tiempos reales:
1 h 30 min para HU-08, HU-14 y HU-15, y 1 h 35 min para HU-13. La Review y Retrospectiva se
realizaron el 25/11/2026, según confirmación del equipo; las minutas y acuerdos todavía están
pendientes de transcripción.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | La verificación local de `develop` terminó con 160 pruebas, 0 fallos, 1 omitida y Checkstyle con 0 violaciones | `./mvnw verify` y `./mvnw checkstyle:check`, 24/11/2026 |
| 2 | HU-13 se completó en 1 h 35 min frente a las 2,5 h planificadas, iniciando y finalizando el mismo día (23/11/2026) | Aclaración posterior del usuario; Issue #45 originalmente reportó 2 h |
| 3 | No se registraron otras observaciones antes de la sesión formal | — |

## 3. ¿Qué no funcionó? (Stop)

| # | Observación (tercera persona) | Impacto | Evidencia |
| --- | --- | --- | --- |
| 1 | El merge de `develop` en HU-13 dejó marcadores de conflicto en pruebas y colisión de versiones Flyway; el check de ese commit falló | CI no pudo compilar las pruebas hasta resolver los marcadores y asignar versiones de migración únicas | PR #150 — commit `2a3d66f`; corrección `8b34b00` |
| 2 | No se registraron otros problemas antes de la sesión formal | — | — |

## 4. ¿Qué se debería empezar a hacer? (Start)

| # | Propuesta (tercera persona) | Motivación |
| --- | --- | --- |
| 1 | Correr `./mvnw verify` después de integrar cambios de `develop` y antes de publicar | Detectar marcadores de conflicto, errores de compilación y colisiones entre migraciones antes de CI |
| 2 | No se registraron otras propuestas antes de la sesión formal | — |

## 5. Seguimiento de las acciones del Sprint 3 y de los riesgos del Planning

| Acción / Riesgo | Qué ocurrió |
| --- | --- |
| Ejecutar pruebas y Checkstyle antes de integrar | ✅ `./mvnw verify` (160 pruebas, 0 fallos, 1 omitida) y `./mvnw checkstyle:check` (0 violaciones), 24/11/2026 |
| Escribir las pruebas junto con cada historia | ✅ HU-08, HU-13, HU-14 y HU-15 integraron pruebas automatizadas |
| Sprint muy corto (3 días) — riesgo de no completar todas las historias | Las cuatro historias estaban integradas antes del cierre programado; Review y Retrospectiva realizadas el 25/11/2026, según confirmación del equipo |
| Integración HU-08/HU-13 y aviso al comprador al corte del 24/11/2026 | No se encontró transición de `EN_DESTINO` a `ENTREGADA`; el listener de llegada solo registra un log y no se verificó entrega de notificación. Cerrado técnicamente después; ver sección 10 |

## 6. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / integrados | 9 / 9 (HU-08, HU-13, HU-14 y HU-15) |
| Horas planificadas / reales | 9 h / 6 h 5 min; HU-08, HU-14 y HU-15: 1 h 30 min cada una; HU-13: 1 h 35 min según aclaración del usuario |
| Dailies registradas | 1 acta completa; la bitácora menciona otras sesiones y registra aparte avances técnicos |
| Pull Requests fusionados | 4 (PR #148, #150, #151 y #152; HU-08 se integró además mediante `63c972b`) |
| Veces que se superó un WIP Limit | No hay registro disponible |
| Impedimentos reportados / resueltos | No hay registro completo disponible |

## 7. Acciones de mejora para el cierre del Sprint 4

| # | Acción (tercera persona) | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | Implementar y probar la transición de `EN_DESTINO` a `ENTREGADA` en el flujo HU-08/HU-13 | Equipo Scrum | Review, 25/11/2026 | Cerrada técnicamente después: endpoint de entrega y suite limpia; ver sección 10 |
| 2 | Conectar el evento de llegada con un canal de notificación al comprador y probar su recepción | Equipo Scrum | Review, 25/11/2026 | Cerrada técnicamente después: notificación persistida y bandeja autenticada; ver sección 10 |
| 3 | Registrar el enlace del PR, los asistentes y los acuerdos de la Review formal | Scrum Master / responsable del tablero | Cierre del Sprint | Evidencia enlazada y asistentes/acuerdos confirmados; la integración técnica posterior está en PR #152 |

## 8. Evolución del registro preliminar de HU-08

El registro inicial de HU-08, elaborado el 23/11/2026, documentó 3 SP completados técnicamente,
6 pruebas nuevas y una suite de 102 pruebas (0 fallos y 1 omitida). En ese momento no se había
registrado validación contra PostgreSQL real ni un PR de integración. También señaló que la
notificación al comprador solo generaba un log interno. Como aspectos positivos, destacó la
separación por capas y las pruebas de conductor asignado, conductor ajeno, estado incompatible,
autenticación y autorización por rol. La revisión preliminar concluyó que el incremento superaba la
suite local, pero dejó el cierre formal sujeto a validar PostgreSQL, definir una notificación
observable y registrar la revisión por pares.

Sus métricas de migración PostgreSQL real (0) y PR registrados (0) corresponden únicamente a ese
corte inicial. El registro recomendó ejecutar la migración y pruebas de integración sobre PostgreSQL
antes de cada merge, acordar un canal de notificación persistente u observable y enlazar el PR y la
Review al cerrar el incremento.

Este corte inicial se conserva como antecedente y no reemplaza el estado posterior de Sprint 4:
el PR #152 y los commits `e2abc94` y `63c972b` documentan la integración; el `verify` del 24/11/2026
reportó 160 pruebas (0 fallos y 1 omitida). Según la Review consolidada, siguen sin comprobarse la
validación con PostgreSQL real y la entrega efectiva de una notificación al comprador.

## 9. Trabajo en equipo

Pendiente de transcribir las observaciones de la sesión formal del 25/11/2026.

## 10. Actualización posterior al cierre técnico

Como seguimiento de las acciones 1 y 2, HU-08 ahora permite al transportista asignado completar
`EN_DESTINO` → `ENTREGADA` y persiste una notificación consultable por el comprador autenticado.
HU-13 y HU-14 solo aceptan calificaciones cuando el viaje alcanzó `ENTREGADA`; así se conecta la
dependencia entre las historias. La migración V22 agrega el soporte de base de datos.

La primera verificación limpia encontró expectativas desactualizadas de V21 en las pruebas de
migración. Se corrigieron para validar V22 y la nueva columna `comprador_id`. La nueva ejecución
`.\mvnw.cmd clean verify checkstyle:check` terminó con BUILD SUCCESS: 171 pruebas, 0 fallos,
0 errores, 1 omitida y 0 violaciones de Checkstyle. JaCoCo registró 90% en instrucciones y 72% en
ramas. La validación en PostgreSQL real y la transcripción de la minuta de retrospectiva continúan
pendientes.

El usuario informó que HU-08, HU-14 y HU-15 tomaron 1 h 30 min cada una, y aclaró que las tareas
de HU-13 tomaron en total 1 h 35 min. El issue #45 había reportado 2 h para HU-13; este documento
usa la aclaración posterior más precisa. El esfuerzo real total es 6 h 5 min frente a 9 h
planificadas (2 h 55 min menos). El Planning no desglosa las horas planificadas de HU-08, HU-14 y
HU-15, así que no se calcula una variación individual para ellas.

## 11. Conclusión

Pendiente de transcribir las conclusiones y acuerdos de la sesión formal de cierre del 25/11/2026.
