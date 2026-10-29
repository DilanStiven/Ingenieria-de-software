# Resumen ejecutivo — Sprint 2

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Equipo | Grupo 5 |
| Corte de información | 28 de octubre de 2026 |
| Estado | Resumen del avance técnico verificable; no sustituye la Review de un Sprint cerrado. |

## Síntesis

AgroValle Connect cuenta con una API REST para el registro de agricultores, autenticación JWT y publicación/consulta de productos. El estado técnico verificado incluye persistencia PostgreSQL para agricultores y productos, reglas de integridad para el esquema de productos y pruebas automatizadas. La planificación disponible propuso HU-06 — Registro de Vehículos de Carga, pero la documenta expresamente como candidata: no hay constancia en este repositorio de que el equipo la aprobara como objetivo o compromiso del Sprint 2. Tampoco se registraron aquí las fechas, el Sprint Goal, la capacidad, las historias comprometidas ni los resultados de una Review real; por eso este resumen no atribuye esos datos ni presenta HU-06 como terminada.

## Avances y calidad comprobados

- Se verificó el esquema PostgreSQL de productos y una inserción SQL temporal dentro de una transacción revertida. La comprobación final dejó cero productos persistidos. La persistencia de productos mediante una petición HTTP continúa pendiente de verificación.
- Como evidencia histórica del 28/10/2026, la validación local con Java 25, `clean verify checkstyle:check`, terminó correctamente: 26 pruebas, cero fallos, cero errores y una omitida. Checkstyle reportó cero infracciones. JaCoCo midió 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas; se configuró un mínimo de 60 % para cada contador.
- El [workflow de GitHub Actions #91](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503), ejecutado con Temurin Java 25 para el [Pull Request #56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56), aprobó compilación, Checkstyle y JaCoCo. Sus métricas son evidencia de esa ejecución histórica y no validan la versión Java 17 requerida por la guía. El [informe JaCoCo](evidencias/reporte-jacoco-java25-run-91.md) enlaza el artefacto original.
- Las reglas de protección de `main` y `develop` muestran Pull Request obligatorio, una aprobación requerida y el check de CI; las opciones mostradas también desactivan force push y eliminación. La captura archivada evidencia los límites visibles: 3 en *In Progress* y 2 en *Code Review / Testing*; no acredita cumplimiento histórico durante todo el Sprint.

## Pendientes y conclusión

Al corte de este resumen, el PR #56 seguía abierto hacia `develop`; posteriormente se integró en `develop` y los cambios llegaron a `main` mediante el PR #58. La selección del Sprint Goal y las historias, las fechas, los puntos comprometidos/completados, la retroalimentación y los resultados de una Review/retrospectiva reales deben confirmarse con el equipo antes de reportarlos como hechos. La validación HTTP de productos continúa pendiente.

**Conclusión:** hay evidencia técnica histórica favorable de compilación, pruebas, estilo y cobertura con Java 25, pero la guía académica establece Java 17. La configuración actual se corrigió a Java 17; la validación local pasó y el CI del PR #60 también pasó con esa versión. Para cerrar un informe de Sprint 2 como Sprint ejecutado, falta incorporar los acuerdos y resultados reales del equipo; este documento mantiene explícita esa limitación y evita inventar métricas o decisiones.

### Actualización de configuración — 29 de octubre de 2026

La guía académica establece Java 17. El `pom.xml`, GitHub Actions y la documentación vigente se
alinearon a esa versión. En la copia basada en `main`, `clean verify checkstyle:check` terminó
con `BUILD SUCCESS`: 26 pruebas, cero fallos, cero errores y una omitida; Checkstyle reportó cero
infracciones. El CI del PR #60 también aprobó Build, Checkstyle y JaCoCo con Java 17.

## Enlaces

- [Repositorio AgroValle Connect](https://github.com/DilanStiven/Ingenieria-de-software)
- [Tablero GitHub Projects](https://github.com/users/DilanStiven/projects/2)
- [Pull Request #56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56)
- [GitHub Actions #91 — evidencia histórica de Java 25](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503)
- [Reporte JaCoCo de la ejecución #91](evidencias/reporte-jacoco-java25-run-91.md)
- [Anexo PDF con JaCoCo y captura Kanban](evidencias/anexo-evidencias-jacoco-kanban.pdf)
