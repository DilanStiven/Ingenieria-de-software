# Documentación de AgroValle Connect

Esta carpeta reúne especificaciones, registros de Sprint y documentos de diseño. Los artefactos
de planificación y diseño describen decisiones o propuestas de su fecha; no necesariamente
representan la implementación actual.

## Documentos

| Documento | Contenido |
| --- | --- |
| [`dod.md`](dod.md) | Definition of Done y criterios de calidad del equipo. |
| [`ieee29148-req01.md`](ieee29148-req01.md) | Requerimiento de registro de agricultores y escenarios BDD. |
| [`sprint-1-planning.md`](sprint-1-planning.md) | Alcance y tareas planificadas para el Sprint 1. |
| [`sprint-1-review.md`](sprint-1-review.md) | Resultado reportado y demostración del Sprint 1. |
| [`sprint-1-retrospective.md`](sprint-1-retrospective.md) | Retrospectiva y acciones de mejora del Sprint 1. |
| [`bitacora-daily-scrum.md`](bitacora-daily-scrum.md) | Registro disponible de las reuniones de seguimiento del Sprint 1. |
| [`kanban-policies.md`](kanban-policies.md) | Políticas de flujo y métricas; incluye las limitaciones de evidencia. |
| [`sprint-2-planning.md`](sprint-2-planning.md) | Propuesta de selección HU-06 para el Sprint 2; requiere decisiones y aprobación del equipo. |
| [`resumen-ejecutivo-sprint-2.md`](resumen-ejecutivo-sprint-2.md) | Estado ejecutivo del avance técnico verificable y pendientes; no inventa resultados de un Sprint no documentado. |
| [`evidencias/reporte-jacoco-java25-run-91.md`](evidencias/reporte-jacoco-java25-run-91.md) | Cobertura del reporte JaCoCo descargado del CI Java 25, con acceso al HTML completo y contadores agregados. |
| [`evidencias/anexo-evidencias-jacoco-kanban.pdf`](evidencias/anexo-evidencias-jacoco-kanban.pdf) | Anexo PDF con resultados JaCoCo y captura de los límites WIP visibles en GitHub Projects. |
| [`sprint-2-review-template.md`](sprint-2-review-template.md) | Plantilla vacía para documentar la Review real del Sprint 2. |
| [`sprint-2-retrospective-template.md`](sprint-2-retrospective-template.md) | Plantilla vacía para documentar la retrospectiva real del Sprint 2. |
| [`diseno/Documento_de_arquitectura.md`](diseno/Documento_de_arquitectura.md) | Arquitectura propuesta y contraste con el código actual. |
| [`diseno/Documento_de_normalizacion.md`](diseno/Documento_de_normalizacion.md) | Contrasta el modelo implementado con la propuesta académica normalizada. |
| [`diseno/Documento_de_prototipado.md`](diseno/Documento_de_prototipado.md) | Wireframes y flujos propuestos; no son pantallas implementadas. |
| [`diseno/Anexo_de_diagramas.md`](diseno/Anexo_de_diagramas.md) | Catálogo de diagramas y mockups. |
| [`diseno/schema.sql`](diseno/schema.sql) | DDL de referencia para el modelo JPA vigente, en una base vacía; no es una migración automática. |
| [`diseno/schema-academico-propuesto.sql`](diseno/schema-academico-propuesto.sql) | DDL separado del diseño académico propuesto de cinco entidades; no corresponde al esquema vigente. |

## Evidencia y estado

Las imágenes de diseño se encuentran en [`diseno/images/`](diseno/images/). Las capturas
operativas históricas que cita la Review del Sprint 1 no están incluidas en este repositorio; sus
resultados se conservan como información reportada en el documento, no como evidencia
reproducible disponible aquí. La carpeta [`evidencias/`](evidencias/) contiene capturas de
verificaciones realizadas el 22/10/2026 y el 28/10/2026, no evidencia histórica del Sprint 1:

- [Resumen de pruebas y cobertura](evidencias/validacion-pruebas-y-cobertura-2026-10-22.png).
- [Reporte JaCoCo completo de la ejecución CI #91](evidencias/reporte-jacoco-java25-run-91.md) ([ZIP descargable](evidencias/jacoco-java25-run-91.zip)).
- [Anexo PDF: cobertura JaCoCo y captura de WIP](evidencias/anexo-evidencias-jacoco-kanban.pdf).
- [Captura del tablero con WIP In Progress 3 y Code Review / Testing 2](evidencias/tablero-kanban-limites-wip-2026-10-28.png); muestra la configuración visible en ese momento, no su cumplimiento histórico.
- [Build exitoso y Checkstyle](evidencias/validacion-build-checkstyle-2026-10-22.png).
- [Captura histórica del Pull Request #56 con CI Java 25 aprobado y revisión pendiente al momento de la captura](evidencias/ci-java25-pr-56-2026-10-28.png).
- [Restricción única para la cédula en PostgreSQL](evidencias/pgadmin-restriccion-2026-10-28.png).
- [Tablas de `agrovalle_db` antes de crear `productos`](evidencias/pgadmin-tablas-2026-10-28.png).
- [Listado de reglas de protección para `main` y `develop`](evidencias/proteccion-ramas-main-develop-2026-10-28.png).
- [Regla de `develop`: resumen](evidencias/proteccion-develop-resumen-2026-10-28.png), [check de CI requerido](evidencias/proteccion-develop-check-ci-2026-10-28.png) y [opciones finales](evidencias/proteccion-develop-opciones-finales-2026-10-28.png). Las capturas muestran Pull Request obligatorio, una aprobación requerida y el check `Build, Checkstyle & JaCoCo Coverage`; también muestran desactivadas las opciones de force push y eliminación.
- [Regla de `main`: resumen](evidencias/proteccion-main-resumen-2026-10-28.png), [check de CI requerido](evidencias/proteccion-main-check-ci-2026-10-28.png) y [opciones finales](evidencias/proteccion-main-opciones-finales-2026-10-28.png), con la misma configuración visible que `develop`.

### Verificación local de PostgreSQL — 2026-10-22

La aplicación se inició con Java 25 contra `agrovalle_db` en PostgreSQL 17.11 y con
`spring.jpa.hibernate.ddl-auto=none` para impedir cambios automáticos de esquema durante la
prueba. El endpoint de registro respondió `409 Conflict` al recibir una cédula ya existente.
Después de detener la aplicación, una consulta de solo lectura confirmó un agricultor, ninguna
cédula duplicada y la restricción `uk_agricultor_cedula`; la tabla `productos` no existía y no fue
creada durante la prueba.

Este resultado documenta una verificación técnica actual, no una captura ni un resultado
histórico de la Review del Sprint 1. Las capturas compartidas durante esta revisión muestran el
respaldo y consultas actuales de pgAdmin; no sustituyen las evidencias históricas ausentes.

### Persistencia de productos en PostgreSQL — 2026-10-28

Antes del cambio se creó y validó un respaldo reciente de `agrovalle_db`. Después, los scripts
`V2__create_productos.sql` y `V3__index_productos_filtro.sql` se aplicaron manualmente y en una
sola transacción, porque Flyway no está configurado en la aplicación. La tabla `productos` quedó
con sus columnas, restricciones de cantidad y precio, llave foránea hacia `agricultor` e índice
de filtro.

Una prueba SQL transaccional insertó un producto temporal usando un agricultor existente,
confirmó que era visible dentro de la transacción y revirtió el cambio. La comprobación final de
la base real confirmó un agricultor, cero productos y cero filas de prueba; no se dejó información
temporal persistida. La captura de tablas enlazada arriba es anterior a esta migración y no
representa el esquema actual.

Una primera petición contra una copia desechable no devolvió respuesta HTTP y no fue posible
determinar si llegó a ejecutarse. La copia temporal se eliminó antes de consultar el marcador de
esa petición, así que su resultado queda indeterminado. En un segundo intento se preparó otra
base vacía, pero el lanzador Java no inició la aplicación: su compilación Maven usó el JDK
predeterminado 17.0.12, mientras el proyecto estaba configurado entonces para Java 25
(`release 25`). No se envió ninguna
petición en este segundo intento. La base y el archivo de configuración temporales se eliminaron,
y la base real no recibió solicitudes de prueba. Por tanto, el DDL y la inserción SQL reversible
están verificados; la persistencia mediante HTTP queda pendiente de una ejecución exitosa con la
versión Java 17 requerida por la guía académica.

El 29/10/2026 se corrigieron el `pom.xml`, el workflow y la documentación para volver a Java 17.
Los resultados y el reporte JaCoCo obtenidos con Java 25 que se describen a continuación son
evidencia histórica de esa configuración anterior, no validación de la configuración vigente.

### Verificación local de calidad — 2026-10-28

Con Java 25 se ejecutó `.\mvnw.cmd clean verify checkstyle:check`: 26 pruebas, cero fallos,
cero errores y una omitida porque requiere PostgreSQL local; Checkstyle reportó cero infracciones.
JaCoCo midió 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas. El `pom.xml`
ahora exige al menos 60 % en los tres contadores y la verificación Maven pasó.

Para conocer el contrato y la persistencia vigentes, priorizar el código fuente, los DTO y la
configuración de `src/main`. La aplicación usa Hibernate `ddl-auto=update`; los scripts
`src/main/resources/db/migration/V2__create_productos.sql` y `V3__index_productos_filtro.sql`
no se ejecutan automáticamente porque Flyway no está incluido como dependencia. El modelo de
cinco entidades y las pantallas web se conservan como propuestas académicas, separadas del
modelo JPA actualmente implementado.

La ejecución remota #91 de GitHub Actions para el [Pull Request #56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56)
finalizó correctamente el 28/10/2026: el workflow configuró Java 25 Temurin y pasaron compilación,
Checkstyle y JaCoCo. La [ejecución #91](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503)
corresponde al commit `0c9d664`; el reporte descargado midió 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas.
Al momento de la captura, el PR esperaba una aprobación. Después se integró en `develop` y luego
los cambios de `develop` llegaron a `main` mediante el PR #58. La captura enlazada arriba conserva
el estado y el check que tenía el PR en ese momento.

La ejecución anterior #86 en `main`, commit
[`f648ba7`](https://github.com/DilanStiven/Ingenieria-de-software/commit/f648ba77d36b8876d35c377cef766529538a1f82),
terminó correctamente el 19/10/2026 usando Java 17; no validaba Java 25. Las capturas de
configuración verifican que tanto `main` como `develop` exigen Pull Request, una aprobación y el
check `Build, Checkstyle & JaCoCo Coverage`; en ambas se muestran desactivados force push y
eliminación.

### Pendiente para la entrega formal

- Configurar el depurador con JDK 17 y volver a realizar la prueba HTTP de persistencia contra una
  base desechable.
- Ejecutar nuevamente el CI remoto con Java 17 después de integrar la corrección de versión; el
  run #91 solo acredita la configuración histórica de Java 25.
- Realizar el Planning real del Sprint 2, confirmar o cambiar HU-06, tomar decisiones pendientes,
  y completar Review/retrospectiva con fechas y resultados reales. Las tres revisiones adicionales
  de avance reportadas por el equipo se consignan sin inventar sus fechas.
- Preparar el PDF de entrega con los enlaces, la evidencia CI requerida y el resumen del trabajo
  que efectivamente se complete. La falta de la línea de tiempo histórica Kanban queda declarada
  como limitación, no se presenta como si existiera una captura.
