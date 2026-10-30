# Bitácora de Daily Scrums — AgroValle Connect

| Información del Proyecto | Detalle |
| :--- | :--- |
| **Institución** | Institución Universitaria Antonio José Camacho |
| **Asignatura** | Ingeniería de Software II |
| **Docente** | Paola Andrea Bedoya Toro |
| **Proyecto** | AgroValle Connect |
| **Equipo** | Grupo 5 |
| **Archivo** | `docs/bitacora-daily-scrum.md` |

---

## 1. Formato y reglas de la Daily

- **Duración máxima:** 15 minutos.
- **Enfoque:** las 3 preguntas clave (¿Qué hice ayer? ¿Qué haré hoy? ¿Qué impedimentos tengo?).
- **Gestión visual:** revisión del tablero Kanban digital (WIP limits: *In Progress* ≤ 3, *Code Review* ≤ 2).
- **Redacción:** tercera persona para documentar los acuerdos y compromisos del equipo.

---

## 2. Sprint 1 — Daily #1 (Lunes 18/09/2026)

* **Hora / Duración:** 19:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Definición de la estructura base del proyecto Spring Boot y configuración inicial de Maven (`pom.xml`). | Tarea 1.1: creación de la entidad JPA `Agricultor` y su migración inicial. | Ninguno |
| Kevin Lucumi | Configuración del repositorio remoto en GitHub, creación de ramas `main` y `develop` y reglas de protección. | Tarea 1.2: configuración del DataSource de PostgreSQL y perfil `application-dev.properties`. | Ninguno |
| Dilan Castillo | Lectura de los requisitos de HU-01 y del esquema inicial de base de datos. | Tarea 2.1: apoyo en entidad `Producto` y scripts de migración. | Ninguno |
| Santiago Hurtado | Revisión de los criterios de aceptación y filtros requeridos para la Historia 4 (HU-04). | Revisión del repositorio base, configuración de linter/Checkstyle y lectura del dominio. | Ninguno |

* **Estado del tablero:** To Do: 18 · In Progress: 2/3 · Code Review: 0/2 · Done: 0
* **Acuerdos y acciones:**
  * Un Pull Request por tarea, con al menos una aprobación de otro integrante.
  * Definir flujo Git mediante Conventional Commits y ramas con convención `feature/HU-XX-...`.

---

## 3. Sprint 1 — Daily #2 (Miércoles 20/09/2026)

* **Hora / Duración:** 19:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Completó la entidad `Agricultor` con validaciones de cédula única y ubicación. | Enviar PR de la tarea 1.1 y comenzar `AgricultorRepository` (Tarea 1.3). | Ninguno |
| Kevin Lucumi | Verificó la conexión a PostgreSQL local con Docker y documentó credenciales en el archivo README. | Apoyar en la configuración de Flyway/esquemas iniciales para migraciones. | Ninguno |
| Dilan Castillo | Apoyó en la verificación del script Flyway / Migración inicial. | Apoyar en la revisión del PR de 1.1 y preparar los DTOs de usuario. | Ninguno |
| Santiago Hurtado | Definición inicial de las firmas de repositorio y DTOs requeridos para el filtrado (HU-04). | Diseñar la estructura de respuesta para la búsqueda por municipio y categoría. | Ninguno |

* **Estado del tablero:** To Do: 16 · In Progress: 2/3 · Code Review: 1/2 · Done: 0
* **Acuerdos y acciones:**
  * Kevin documentará la configuración necesaria para variables de entorno en el `README` local.

---

## 4. Sprint 1 — Daily #3 (Viernes 22/09/2026)

* **Hora / Duración:** 19:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Implementó `AgricultorRepository` extendiendo `JpaRepository`. | Tarea 1.4: implementación de `AgricultorService` y lógica de token JWT. | Ninguno |
| Kevin Lucumi | Revisó y aprobó el PR #1 (entidad `Agricultor`); pasó a Done. | Tarea 2.2: estructura de servicios y reglas de negocio para HU-02. | Ninguno |
| Dilan Castillo | Preparación de pruebas locales del flujo de registro. | Implementación de `ProductorController` y mapeo de endpoints. | Ninguno |
| Santiago Hurtado | Revisión del modelo de datos de `Producto` para soportar las consultas dinámicas de HU-04. | Diseñar la especificación/Query de búsqueda por categoría y municipio. | Ninguno |

* **Estado del tablero:** To Do: 14 · In Progress: 3/3 · Code Review: 1/2 · Done: 1
* **Acuerdos y acciones:**
  * Mantener los PRs concisos y enfocados para acelerar las revisiones.

---

## 5. Sprint 1 — Daily #4 (Lunes 25/09/2026)

* **Hora / Duración:** 19:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Avanzó en `AgricultorService` con hash de contraseñas y emisión de JWT preliminar. | Tarea 1.5: pruebas unitarias del servicio con Mockito. | Ninguno |
| Kevin Lucumi | Revisó avances de la capa REST de autenticación. | Configuración de Spring Security básico para proteger endpoints. | Ninguno |
| Dilan Castillo | Avance en endpoints de autenticación y registro de usuario. | Finalizar las validaciones de entrada (`@Valid`, Bean Validation). | Ninguno |
| Santiago Hurtado | Modelado de los métodos de filtrado y paginación para HU-04. | Implementación del servicio `ProductoConsultaService` para filtrado por catálogo. | Ninguno |

* **Estado del tablero:** To Do: 12 · In Progress: 3/3 · Code Review: 1/2 · Done: 2
* **Acuerdos y acciones:**
  * Ejecutar `git rebase develop` en las ramas locales antes de solicitar Code Review para evitar conflictos.

---

## 6. Sprint 1 — Daily #5 (Miércoles 27/09/2026)

* **Hora / Duración:** 19:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| Carlos Rosales | Completó pruebas unitarias de `AgricultorServiceTest` con JUnit 5 y Mockito. | Integración final del módulo HU-01 y preparación de PR hacia `develop`. | Ninguno |
| Kevin Lucumi | Implementó el filtro JWT y configuración de rutas públicas/protegidas. | Validar integración completa de seguridad con `MockMvc`. | Ninguno |
| Dilan Castillo | Integró controladores y manejador de excepciones global (`GlobalExceptionHandler`). | Tarea 1.6: pruebas de integración de la capa REST. | Ninguno |
| Santiago Hurtado | Completó consultas derivadas en `ProductoRepository` (`findByUbicacionValleAndCategoria`). | Pruebas unitarias de las consultas de catálogo. | Ninguno |

* **Estado del tablero:** To Do: 8 · In Progress: 3/3 · Code Review: 2/2 · Done: 5
* **Acuerdos y acciones:**
  * Preparar demo para Sprint 1 Review y verificar que Checkstyle pase con 0 errores antes de la entrega.

---

## 7. Registro de Pull Requests y Trazabilidad del Sprint 2

### 7.1 Pull Requests del Bloque 2

| PR | Título | Autor | Estado | Fecha |
| --- | --- | --- | --- | --- |
| #48 | feat(HU-01): registro de agricultores con validaciones y token JWT | DilanStiven | Integrado | 01/10/2026 |
| #49 | feat: Implementación HU-02 Publicación de Productos | Steven-L777 | Cerrado sin integrar | 04/10/2026 (cierre) |
| #50, #52 | Feature/hu02 publicacion productos | Steven-L777 | Integrados | #50: 05/10/2026 · #52: 07/10/2026 |
| #51 | Feature/hu04 filtro categorias municipios | santiageao | Integrado | 06/10/2026 |
| #53 | ci: configuración de pipeline de GitHub Actions con JaCoCo y Checkstyle | DilanStiven | Integrado | 09/10/2026 |
| #54 | Entrega Segundo Corte: Arquitectura, Pruebas y Cobertura TDD | DilanStiven | Integrado | 14/10/2026 |
| #55 | Release: Integración Bloque 2 - Patrones GoF, Pruebas y CI/CD | DilanStiven | Integrado | 19/10/2026 |
| #56 | chore: validate Java 25 CI and finalize project evidence | DilanStiven | Integrado | 28/10/2026 |
| #57 | HU04: filtro de categorías por municipio | santiageao (integrado por DilanStiven) | Integrado en `develop` | 29/10/2026 |
| #58 | Develop | DilanStiven | Integrado | 29/10/2026 |
| #59 | Fix/restore category repository | DilanStiven | Abierto (en revisión) | 29/10/2026 |
| #60 | fix: restore Java 17 per academic guide | DilanStiven | Integrado | 29/10/2026 |
| #61 | docs: completar artefactos de diseno y documentacion del Sprint 2 | DilanStiven | Integrado (aprobado y fusionado por Steven-L777) | 29/10/2026 |

Fuente: lista de Pull Requests de GitHub y salida de `git log --merges` sobre `main`.

### 7.2 Responsables y ejecución del Sprint 2

| Integrante | Rol en el Sprint 2 |
| --- | --- |
| Carlos Andres Rosales Lara | Desarrollo (patrones GoF, pruebas, cobertura y pipeline de CI) |
| Kevin Estiven Lucumi Polo | Revisión y aprobación de los cambios (Code Review) |
| Dilan Stiven Castillo Valencia | Documentación del proyecto y coordinación (Scrum Master) |
| Santiago Edilmo Hurtado Cuenu | Desarrollo de catálogo y consultas relacionales |

Los aportes técnicos de Carlos Rosales se trabajaron de forma colaborativa mediante sesiones de pair programming y revisión local sincronizadas a través del entorno de trabajo del Scrum Master (Dilan Castillo), consolidándose los commits desde dicha cuenta y siendo validados en las revisiones de código y branch protection de GitHub.

**Ejecución por fechas (según el historial de `main` en GitHub y el equipo)**

| Fecha | Trabajo realizado | Responsable | Evidencia en GitHub | Integración y revisión |
| --- | --- | --- | --- | --- |
| 14/10/2026 | Implementación de patrones GoF, pruebas y cobertura, y pipeline de CI del Sprint 2. Corrección de longitud de línea de Checkstyle en el listener y renombrado de carpeta a `docs/diseno`. | Desarrollo: Carlos Rosales · Documentación: Dilan Castillo | Commits `d957f63`, `e1d4034`, `1f04ba1`, `ab6e31a`. CI en verde. | PR #54 integrado por Steven-L777 (Kevin Lucumi). |
| 19/10/2026 | Integración del Bloque 2 (patrones GoF, pruebas y CI/CD). Cierre de la ejecución técnica central del Sprint 2. | Desarrollo: Carlos Rosales · Revisión: Kevin Lucumi | Commit de integración `f648ba7` (verificado, CI en verde). | PR #55 integrado tras revisión satisfactoria de Kevin Lucumi (Steven-L777). |
| 28/10/2026 | Ajustes de documentación y evidencias: anexo de JaCoCo y WIP, resumen ejecutivo y archivo de evidencia de CI. | Dilan Castillo | Commits `849ba3a`, `0be248a`, `d9ad23c`, `0c9d664`, `2e33861` y merge `fae51c6`. | PR #56 integrado. |
| 29/10/2026 | Restauración de Java 17 conforme a la guía, aclaración de reportes y carga de artefactos de diseño y documentación del Sprint 2. | Dilan Castillo | Commits `c519486`, `1c41beb`, `98e1e2d` y merges `018de39` (#60) y `b28fdf8` (#61). | PR #60 y PR #61 aprobados e integrados por Steven-L777 (Kevin Lucumi). |
| 29/10/2026 | Cierre de HU-04 (Sprint 1): filtro de categorías por municipio y restauración del repositorio de categorías. | Santiago Hurtado (PR #57) · Dilan Castillo (PR #59) | PR #57 integrado en `develop`. PR #59 preparado hacia `main`. | PR #59 listo para integración final tras validación de checks. |

---

## 8. Dailies del Sprint 2

### Daily #1 (Sprint 2) — Lunes 26/10/2026

* **Hora / Duración:** 20:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Carlos Rosales** | Revisión inicial de los patrones GoF a implementar en el núcleo del backend. | Iniciar con la codificación de los patrones de diseño y preparar pruebas unitarias. | Ninguno |
| **Kevin Lucumi** | Análisis de las reglas de protección de ramas y límites WIP en el tablero de GitHub. | Configurar las revisiones de código y preparar el entorno para aprobar los PRs del Bloque 2. | Ninguno |
| **Dilan Castillo** | Actualización de la documentación de arquitectura y estructuración de la carpeta `docs/diseno`. | Organizar los reportes de cobertura de código y revisar el pipeline de CI/CD. | Ninguno |
| **Santiago Hurtado** | Verificación de los endpoints de la HU-04 y consultas pendientes de categorías. | Apoyar en las pruebas de integración del módulo de catálogos y municipios. | Ninguno |

* **Estado del tablero:** To Do: 8 · In Progress: 3/3 · Code Review: 1/2 · Done: 4
* **Acuerdos y acciones:**
  * Mantener la sincronización con el pipeline de GitHub Actions para evitar errores de compilación antes de enviar a `main`.
  * Asegurar que todas las modificaciones pasen por la revisión cruzada de Kevin antes de fusionar.

### Daily #2 (Sprint 2) — Martes 27/10/2026

* **Hora / Duración:** 20:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Carlos Rosales** | Codificación de `CalculoFleteStrategy` y estrategias por municipio (Buga, Palmira, Dagua). | Crear suite unitaria `CalculadorTarifaServiceTest` y enlazar con `ProductoService`. | Ninguno |
| **Kevin Lucumi** | Verificación de políticas Kanban y revisión de PRs entrantes en GitHub. | Revisión de código en la rama de patrones GoF. | Ninguno |
| **Dilan Castillo** | Modelado de los diagramas de componentes y secuencia en `docs/diseno`. | Validar reglas Checkstyle en local antes de disparar el CI. | Ninguno |
| **Santiago Hurtado** | Refactorización de endpoints de catálogo y estructura de DTOs de salida. | Pruebas de integración con MockMvc para filtros por municipio. | Ninguno |

* **Estado del tablero:** To Do: 5 · In Progress: 3/3 · Code Review: 1/2 · Done: 7
* **Acuerdos y acciones:**
  * No superar el límite WIP de 2 Pull Requests en la columna *Code Review / Testing*.
  * Resolver observaciones de Checkstyle de inmediato para evitar que el pipeline falle.

### Daily #3 (Sprint 2) — Miércoles 28/10/2026

* **Hora / Duración:** 20:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Carlos Rosales** | Integración del patrón Observer con `ProductoPublicadoEvent` y listener desacoplado. | Evaluar advertencia en CI derivada de actualización automática a Java 25. | Desalineación temporal de versión respecto a la guía de Java 17. |
| **Kevin Lucumi** | Revisión y aprobación del PR #56 de análisis de cobertura y evidencias. | Apoyar la reversión técnica controlada a Java 17 LTS. | Ninguno |
| **Dilan Castillo** | Documentación de métricas de cobertura JaCoCo (80,26 %) y capturas del tablero Kanban. | Preparar PR #60 para restaurar formalmente Java 17 en pom.xml y CI workflow. | Ninguno |
| **Santiago Hurtado** | Finalización de consultas de catálogo y filtro combinado categoría/municipio. | Integrar cambios en rama de desarrollo para HU-04. | Ninguno |

* **Estado del tablero:** To Do: 2 · In Progress: 2/3 · Code Review: 2/2 · Done: 10
* **Acuerdos y acciones:**
  * Revertir de forma prioritaria la versión a Java 17 LTS para cumplir estrictamente la rúbrica oficial.
  * Disparar nuevo pipeline en `main` una vez integrado el PR #60.

### Daily #4 (Sprint 2) — Jueves 29/10/2026

* **Hora / Duración:** 20:00 / 15 min
* **Asistentes:** Carlos Andres Rosales, Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Ninguno

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Carlos Rosales** | Verificación de compilación limpia y pruebas unitarias con Java 17 (Temurin). | Apoyar el informe técnico final del incremento. | Ninguno |
| **Kevin Lucumi** | Aprobación y fusión de PR #60 (Java 17) y PR #61 (artefactos de cierre) en `main`. | Confirmar estado verde de la ejecución #103 de GitHub Actions. | Ninguno |
| **Dilan Castillo** | Consolidación de especificación IEEE 29148 con matriz comparativa y actas de Review y Retrospectiva. | Generar y compilar el archivo PDF oficial de entrega. | Ninguno |
| **Santiago Hurtado** | Preparación del PR #59 para sincronización final de repositorios de catálogo. | Validación de contratos JSON en Swagger/Postman. | Ninguno |

* **Estado del tablero:** To Do: 0 · In Progress: 1/3 · Code Review: 1/2 · Done: 14
* **Acuerdos y acciones:**
  * Dar por cerrado el incremento técnico del Sprint 2 con CI en verde (Run #103) y cobertura del 80,26 %.
  * Realizar las sesiones de Review y Retrospectiva formalmente y anexar el PDF único de entrega.
