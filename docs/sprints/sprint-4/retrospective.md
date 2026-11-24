# Sprint 4 Retrospectiva — AgroValle Connect

> **Estado del documento:** en curso. Los campos **Pendiente** se completan en la reunión de
> retrospectiva (25/11/2026) con observaciones reales del equipo; no se atribuyen comentarios ni
> evidencias que no existan.

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Sprint** | Sprint 4 (23/11/2026 – 25/11/2026) |
| **Fecha de la Retrospectiva** | Pendiente (25/11/2026) |
| **Técnica utilizada** | Start / Stop / Continue |
| **Archivo** | `docs/sprints/sprint-4/retrospective.md` |

---

## 1. Contexto del Sprint

El Grupo 5 se comprometió a entregar 9 Story Points distribuidos en cuatro historias de usuario
(HU-08, HU-13, HU-14 y HU-15), con una capacidad estimada de 24 horas. Al 24/11/2026, las cuatro
historias están integradas en `develop`; solo se dispone de horas reales verificables para HU-13
(2 h). La Review y Retrospectiva formales están programadas para el 25/11/2026.

## 2. ¿Qué salió bien? (Continue)

| # | Observación (tercera persona) | Evidencia |
| --- | --- | --- |
| 1 | La verificación local de `develop` terminó con 160 pruebas, 0 fallos, 1 omitida y Checkstyle con 0 violaciones | `./mvnw verify` y `./mvnw checkstyle:check`, 24/11/2026 |
| 2 | HU-13 se completó en 2 h frente a las 2,5 h planificadas, iniciando y finalizando el mismo día (23/11/2026) | Issue #45 — Start date y Target date: 23/11/2026; comentario de Kevin: «me demore 2 horas terminando las tareas de la HU13» |
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
| Sprint muy corto (3 días) — riesgo de no completar todas las historias | Las cuatro historias están integradas antes del cierre programado; Review y Retrospectiva aún pendientes |
| HU-13 depende de una orden `ENTREGADA` (HU-08) | Pendiente de reconciliar: HU-08 registra `EN_DESTINO`, HU-13 exige `ENTREGADA`; falta prueba integrada del flujo |

## 6. Métricas del proceso

| Métrica | Valor |
| --- | --- |
| Story Points comprometidos / integrados | 9 / 9 (HU-08, HU-13, HU-14 y HU-15) |
| Horas planificadas / reales | Según el Planning; 2 h verificables para HU-13; resto sin dato |
| Dailies registradas | 1 acta completa; la bitácora menciona otras sesiones y registra aparte avances técnicos |
| Pull Requests fusionados | 4 (PR #148, #150, #151 y #152; HU-08 se integró además mediante `63c972b`) |
| Veces que se superó un WIP Limit | No hay registro disponible |
| Impedimentos reportados / resueltos | No hay registro completo disponible |

## 7. Acciones de mejora para el cierre del Sprint 4

| # | Acción (tercera persona) | Responsable | Fecha límite | Criterio de éxito |
| --- | --- | --- | --- | --- |
| 1 | Definir y probar la transición entre `EN_DESTINO` y `ENTREGADA` en el flujo HU-08/HU-13 | Equipo Scrum | Review, 25/11/2026 | Prueba integrada que permite reseñar una orden cuyo viaje llegó al destino |
| 2 | No se registró otra acción antes de la sesión formal | — | — | — |

## 8. Trabajo en equipo

Pendiente de la sesión formal del 25/11/2026.

## 9. Conclusión

Pendiente de la sesión formal de cierre del 25/11/2026.
