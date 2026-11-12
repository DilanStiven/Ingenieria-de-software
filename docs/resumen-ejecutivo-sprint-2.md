# Resumen ejecutivo — Sprint 2

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Equipo | Grupo 5 |
| Corte de información | 28 de octubre de 2026 |
| Estado | Resumen del avance técnico verificable; no sustituye la Review de un Sprint cerrado. |

## Síntesis

AgroValle Connect cuenta con una API REST para el registro de agricultores, autenticación JWT y publicación/consulta de productos. El estado técnico verificado incluye persistencia PostgreSQL para agricultores y productos, reglas de integridad para el esquema de productos y pruebas automatizadas. El corte original de este resumen (28/10/2026) documentó HU-06 como candidata. En una aclaración posterior registrada el 9/11/2026, Dilan Castillo informó que en el Planning se eligieron HU-03 y HU-06. Esta precisión no modifica el corte original ni presenta las historias como terminadas durante Sprint 2.

## Avances y calidad comprobados

- Se verificó el esquema PostgreSQL de productos y una inserción SQL temporal dentro de una transacción revertida. La comprobación final dejó cero productos persistidos. La persistencia de productos mediante una petición HTTP continúa pendiente de verificación.
- En la validación local con Java 25, `clean verify checkstyle:check` terminó correctamente: 26 pruebas, cero fallos, cero errores y una omitida. Checkstyle reportó cero infracciones. JaCoCo midió 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas; se configuró un mínimo de 60 % para cada contador.
- El [workflow de GitHub Actions #91](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503), ejecutado con Temurin Java 25 para el [Pull Request #56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56), aprobó compilación, Checkstyle y JaCoCo. Su reporte real registra 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas. El [informe legible](evidencias/reporte-jacoco-java25-run-91.md) enlaza el ZIP completo del artefacto de Actions.
- Las reglas de protección de `main` y `develop` muestran Pull Request obligatorio, una aprobación requerida y el check de CI; las opciones mostradas también desactivan force push y eliminación. La captura archivada evidencia los límites visibles: 3 en *In Progress* y 2 en *Code Review / Testing*; no acredita cumplimiento histórico durante todo el Sprint.

## Seguimiento técnico registrado el 9/11/2026

El equipo implemento las Historias HU-03 y HU-06; adicionalmente, HU-07 se implementó
el 9/11/2026 con asistencia de IA a solicitud suya. Estas notas posteriores no cambian el corte
del 28/10 ni los resultados de la Review del Sprint 2. La verificación posterior
`.\mvnw.cmd clean verify checkstyle:check` pasó con 71 pruebas, cero fallos, una omitida y cero
infracciones de Checkstyle.

- **HU-03:** endpoint para promedio de precios por producto en las últimas 24 horas. Falta integrar
  el registro de transacciones con ventas completadas y validar persistencia con PostgreSQL real.
- **HU-06:** registro de vehículos para agricultores y transportistas. Las reglas provisionales de
  tipo, placa, capacidad y estado deben confirmarse con el equipo/Product Owner.
- **HU-07:** orden logística mínima, alistamiento, asignación de vehículo/ruta y guía de despacho.
  La liberación del vehículo tras la entrega queda fuera de HU-07 y puede acordarse como alcance
  futuro; no es requisito de HU-06.

## Pendientes y conclusión

El PR #56 sigue abierto hacia `develop` y requiere la aprobación configurada; por tanto, el CI verde valida la rama del PR, no una integración en `main`. La selección del Sprint Goal y las historias, las fechas, los puntos comprometidos/completados, la retroalimentación y los resultados de una Review/retrospectiva reales deben confirmarse con el equipo antes de reportarlos como hechos. La validación HTTP de productos continúa pendiente.

**Conclusión:** el proyecto tiene evidencia histórica favorable de compilación, pruebas, estilo y cobertura con Java 25. La configuración actual se alineó a Java 17 para cumplir la guía académica, por lo que esa evidencia histórica no valida la configuración vigente; se debe volver a ejecutar CI con Java 17. La selección de HU-03 y HU-06 se registra como declaración posterior de Dilan; fechas, Sprint Goal, capacidad, resultados y cierre de historias durante el Sprint deben sustentarse con evidencia del equipo. Las implementaciones de HU-03, HU-06 y HU-07 descritas en el seguimiento técnico son posteriores al corte original y no cuentan todavía con verificación de persistencia PostgreSQL real.

## Enlaces

- [Repositorio AgroValle Connect](https://github.com/DilanStiven/Ingenieria-de-software)
- [Tablero GitHub Projects](https://github.com/users/DilanStiven/projects/2)
- [Pull Request #56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56)
- [GitHub Actions #91 — Java 25](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503)
- [Reporte JaCoCo de la ejecución #91](evidencias/reporte-jacoco-java25-run-91.md)
- [Anexo PDF con JaCoCo y captura Kanban](evidencias/anexo-evidencias-jacoco-kanban.pdf)
