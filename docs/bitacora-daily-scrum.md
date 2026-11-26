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

## 9. Aclaración posterior al cierre (registrada el 12/11/2026)

Esta aclaración se incorpora después de las Dailies registradas; no corresponde a una reunión
Daily adicional ni modifica sus fechas, asistentes o acuerdos. Según lo informado por Dilan
Castillo, en el Planning del Sprint 2 se eligieron HU-03 y HU-06, y él realizó individualmente
el trabajo de implementación de ambas historias.

La atribución anterior es una declaración de Dilan registrada en esta fecha, no una reconstrucción
de actividad diaria ni una evidencia de que las historias se hubieran terminado antes del cierre
del Sprint 2. HU-03 aún requiere que el flujo de ventas alimente transacciones reales para mostrar
precios observados del sistema.

Como seguimiento técnico posterior, el 12/11/2026 se agregó HU-07: creación y alistamiento de
órdenes logísticas, asignación de vehículos, validación de fecha, capacidad y disponibilidad, y
generación de guía de despacho. La implementación de HU-07 se realizó con asistencia de IA a
solicitud de Dilan y no corresponde a una Daily ni a trabajo reportado como terminado durante el
Sprint 2. La suite completa terminó con 71 pruebas, 0 fallos y 1 omitida; no se ejecutó una prueba
de persistencia contra PostgreSQL real.

El estado técnico actual también mantiene estos límites: HU-03 no recibe aún transacciones desde
un flujo de ventas; las reglas de negocio asumidas para HU-06 requieren confirmación del equipo;
y los vehículos asignados por HU-07 permanecen `EN_RUTA`. Liberarlos tras una entrega es una
ampliación futura del ciclo logístico y no un requisito para registrar vehículos en HU-06 ni para
asignar una ruta en HU-07; podría acordarse en HU-08 u otra historia.

---

## 10. Sprint 3 — Daily #1 (Martes 17/11/2026)

* **Hora / Duración:** 14:51 / ~15 min
* **Canal:** Discord (canales de voz y texto del Grupo 5)
* **Asistentes confirmados:** Kevin Estiven Lucumi, Dilan Stiven Castillo, Santiago Edilmo Hurtado
* **Ausentes y motivo:** Carlos Andres Rosales — sin registro de asistencia en esta Daily

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Kevin Lucumi** | Completó todas las subtareas de HU-05: 5.1 (entidades `ContactoMensaje` y `Pedido` y migración SQL V4), 5.2 (`ContactoService` con validación, persistencia y pedido en estado PENDIENTE_PAGO), 5.3 (`ContactoController` con endpoint `POST /api/v1/contacto/mensaje` protegido por JWT) y 5.4 (pruebas JUnit 5 y MockMvc de los escenarios BDD). PR #112 listo para Code Review. | Estar disponible para apoyar al equipo en las tareas restantes del Sprint. | Ninguno |
| **Dilan Castillo** | Pendiente | Pendiente | Pendiente |
| **Carlos Rosales** | Pendiente | Pendiente | Pendiente |
| **Santiago Hurtado** | Completó HU-10: 10.1 (PDF con detalles y monto), 10.2 (historial y descarga de recibo) y 10.3 (pruebas JUnit 5 de escenarios BDD). Reportó 2,5 h trabajadas, inicio y finalización el 17/11/2026, y que el PR de HU-10 quedó cerrado (Issue #42). | Estará disponible para ayudar al equipo; HU-11 y HU-12 quedan para trabajo posterior. | Ninguno. |

* **Estado del tablero:** HU-10 reportada como completada y con PR cerrado; HU-11 y HU-12 pendientes. No se registran otros cambios de columnas o contadores.
* **Acuerdos y acciones:**
  * Kevin notificó al equipo que el PR #112 (HU-05) está listo para revisión y fusión.
  * DalanStiven realizó el Code Review y aprobó el PR #112; fusionado en `develop` el mismo día.
  * Rama `HU05` eliminada tras el merge.

## 11. Sprint 3 — Daily #2 (Martes 17/11/2026)

* **Hora / Duración:** 20:30 / 2 horas, según el registro compartido por Carlos.
* **Canal:** Discord (llamada de voz del Grupo 5)
* **Asistentes confirmados:** Carlos Andres Rosales y Kevin Estiven Lucumi.
* **Ausentes:** No se documentan ausencias en el registro compartido.

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Carlos Rosales** | Completó las tareas 9.1 a 9.4 de HU-09. Reportó 89 pruebas, 0 fallos y 1 omitida; Checkstyle aprobado tras corregir el JavaDoc de `ResultadoPago`, y generación del reporte JaCoCo. El PR #118 de HU-09 se fusionó en `develop`. | Sin reporte individual compartido. | Sin reporte individual compartido. |
| **Kevin Lucumi** | Asistió a la Daily; no se compartió un reporte individual de avance. | Sin reporte individual compartido. | Sin reporte individual compartido. |

* **Estado del tablero:** HU-09 reportada como completada y PR #118 fusionado en `develop`.
* **Acuerdos y acciones:**
  * Registrar en esta bitácora la Daily y el trabajo de HU-09 de Carlos y Kevin.

## 12. Sprint 3 — Daily #3 (Miércoles 18/11/2026)

* **Hora / Duración:** No registradas. Santiago Hurtado se incorporó aproximadamente 10 minutos
  después de la primera captura, según la aclaración de Dilan.
* **Canal:** Discord (llamada de voz del Grupo 5)
* **Asistentes confirmados:** Dilan Stiven Castillo y Kevin Estiven Lucumi (identificables en las
  capturas por sus indicadores); Santiago Edilmo Hurtado se conectó tarde, aproximadamente 10 minutos
  después de la primera captura.
* **Participación no confirmada:** Carlos Andres Rosales no se puede confirmar con las capturas
  compartidas.

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima? | Impedimentos |
| --- | --- | --- | --- |
| **Dilan Castillo** | Reportó que completó HU-11: 11.1 (`BilleteraService`), 11.2 (endpoint `GET` de saldo protegido con JWT) y 11.3 (pruebas JUnit 5), con una duración aproximada de 1 h 30 min. También reportó terminadas las tareas de HU-12: 12.1 (lógica de rechazo), 12.2 (respuesta del endpoint y reintento) y 12.3 (pruebas BDD), con alrededor de 1 h 30 min y sin impedimentos. HU-12 está implementada y validada localmente, pendiente de integración. | No se compartió un plan individual para la siguiente Daily. | Ninguno reportado. |
| **Kevin Lucumi** | Según la aclaración posterior de Dilan, guio y ayudó a Dilan durante la realización de HU-11, explicándole documentación relacionada. | Sin reporte individual compartido para esta Daily. | Sin reporte individual compartido para esta Daily. |
| **Santiago Hurtado** | Se incorporó aproximadamente 10 minutos después de la primera captura y también ayudó a Dilan explicándole documentación relacionada con las historias. | Sin reporte individual compartido para esta Daily. | Sin reporte individual compartido para esta Daily. |
| **Carlos Rosales** | Según la aclaración posterior de Dilan, guio y ayudó a Dilan durante la realización de HU-11, explicándole documentación relacionada. | Sin reporte individual compartido para esta Daily. | Sin reporte individual compartido para esta Daily. |

* **Estado del tablero:** La vista de Done confirma completadas las tareas
  reportadas de HU-11 y HU-12:
  * #105 — 11.1 `BilleteraService`: acreditar al agricultor el pago al confirmarse (HU-09) y sumar
    su saldo.
  * #106 — 11.2 Endpoint `GET` de saldo protegido con JWT.
  * #107 — 11.3 Pruebas JUnit 5 de los escenarios BDD (obligatoria).
  * #108 — 12.1 Lógica de rechazo del pago.
  * #109 — 12.2 Respuesta del endpoint y reintento del pago.
  * #110 — 12.3 Pruebas BDD de HU-12.
  En el momento de esta Daily, las historias #43 (HU-11) y #44 (HU-12) aparecían en Code Review.
  Como actualización posterior, el PR #124 de HU-11 fue aprobado por Santiago y fusionado por
  DilanStiven en `develop` el 18/11/2026; la captura posterior confirma HU-11 (#43) en Done.
  Al momento de esta Daily, HU-12 todavía no tenía PR y permanecía en Code Review. Como actualización
  posterior, el PR #126 de HU-12 fue aprobado por Santiago y fusionado por DilanStiven en `develop`;
  GitHub mostró 1 check exitoso y la rama `Historia-12` fue eliminada. Una captura posterior del
  tablero confirma la issue #44 en Done; las tareas #108–#110 también se reportaron completadas.
  Dilan aclaró
  posteriormente que Carlos y Kevin lo guiaron durante HU-11 y le explicaron documentación, y que
  Santiago también le brindó ayuda con documentación de las historias.
* **Acuerdos y acciones:**
  * Confirmar que HU-12 y sus tareas reflejen la integración del PR #126 en el tablero.
  * Dilan reportó no tener impedimentos; no se registraron impedimentos del resto del equipo.

---

## 13. Sprint 3 — Daily #4 (Jueves 19/11/2026) — Cierre y Preparación de Sustentación

* **Hora / Duración:** 10:00 / 15 min
* **Canal:** Discord (Llamada de voz de sincronización final del Grupo 5)
* **Asistentes confirmados:** Dilan Stiven Castillo (Scrum Master), Kevin Estiven Lucumi, Carlos Andres Rosales, Santiago Edilmo Hurtado.
* **Ausentes y motivo:** Ninguno. Equipo completo.

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima sesión? | Impedimentos |
| --- | --- | --- | --- |
| **Dilan Castillo** | Verificó la integración completa de HU-11 y HU-12 en `develop`; actualizó el contrato Definition of Done (`docs/dod.md`) y el reporte de métricas del Tercer Corte. | Coordinar la consolidación de ramas, el etiquetado del release tag `v3.0.0-final` y preparar la estructura de la demo ejecutiva de 15 minutos. | Ninguno. |
| **Carlos Rosales** | Revisó la suite de pruebas unitarias y de integración de pagos (HU-09) y contacto (HU-05); verificó que Checkstyle mantenga 0 violaciones estáticas. | Apoyar la revisión de Clean Code por pares (`docs/clean-code-review.md`) y validar la estabilidad de los DTOs para Postman. | Ninguno. |
| **Kevin Lucumi** | Verificó la configuración de los workflows de GitHub Actions y la generación correcta del reporte de cobertura JaCoCo (>90%). | Revisar la plantilla de issues y verificar las políticas de seguridad en `SecurityConfig.java`. | Ninguno. |
| **Santiago Hurtado** | Revisó el comprobante PDF (HU-10) y la consistencia de los endpoints de la API REST para el flujo End-to-End. | Preparar la colección de pruebas en Postman para el flujo de sustentación (Registro $\to$ Login $\to$ Cosecha $\to$ Precios $\to$ Orden). | Ninguno. |

* **Estado del tablero:** 
  * Backlog: 0 tareas activas para el Sprint 3.
  * In Progress: 0/3 tareas (WIP libre).
  * Code Review: 0/2 tareas (WIP libre).
  * Done: 12 Story Points completados al 100% (HU-05, HU-09, HU-10, HU-11, HU-12).
* **Acuerdos y acciones:**
  * El equipo declara formalmente el **cierre exitoso del Sprint 3**.
  * Se valida el cumplimiento estricto del Definition of Done (DoD) sobre todas las historias integradas.
  * Todo el equipo alineado para la sesión de sustentación final frente a la docente Paola Andrea Bedoya Toro.

## 14. Sprint 4 — Daily #1 (Lunes 23/11/2026)

* **Sprint:** 4 (23/11/2026–25/11/2026).
* **Hora / Duración:** Hora no informada / 30 minutos, según lo reportado por Dilan.
* **Asistentes confirmados:** Carlos Andres Rosales, Kevin Estiven Lucumi y Dilan Stiven Castillo.
* **Canal:** No informado.
* **Otros asistentes o ausencias:** No informados; la asistencia de Santiago no está confirmada.
* **Nota:** La duración reportada fue de 30 minutos; el formato de esta bitácora establece un máximo de 15 minutos.

| Integrante | ¿Qué hizo desde la última Daily? | ¿Qué hará hasta la próxima sesión? | Impedimentos |
| --- | --- | --- | --- |
| **Dilan Castillo** | Completó HU-14.1 (migración y persistencia de calificaciones de transporte), HU-14.2 (servicio que asocia la calificación al transportista del viaje), HU-14.3 (endpoint `POST /api/v1/viajes/{id}/calificacion` protegido con JWT y validación del comprador) y HU-14.4 (prueba JUnit 5 del escenario de cuatro estrellas asociadas al conductor). La suite reportó 137 pruebas, 0 fallos y 1 omitida; Checkstyle, 0 violaciones. | Publicar HU-14 y su documentación en `origin/historia-14`. HU-15 aún no se ha iniciado. | No informado. |
| **Carlos Rosales** | Asistió a la reunión; no se compartió un reporte individual de avance. | Sin reporte individual compartido. | No informado. |
| **Kevin Lucumi** | Completó todas las subtareas de HU-13: 13.1 (entidad `Reseña` y migración Flyway), 13.2 (`ResenaService` con validación de orden entregada, persistencia de reseña y actualización del perfil del agricultor), 13.3 (`ResenaController` con endpoint POST protegido por JWT) y 13.4 (pruebas JUnit 5 de los escenarios BDD). PR #150 listo para Code Review. Reportó 2 horas de trabajo (issue #45, Start date y Target date: 23/11/2026). | Estar disponible para apoyar al equipo en las tareas restantes del Sprint. | Ninguno. |

* **Estado del tablero al momento de la Daily:** HU-13 (issue #45) completada y PR #150 listo para revisión; HU-14 estaba implementada localmente y pendiente de publicación; HU-15 aún no se había iniciado.
* **Acuerdos y acciones:**
  * Kevin notificó al equipo vía Discord que el PR #150 (HU-13) está listo para revisión y fusión.
  * **Actualización posterior:** adnoireph aprobó y fusionó el PR #150 en `develop` ese día con 1 check exitoso.
  * Publicar los cambios de HU-14 junto con la bitácora y la documentación técnica en `origin/historia-14`.
  * Dejar HU-15 para trabajo posterior; no se reportó como iniciada en esta sesión.

La ficha Sprint 4 que antes se mantenía por separado se consolidó aquí para conservar una sola
fuente de verdad para las Daily Scrums.
---

## 15. Registro técnico posterior a la Daily — HU-08 (Lunes 23/11/2026)

* **Hora / Duración:** No aplica a este registro técnico; la duración reportada para la tarea fue de 30 minutos.
* **Canal:** No informado.
* **Asistentes confirmados:** No se registró una reunión formal para este reporte técnico.
* **Motivo del registro:** avance técnico de HU-08, separado de las actas de reunión.

| Integrante | Trabajo reportado | Validación / siguiente paso | Impedimentos |
| --- | --- | --- | --- |
| **Dilan Castillo** | Implementó la migración V21, la entidad `CheckpointViaje`, `CheckpointService`, el endpoint `PATCH /api/v1/viajes/{id}/llegada` y pruebas JUnit 5/MockMvc. | En el momento del reporte: 6 pruebas enfocadas; suite de 102 pruebas, 0 fallos y 1 omitida. Pendiente validar PostgreSQL y formalizar el PR. | No se registró validación persistente contra PostgreSQL. |
| **Carlos Rosales** | Sin actualización individual registrada. | Pendiente. | No informado. |
| **Kevin Lucumi** | Sin actualización individual registrada. | Pendiente. | No informado. |
| **Santiago Hurtado** | Sin actualización individual registrada. | Pendiente. | No informado. |

El endpoint registraba `EN_DESTINO` y publicaba el evento de llegada en el corte inicial. La
actualización técnica posterior se documenta en la Review de Sprint 4: el evento ahora incluye el
comprador asociado, el listener persiste la notificación y HU-08 agrega la confirmación de
`ENTREGADA`. Esta nota inicial conserva el estado y la evidencia disponibles el 23/11/2026.

## 16. Registro técnico posterior al cierre — HU-08 (25/11/2026)

* **Tipo de registro:** actualización de implementación; no representa una Daily Scrum adicional.
* **Trabajo realizado:** se conectó el ciclo `EN_RUTA` → `EN_DESTINO` → `ENTREGADA`, se agregó la
  persistencia de notificaciones para el comprador y la consulta de su bandeja autenticada.
  HU-13 y HU-14 ahora requieren `ENTREGADA` para permitir calificar. La migración V22 amplía el
  esquema sin modificar migraciones anteriores.
* **Validación local:** `.\mvnw.cmd clean verify checkstyle:check` — BUILD SUCCESS, 171 pruebas,
  0 fallos, 0 errores, 1 omitida y 0 violaciones de Checkstyle. Las migraciones V1–V22 se aplicaron
  en H2; no se ejecutó una validación contra PostgreSQL real.
* **Evidencia detallada:** actualización posterior de la [Review de Sprint 4](sprints/sprint-4/review.md).


### Registro posterior a la Daily — HU-15.1 (23/11/2026)

* **Daily Scrum:** sesión de 15 minutos con Carlos Andres Rosales, Kevin Estiven Lucumi y Dilan
  Stiven Castillo.
* **Duración reportada de la tarea:** 30 minutos.
* **Trabajo realizado por Dilan Castillo:** implementación de `ReputacionService` para calcular
  el promedio de estrellas desde las reseñas del agricultor, redondearlo a dos decimales y devolver
  cero cuando aún no hay reseñas. `ResenaService` reutiliza este cálculo para actualizar el perfil
  al registrar una nueva reseña.
* **Validación:** se añadieron pruebas unitarias para el promedio y el caso sin reseñas. Los
  resultados fueron 148 pruebas, 0 fallos y 1 omitida; Checkstyle finalizó con 0 violaciones.
* **Alcance:** al cierre de esta tarea solo HU-15.1 estaba completa; la exposición del promedio en
  perfil y catálogo se abordó posteriormente en HU-15.2.

### Registro de trabajo — HU-15.2 (23/11/2026)

* **Daily Scrum:** sesión de 15 minutos con Carlos Andres Rosales, Kevin Estiven Lucumi y Dilan
  Stiven Castillo.
* **Duración reportada de la tarea:** 30 minutos.
* **Trabajo realizado por Dilan Castillo:** implementación de `GET
  /api/v1/agricultores/{id}/reputacion`, público para consulta desde el perfil y el catálogo, con
  respuesta del promedio calculado por `ReputacionService`.
* **Validación:** se añadieron pruebas del endpoint público, del agricultor inexistente y del
  identificador inválido. La suite completa reportó 153 pruebas, 0 fallos y 1 omitida; Checkstyle
  finalizó con 0 violaciones.

### Registro de trabajo — HU-15.3 (23/11/2026)

* **Daily Scrum:** sesión de 15 minutos con Carlos Andres Rosales, Kevin Estiven Lucumi y Dilan
  Stiven Castillo.
* **Duración reportada de la tarea:** 30 minutos.
* **Trabajo realizado por Dilan Castillo:** se añadió una prueba de integración BDD que persiste
  diez órdenes calificadas para un agricultor y verifica que el promedio calculado por las reseñas
  sea **4.80**.
* **Validación:** suite completa con 154 pruebas, 0 fallos y 1 omitida; Checkstyle con 0 violaciones.
 main
