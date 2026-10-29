# Sprint 2 Review — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Asignatura | Ingeniería de Software II — UNIAJC |
| Equipo | Grupo 5 |
| Sprint | Sprint 2 (Bloque 2, Segundo Corte) |
| Fechas del Sprint | [completar: DD/MM/2026 – DD/MM/2026] |
| Fecha y modalidad de la Review | [completar con la fecha real de la reunión] |
| Asistentes | [completar con quienes asistieron] |

> **Alcance de este documento:** el equipo describe en tercera persona el incremento técnico que existe en el
> repositorio y cita la evidencia que lo respalda. Los campos entre corchetes se completan con los datos de la
> reunión de Review. El resto se puede comprobar en el código, en GitHub Actions y en la carpeta `evidencias/`.

## 1. Sprint Goal y resultado

**Sprint Goal:** el equipo consolidó el incremento del Sprint 1 con refactorización SOLID, patrones GoF de
comportamiento, pruebas automatizadas con cobertura mínima de 60 %, un pipeline de Integración Continua y una
gestión visual del flujo con límites de trabajo en progreso.

**Resultado:** el equipo reportó el objetivo como cumplido en los puntos técnicos verificables de la sección 2.
La Historia HU-06 permanece como candidata del Product Backlog y no se declara terminada
(véase [`sprint-2-planning.md`](sprint-2-planning.md)).

## 2. Incremento entregado y evidencia

| Elemento | Qué se implementó | Evidencia |
| --- | --- | --- |
| Patrón Strategy (GoF) | Interfaz `CalculoFleteStrategy` con estrategias de flete por municipio (Buga, Dagua y Palmira), consumidas por `CalculadorTarifaService`. Permite agregar municipios sin modificar código probado (OCP). | `src/main/java/.../strategies/`, `CalculadorTarifaServiceTest` |
| Patrón Observer (GoF) | `ProductoService` publica `ProductoPublicadoEvent` mediante `ApplicationEventPublisher`; `ProductoPublicadoListener` reacciona de forma desacoplada con `@EventListener`. | `src/main/java/.../events/`, `ProductoServicePublicarTest` |
| Repository, DTO e IoC | Repositorios `JpaRepository`, DTO de solicitud y respuesta, e inyección de dependencias por constructor. | `repositories/`, `dtos/`, [`Documento_de_arquitectura.md`](diseno/Documento_de_arquitectura.md) |
| Catálogo | Consulta de productos con filtros por municipio y categoría, y consulta de categorías. | `ProductoController`, `CategoriaController`, `ProductoControllerTest` |
| Pruebas | Pruebas unitarias con Mockito y pruebas de capa REST con MockMvc, en JUnit 5. | `src/test/java/` |
| Cobertura | JaCoCo configurado con mínimo de 60 % en instrucciones, ramas y líneas (`jacoco:check` en `verify`). El artefacto del run #101 de Actions registró 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas. | [`pom.xml`](../pom.xml), [`evidencias/reporte-jacoco-java25-run-91.md`](evidencias/reporte-jacoco-java25-run-91.md) (medición histórica), run #101 |
| Integración Continua | Workflow `.github/workflows/ci.yml` con Temurin 17: compilación, Checkstyle y `verify` con JaCoCo, en push y Pull Request hacia `main` y `develop`. | [`ci.yml`](../.github/workflows/ci.yml), run #101 |
| Protección de ramas | Reglas en `main` y `develop`: Pull Request obligatorio, una aprobación y check `Build, Checkstyle & JaCoCo Coverage`; force push y eliminación desactivados. | [`evidencias/proteccion-*`](evidencias/) |
| Tablero Kanban | Límites WIP: *In Progress* ≤ 3 y *Code Review / Testing* ≤ 2. | [`evidencias/tablero-kanban-limites-wip-2026-10-28.png`](evidencias/tablero-kanban-limites-wip-2026-10-28.png) |
| Colaboración en Pull Requests | El historial de GitHub registra Pull Requests del Bloque 2 de varios integrantes (DilanStiven, santiageao y Steven-L777), entre ellos CI (#53), entrega del Segundo Corte (#54), release del Bloque 2 (#55) y HU-04 (#57). | [`bitacora-daily-scrum.md`](bitacora-daily-scrum.md), sección 7.1 |
| Corrección de versión | Una actualización automática a Java 25 se integró el 28/10/2026 (PR #56). El equipo la revirtió el 29/10/2026 en el PR #60 para cumplir la guía académica, que exige Java 17. | Historial de `main` en GitHub |

## 3. Demostración y retroalimentación

| Escenario demostrado | Resultado observado | Retroalimentación | Acción acordada |
| --- | --- | --- | --- |
| Registro de agricultor y obtención de token | [completar] | [completar] | [completar] |
| Publicación de producto con evento Observer | [completar] | [completar] | [completar] |
| Cálculo de flete por municipio (Strategy) | [completar] | [completar] | [completar] |
| Filtro del catálogo por municipio y categoría | [completar] | [completar] | [completar] |

## 4. Calidad del incremento

| Verificación | Comando | Resultado |
| --- | --- | --- |
| Compilación, pruebas, cobertura y estilo | `./mvnw clean verify checkstyle:check` (Windows: `.\mvnw.cmd ...`) | El run #101 de GitHub Actions terminó en verde con Java 17. Una ejecución local con Java 17 queda por registrar: [completar]. |

## 5. Trabajo no terminado

| Elemento | Motivo | Decisión para el backlog |
| --- | --- | --- |
| HU-06 — Registro de Vehículos de Carga | No superó la Definition of Ready: faltan decisiones sobre roles y propiedad del vehículo. | Se mantiene en el Product Backlog para el siguiente Sprint. |
| Prueba HTTP de persistencia de productos | Pendiente de ejecución local con JDK 17 contra una base desechable. | Se registra como tarea del siguiente Sprint. |
