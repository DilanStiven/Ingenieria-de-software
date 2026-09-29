# Bitácora de Daily Scrums — Sprint 1

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Sprint** | Sprint 1 (24/09/2026 – 09/10/2026) |
| **Sprint Goal** | Entregar un incremento funcional en el que un agricultor pueda registrarse, obtener un token de acceso y publicar sus cosechas, y un comprador pueda filtrar el catálogo por municipio y categoría. |
| **Archivo** | `docs/bitacora-daily-scrum.md` |

---

## 1. Formato y reglas de la Daily

- **Duración máxima:** 15 minutos.
- **Frecuencia acordada:** lunes, miércoles y viernes, más los dos primeros días del Sprint.
  Horario: 20:00 h.
- **Modalidad:** virtual (Discord).
- **Preguntas de cada integrante:** ¿qué hice?, ¿qué haré?, ¿qué impedimentos tengo?
- **Regla de flujo:** se revisa el tablero de GitHub Projects respetando los WIP Limits
  (*In Progress* ≤ 3 y *Code Review* ≤ 2).
- **Numeración:** los Issues #1–#20 corresponden a las tareas 1.1–1.8, 2.1–2.7 y 4.1–4.5 de
  `docs/sprint-1-planning.md`, en ese orden.

## 2. Resumen de Dailies

| # | Fecha | Asistentes | Estado | Impedimentos clave |
| --- | --- | --- | --- | --- |
| 1 | Jueves 24/09/2026 | Los 4 integrantes | Realizada | Ninguno |
| 2 | Viernes 25/09/2026 | Los 4 integrantes | Realizada | Configuración de JWT (jjwt 0.12) |
| 3 | Lunes 28/09/2026 | Los 4 integrantes | Realizada | Ninguno |
| 4 | Miércoles 30/09/2026 | Los 4 integrantes | Realizada | Conflictos de merge leves |
| 5 | Viernes 02/10/2026 | Los 4 integrantes | Realizada | Dependencia 2.4 ← 2.3 |
| 6 | Lunes 05/10/2026 | Los 4 integrantes | Realizada | Simular usuario autenticado en pruebas |
| 7 | Miércoles 07/10/2026 | Los 4 integrantes | Realizada | Ninguno |

## 3. Trabajo previsto según el Sprint Planning (referencia)

| Integrante | Semana 1 (24/09 – 02/10) | Semana 2 (05/10 – 09/10) |
| --- | --- | --- |
| Carlos Andres Rosales Lara | 1.1, 1.2, 1.3 | 1.7, 1.8, 4.1 |
| Kevin Estiven Lucumi Polo | 1.4, 1.5, 2.3 | 2.5, 2.7 |
| Dilan Estiven Castillo | 1.6, 2.1, 2.2 | 4.2, 4.3 |
| Santiago Edilmo Cueno Hurtado | Apoyo en revisión de PR | 2.4, 2.6, 4.4, 4.5 |

*(En la práctica, algunas tareas se adelantaron o se movieron entre semanas por dependencias;
el detalle está en cada Daily.)*

---

## 4. Registro de cada Daily

### Daily #1 — Jueves 24/09/2026
*Kick-off del Sprint, justo después del Sprint Planning*

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 12 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Sprint Planning y creación de los 20 Issues del Sprint 1. | Tarea 1.1: entidad `Productor` y migración V1. | Ninguno |
| Kevin Lucumi | Revisión del repositorio base y de la configuración de Spring Security. | Investigar la librería jjwt como preparación de la tarea 1.4. | Ninguno |
| Dilan Castillo | Lectura de los requisitos de HU-02 y del esquema de la tabla `productos`. | Tarea 2.1: entidad `Producto` y migración V2. | Ninguno |
| Santiago Cueno | Revisión de `checkstyle.xml` y de los hooks de Husky configurados en el Sprint 0. | Revisar los primeros Pull Requests. | Ninguno |

**Estado del tablero:** To Do: 18 · In Progress: 2/3 (1.1, 2.1) · Code Review: 0/2 · Done: 0
**Acuerdos y acciones:**
- Un Pull Request por tarea, con al menos una aprobación de otro integrante.
- Usar Conventional Commits y ramas `feature/tarea-X.Y`.

---

### Daily #2 — Viernes 25/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 13 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Terminó la tarea 1.1 y abrió su Pull Request. | Tarea 1.2: `ProductorRepository`. | Ninguno |
| Kevin Lucumi | Avance inicial de `JwtService` (tarea 1.4): generación del token. | Continuar con la validación del token. | Entender la API de jjwt 0.12 y cómo leer el secreto desde variable de entorno. |
| Dilan Castillo | Terminó la tarea 2.1 y abrió su Pull Request. | Revisar el PR de la tarea 1.1 e iniciar la 2.2 cuando se fusione. | Ninguno |
| Santiago Cueno | Revisó los PR de las tareas 1.1 y 2.1. | Aprobar los PR y preparar el trabajo de la tarea 2.4. | Ninguno |

**Estado del tablero:** To Do: 16 · In Progress: 2/3 (1.2, 1.4) · Code Review: 2/2 (1.1, 2.1) · Done: 0
**Acuerdos y acciones:**
- Kevin documentará en el PR de 1.4 cómo configurar `jwt.secret` para que el resto del equipo lo replique.

---

### Daily #3 — Lunes 28/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 15 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Pull Request de la tarea 1.2 en revisión. | Tarea 1.3: DTOs, validaciones y excepción de dominio. | Ninguno |
| Kevin Lucumi | `JwtService` genera y valida tokens; faltan pruebas manuales. | Terminar la tarea 1.4 y abrir el PR. | Ninguno |
| Dilan Castillo | Tarea 2.2 en progreso; el PR de 2.1 quedó fusionado. | Terminar `ProductoRepository` (2.2). | Ninguno |
| Santiago Cueno | Aprobó y fusionó los PR de las tareas 1.1 y 2.1. | Revisar el PR de la tarea 1.2. | Ninguno |

**Estado del tablero:** To Do: 14 · In Progress: 3/3 (1.3, 1.4, 2.2) · Code Review: 1/2 (1.2) · Done: 2 (1.1, 2.1)
**Acuerdos y acciones:**
- Mantener PR pequeños para no acumular revisiones.

---

### Daily #4 — Miércoles 30/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 14 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Tarea 1.2 fusionada; el PR de la 1.3 quedó abierto. | Revisar el PR de 1.4 y dejar listo el esqueleto de las pruebas de HU-01 (tarea 1.7). | Conflictos de merge leves al integrar 1.2 y 2.2 en `develop`. |
| Kevin Lucumi | Tarea 1.4 en Code Review. | Tareas 1.5 (`ProductorService`) y 2.3 (filtro JWT y `SecurityConfig`). | Ninguno |
| Dilan Castillo | Tarea 2.2 fusionada. | Tarea 1.6: `ProductorController` y `GlobalExceptionHandler`. | Ninguno |
| Santiago Cueno | Aprobó los PR de 1.2 y 2.2. | Revisar los PR de 1.3 y 1.4. | Ninguno |

**Estado del tablero:** To Do: 11 · In Progress: 3/3 (1.5, 1.6, 2.3) · Code Review: 2/2 (1.3, 1.4) · Done: 4 (1.1, 1.2, 2.1, 2.2)
**Acuerdos y acciones:**
- Los conflictos de merge se resuelven con `rebase` sobre `develop` antes de abrir el PR.

---

### Daily #5 — Viernes 02/10/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 14 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Tarea 1.3 fusionada. | Tarea 4.1: índices de base de datos (migración V3). | Ninguno |
| Kevin Lucumi | Tarea 1.4 fusionada; PR de 1.5 abierto; 2.3 en progreso. | Terminar el filtro JWT y `SecurityConfig` (2.3). | Ninguno |
| Dilan Castillo | PR de 1.6 abierto. | Iniciar la lógica de filtrado (4.2) cuando se fusione 1.6. | Ninguno |
| Santiago Cueno | Avanzó en `ProductoService` (2.4) con el repositorio simulado. | Continuar 2.4. | La tarea 2.4 depende de que 2.3 esté lista para probar de extremo a extremo. |

**Estado del tablero:** To Do: 9 · In Progress: 3/3 (2.3, 2.4, 4.1) · Code Review: 2/2 (1.5, 1.6) · Done: 6 (1.1, 1.2, 1.3, 1.4, 2.1, 2.2)
**Acuerdos y acciones:**
- Kevin prioriza el PR de 2.3 para desbloquear a Santiago.

---

### Daily #6 — Lunes 05/10/2026
*Se superó el WIP Limit de Code Review*

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 15 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | PR de la tarea 4.1 abierto. | Comenzar las pruebas de HU-01 (tarea 1.7). | Ninguno |
| Kevin Lucumi | Tareas 1.5 y 1.6 fusionadas; PR de 2.3 abierto. | Tarea 2.5: endpoint `POST /api/v1/productos`. | Ninguno |
| Dilan Castillo | Tarea 1.6 fusionada. | Tarea 4.2: lógica de filtrado en `ProductoService`. | Ninguno |
| Santiago Cueno | PR de la tarea 2.4 abierto. | Revisar PR para bajar la cola y estudiar `@WithMockUser` para las pruebas de 2.6. | Necesita simular el usuario autenticado (`SecurityContext`) en las pruebas de HU-02. |

**Estado del tablero:** To Do: 6 · In Progress: 3/3 (1.7, 2.5, 4.2) · Code Review: 3/2 ⚠ (2.3, 2.4, 4.1) · Done: 8 (1.1–1.6, 2.1, 2.2)
**Acuerdos y acciones:**
- No se abren PR nuevos hasta que Code Review baje a 2.
- Dilan revisa los PR pendientes hoy mismo.

---

### Daily #7 — Miércoles 07/10/2026
*Última Daily antes de la Review*

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 10 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Tarea 4.1 fusionada; PR de las pruebas 1.7 abierto. | Auditoría Checkstyle de HU-01 (1.8). | Ninguno |
| Kevin Lucumi | Tareas 2.3 y 2.5 fusionadas. | Auditoría Checkstyle de HU-02 (2.7). | Ninguno |
| Dilan Castillo | Tareas 4.2 y 4.3 fusionadas. | Revisar los PR de 1.7 y 2.6 y verificar la integración con PostgreSQL local. | Ninguno |
| Santiago Cueno | Tarea 2.4 fusionada; PR de 2.6 abierto usando `@WithMockUser`. | Tarea 4.4: pruebas de HU-04. | Ninguno |

**Estado del tablero:** To Do: 1 (4.5) · In Progress: 3/3 (1.8, 2.7, 4.4) · Code Review: 2/2 (1.7, 2.6) · Done: 14
**Acuerdos y acciones:**
- Cerrar HU-01, HU-02 y HU-04 el 08/10 y 09/10 y preparar el entorno local para la demo.

---

## 5. Impedimentos y su resolución

| # | Impedimento | Daily donde se reportó | Responsable | Resolución | Fecha de cierre |
| --- | --- | --- | --- | --- | --- |
| 1 | Configuración de JWT: API de jjwt 0.12 y lectura del secreto desde variable de entorno | #2 | Kevin Lucumi | Se siguió la documentación oficial y se dejó el procedimiento en el PR de la tarea 1.4 | 28/09/2026 |
| 2 | Conflictos de merge leves al integrar 1.2 y 2.2 | #4 | Carlos Rosales / Dilan Castillo | `rebase` sobre `develop` y pruebas locales antes del PR | 30/09/2026 |
| 3 | La tarea 2.4 dependía de que 2.3 estuviera lista | #5 | Santiago Cueno | Kevin priorizó el PR de 2.3 y se fusionó | 05/10/2026 |
| 4 | Simular el usuario autenticado en las pruebas de HU-02 | #6 | Santiago Cueno | Uso de `@WithMockUser` de Spring Security Test | 07/10/2026 |

## 6. Seguimiento de avance (burndown)

Las horas restantes descuentan únicamente las tareas en *Done* (PR fusionado). Los Story Points
se descuentan cuando una historia completa cumple la Definition of Done.

| Fecha | SP restantes | Horas restantes | Comentario |
| --- | --- | --- | --- |
| 24/09/2026 | 10 | 38 | Inicio del Sprint |
| 25/09/2026 | 10 | 38 | Primeros PR en revisión |
| 28/09/2026 | 10 | 33 | Fusionadas 1.1 y 2.1 |
| 30/09/2026 | 10 | 31 | Fusionadas 1.2 y 2.2 |
| 02/10/2026 | 10 | 27 | Fusionadas 1.3 y 1.4 |
| 05/10/2026 | 10 | 23 | Fusionadas 1.5 y 1.6; Code Review con 3 PR |
| 07/10/2026 | 10 | 11 | Restan pruebas y auditorías Checkstyle |
| 09/10/2026 | 0 | 0 | Cierre del Sprint: las 3 historias cumplen la DoD |
