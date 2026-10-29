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
- **Numeración:** los Issues #1–#20 corresponden a las tareas de las historias HU-01, HU-02 y HU-04 definidas en
  `docs/sprint-1-planning.md`.

## 2. Resumen de Dailies

| # | Fecha | Asistentes | Estado | Impedimentos clave |
| --- | --- | --- | --- | --- |
| 1 | Jueves 24/09/2026 | Los 4 integrantes | Realizada | Ninguno |
| 2 | Viernes 25/09/2026 | Los 4 integrantes | Realizada | Configuración inicial del entorno y dependencias JWT |
| 3 | Lunes 28/09/2026 | Los 4 integrantes | Realizada | Ninguno |
| 4 | Miércoles 30/09/2026 | Los 4 integrantes | Realizada | Conflictos de merge leves al integrar ramas |

> **Nota de trazabilidad (aclaración del equipo, 28/10/2026):** el equipo reporta siete espacios
> de seguimiento: cuatro reuniones de Sprint con bitácora detallada y tres revisiones adicionales
> para comprobar qué faltaba y qué estaba completo. Las cuatro entradas conservadas tienen fechas
> del 24/09 al 30/09, dentro del periodo asignado al Sprint 1 en su Planning; el equipo no recuerda
> con certeza si en su momento las identificó como Sprint 1 o Sprint 2. Se mantienen aquí por las
> fechas que quedaron escritas, sin atribuirles más contexto del que respaldan. De los otros tres
> seguimientos no se conservan fechas ni notas, por lo que no se reconstruyen. Tampoco se guardó
> una serie de capturas o historial de transiciones del tablero; los estados de las cuatro entradas
> son fotografías puntuales, no una línea de tiempo ni evidencia para calcular tiempos de ciclo.

---

## 3. Asignación y enfoque por historia

| Integrante | Historia / Responsabilidad Principal |
| --- | --- |
| **Carlos Andres Rosales Lara** | HU-01 (Registro y Dominio de Productor) / Coordinación e infraestructura base |
| **Dilan Stiven Castillo Valencia** | HU-01 (Endpoints y validaciones de usuario) |
| **Kevin Estiven Lucumi Polo** | HU-02 (Publicación de Cosechas / Configuración de Seguridad y JWT) |
| **Santiago Edilmo Hurtado Cuenu** | HU-04 (Filtrado y Consulta de Catálogo por Municipio/Categoría) |

---

## 4. Registro de Dailies Realizadas

### Daily #1 — Jueves 24/09/2026
*Kick-off del Sprint, justo después del Sprint Planning*

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 12 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Sprint Planning y estructuración inicial de los Issues del Sprint 1. | Tarea 1.1: entidad `Productor` y migración V1. | Ninguno |
| Kevin Lucumi | Lectura de requerimientos de la Historia 2 (HU-02) y revisión de la estructura del backend. | Investigar configuración de Spring Security e integrar `jjwt` para la HU-02. | Ninguno |
| Dilan Castillo | Lectura de los requisitos de HU-01 y del esquema inicial de base de datos. | Tarea 2.1: apoyo en entidad `Producto` y scripts de migración. | Ninguno |
| Santiago Hurtado | Revisión de los criterios de aceptación y filtros requeridos para la Historia 4 (HU-04). | Revisión del repositorio base, configuración de linter/Checkstyle y lectura del dominio. | Ninguno |

**Estado del tablero:** To Do: 18 · In Progress: 2/3 · Code Review: 0/2 · Done: 0  
**Acuerdos y acciones:**
- Un Pull Request por tarea, con al menos una aprobación de otro integrante.
- Definir flujo Git mediante Conventional Commits y ramas con convención `feature/HU-XX-...`.

---

### Daily #2 — Viernes 25/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 13 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Avanzó con la configuración de la entidad `Productor` (1.1). | Abrir Pull Request de la tarea 1.1 e iniciar `ProductorRepository`. | Ninguno |
| Kevin Lucumi | Lectura sobre implementación de filtros JWT en Spring Boot para HU-02. | Comenzar con el diseño de la estructura del `JwtService`. | Entender lectura de propiedades de secreto desde variables de entorno. |
| Dilan Castillo | Apoyó en la verificación del script Flyway / Migración inicial. | Apoyar en la revisión del PR de 1.1 y preparar los DTOs de usuario. | Ninguno |
| Santiago Hurtado | Definición inicial de las firmas de repositorio y DTOs requeridos para el filtrado (HU-04). | Diseñar la estructura de respuesta para la búsqueda por municipio y categoría. | Ninguno |

**Estado del tablero:** To Do: 16 · In Progress: 2/3 · Code Review: 1/2 · Done: 0  
**Acuerdos y acciones:**
- Kevin documentará la configuración necesaria para variables de entorno en el `README` local.

---

### Daily #3 — Lunes 28/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 15 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Integración de retroalimentación en el PR de la tarea 1.1. | Diseñar DTOs, validaciones y manejo de excepciones de dominio (HU-01). | Ninguno |
| Kevin Lucumi | Avances en la clase `JwtService` para la generación de tokens en HU-02. | Continuar con la lógica de validación y claims del token. | Ninguno |
| Dilan Castillo | Preparación de pruebas locales del flujo de registro. | Implementación de `ProductorController` y mapeo de endpoints. | Ninguno |
| Santiago Hurtado | Revisión del modelo de datos de `Producto` para soportar las consultas dinámicas de HU-04. | Diseñar la especificación/Query de búsqueda por categoría y municipio. | Ninguno |

**Estado del tablero:** To Do: 14 · In Progress: 3/3 · Code Review: 1/2 · Done: 1  
**Acuerdos y acciones:**
- Mantener los PRs concisos y enfocados para acelerar las revisiones.

---

### Daily #4 — Miércoles 30/09/2026

| Campo | Valor |
| --- | --- |
| Hora / Duración | 20:00 / 14 min |
| Asistentes | Carlos, Kevin, Dilan, Santiago |
| Ausentes y motivo | Ninguno |

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Consolidó cambios en `develop` y apoyó en la sincronización de ramas. | Preparar PR de actualización y pruebas unitarias de HU-01. | Conflictos de merge leves resueltos al rebasar sobre `develop`. |
| Kevin Lucumi | Avance en el servicio de JWT y estructuración del filtro de seguridad para HU-02. | Integrar el filtro en `SecurityFilterChain` y preparar `ProductoService`. | Ninguno |
| Dilan Castillo | Avance en endpoints de autenticación y registro de usuario. | Finalizar las validaciones de entrada (`@Valid`, Bean Validation). | Ninguno |
| Santiago Hurtado | Modelado de los métodos de filtrado y paginación para HU-04. | Implementación del servicio `ProductoConsultaService` para filtrado por catálogo. | Ninguno |

**Estado del tablero:** To Do: 12 · In Progress: 3/3 · Code Review: 1/2 · Done: 2  
**Acuerdos y acciones:**
- Ejecutar `git rebase develop` en las ramas locales antes de solicitar Code Review para evitar conflictos.

---

## 5. Impedimentos y su resolución

| # | Impedimento | Daily donde se reportó | Responsable | Resolución | Fecha de cierre |
| --- | --- | --- | --- | --- | --- |
| 1 | Configuración e integración de `jjwt` y variables de entorno | #2 | Kevin Lucumi | Se documentaron las variables necesarias en el archivo `.env.example` | 28/09/2026 |
| 2 | Conflictos leves de merge al integrar ramas de funcionalidad | #4 | Carlos Rosales / Dilan Castillo | Aplicación de `git rebase` sobre `develop` previo al envío de Pull Requests | 30/09/2026 |

---

## 6. Seguimiento de avance (burndown)

| Fecha | SP restantes | Horas restantes | Comentario |
| --- | --- | --- | --- |
| 24/09/2026 | 10 | 38 | Kick-off e inicio formal del Sprint 1 |
| 25/09/2026 | 10 | 38 | Configuración de entorno y primeros avances en HU-01 y HU-02 |
| 28/09/2026 | 10 | 34 | Avance en servicios base de tokens y repositorios |
| 30/09/2026 | 10 | 30 | Integración parcial en `develop` y desarrollo activo de HU-01, HU-02 y HU-04 |
---

# Bitácora — Sprint 2

> **Aviso de método:** esta sección separa dos cosas. La tabla 7 es un **registro de avance con fecha y
> evidencia** tomado de GitHub y de la carpeta `evidencias/`; no representa reuniones Daily. La tabla 8 se
> completa únicamente con las Dailies que el equipo realice y documente el mismo día.

## 6.1 Resumen del trabajo del Sprint 2 (según el equipo)

El Sprint 2 comenzó con el trabajo de diseño: el equipo elaboró los documentos de arquitectura, normalización,
prototipado, los diagramas y la especificación IEEE 29148 de la HU-01. En paralelo, el equipo corrigió errores
heredados del Sprint 1, entre ellos problemas de compatibilidad de versiones y fallos con la aprobación
requerida al subir Pull Requests. El desarrollo contó con apoyo intensivo de Copilot en Visual Studio Code, lo
que generó algunos errores que el equipo tuvo que detectar y corregir (por ejemplo, la actualización a Java 25
revertida el 29/10/2026). La docente conoce este contexto de trabajo.

## 7. Registro de avance verificable del Sprint 2

| Fecha | Avance | Evidencia |
| --- | --- | --- |
| 22/10/2026 | El equipo validó compilación, pruebas y Checkstyle de forma local. | `evidencias/validacion-build-checkstyle-2026-10-22.png`, `evidencias/validacion-pruebas-y-cobertura-2026-10-22.png` |
| 28/10/2026 | El equipo configuró los límites WIP del tablero y las reglas de protección de `main` y `develop`. | `evidencias/tablero-kanban-limites-wip-2026-10-28.png`, `evidencias/proteccion-*-2026-10-28.png` |
| 28/10/2026 | El equipo verificó el esquema de `productos` en PostgreSQL con una inserción reversible. | `evidencias/pgadmin-*-2026-10-28.png`, `docs/README.md` |
| 28/10/2026 | Se integró el PR #56 (verificación con Java 25). | Historial de `main` en GitHub |
| 29/10/2026 | Se integraron el PR #58 (`develop`) y el PR #60, que restauró Java 17 conforme a la guía. El run #101 de Actions terminó en verde. | Historial de `main` y pestaña Actions |

### 7.1 Pull Requests del trabajo de Bloque 2 (según GitHub)

| PR | Título | Autor | Estado | Fecha |
| --- | --- | --- | --- | --- |
| #48 | feat(HU-01): registro de agricultores con validaciones y token JWT | DilanStiven | Integrado | [completar desde GitHub] |
| #49 | feat: Implementación HU-02 Publicación de Productos | Steven-L777 | Cerrado sin integrar | [completar desde GitHub] |
| #50, #52 | Feature/hu02 publicacion productos | Steven-L777 | Integrados | [completar desde GitHub] |
| #51 | Feature/hu04 filtro categorias municipios | santiageao | Integrado | [completar desde GitHub] |
| #53 | ci: configuración de pipeline de GitHub Actions con JaCoCo y Checkstyle | DilanStiven | Integrado | [completar desde GitHub] |
| #54 | Entrega Segundo Corte: Arquitectura, Pruebas y Cobertura TDD | DilanStiven | Integrado | [completar desde GitHub] |
| #55 | Release: Integración Bloque 2 - Patrones GoF, Pruebas y CI/CD | DilanStiven | Integrado | [completar desde GitHub] |
| #56 | chore: validate Java 25 CI and finalize project evidence | DilanStiven | Integrado | 28/10/2026 |
| #57 | HU04: filtro de categorías por municipio | santiageao | Integrado | [completar desde GitHub] |
| #58 | Develop | DilanStiven | Integrado | 29/10/2026 |
| #60 | fix: restore Java 17 per academic guide | DilanStiven | Integrado | 29/10/2026 |

Fuente: lista de Pull Requests cerrados del repositorio. El estado "Integrado" corresponde al ícono de
fusión que muestra GitHub; las fechas se completan abriendo cada PR.

## 8. Dailies del Sprint 2

| # | Fecha | Asistentes | Qué se hizo / qué se hará / impedimentos | Estado del tablero |
| --- | --- | --- | --- | --- |
| 1 | [completar] | [completar] | [completar] | [completar] |
| 2 | [completar] | [completar] | [completar] | [completar] |
| 3 | [completar] | [completar] | [completar] | [completar] |
