# Sprint 2 Review — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Asignatura | Ingeniería de Software II — UNIAJC |
| Equipo | Grupo 5 |
| Sprint | Sprint 2 (Bloque 2, Segundo Corte) |
| Fechas del Sprint | 09/10/2026 – 29/10/2026 |
| Fecha y modalidad de la Review | 29/10/2026, 18:30 – 19:30 (Sincrónico virtual vía Google Meet) |
| Asistentes | Dilan Stiven Castillo (Scrum Master), Kevin Estiven Lucumi (Dev), Carlos Andres Rosales (Dev), Santiago Edilmo Hurtado (Dev) |

> **Alcance de este documento:** el equipo describe en tercera persona el incremento técnico consolidado en el
> repositorio oficial y cita la evidencia empírica que lo respalda. Todos los componentes han sido comprobados
> en el código fuente Java 17, en el pipeline de GitHub Actions y en los artefactos archivados en `docs/evidencias/`.

## 1. Sprint Goal y resultado

**Sprint Goal:** el equipo consolidó el incremento del Sprint 1 con refactorización orientada a los 5 principios SOLID, adopción de patrones GoF de comportamiento (Strategy y Observer), pruebas automatizadas con cobertura superior al 60 %, pipeline de Integración Continua en GitHub Actions y gestión visual del flujo Scrumban con límites de trabajo en progreso (WIP).

**Resultado:** el equipo reportó el objetivo como cumplido satisfactoriamente en todos los puntos técnicos verificables de la sección 2. La Historia HU-06 permanece como candidata priorizada del Product Backlog para el siguiente ciclo (véase [`sprint-2-planning.md`](sprint-2-planning.md)).

## 2. Incremento entregado y evidencia

| Elemento | Qué se implementó | Evidencia |
| --- | --- | --- |
| Patrón Strategy (GoF) | Interfaz `CalculoFleteStrategy` con estrategias de flete por municipio (Buga, Dagua y Palmira), consumidas por `CalculadorTarifaService`. Permite agregar municipios sin modificar código probado (cumplimiento de OCP). | `src/main/java/.../strategies/`, `CalculadorTarifaServiceTest` |
| Patrón Observer (GoF) | `ProductoService` publica `ProductoPublicadoEvent` mediante `ApplicationEventPublisher`; `ProductoPublicadoListener` reacciona de forma desacoplada con `@EventListener`. | `src/main/java/.../events/`, `ProductoServicePublicarTest` |
| Repository, DTO e IoC | Repositorios `JpaRepository`, DTO de solicitud y respuesta, e inyección de dependencias por constructor (DIP). | `repositories/`, `dtos/`, [`Documento_de_arquitectura.md`](diseno/Documento_de_arquitectura.md) |
| Catálogo | Consulta de productos con filtros por municipio y categoría, y consulta de categorías. | `ProductoController`, `CategoriaController`, `ProductoControllerTest` |
| Pruebas automatizadas | Pruebas unitarias aisladas con Mockito y pruebas de capa REST con MockMvc, en JUnit 5 con estructura AAA. | `src/test/java/` |
| Cobertura de código | JaCoCo configurado con umbral mínimo obligatorio de 60 % en instrucciones, ramas y líneas (`jacoco:check` en fase `verify`). El artefacto de la ejecución #103 de Actions registró 80,26 % de instrucciones, 73,08 % de ramas y 81,13 % de líneas. | [`pom.xml`](../pom.xml), ejecución #103 de GitHub Actions en rama `main` |
| Integración Continua | Workflow `.github/workflows/ci.yml` con Eclipse Temurin 17 LTS: compilación limpia, Checkstyle y verificación JaCoCo en cada push y PR hacia `main` y `develop`. | [`.github/workflows/ci.yml`](../.github/workflows/ci.yml), ejecución #103 |
| Protección de ramas | Reglas activas en `main` y `develop`: Pull Request obligatorio, al menos una aprobación de pares y check obligatorio `Build, Checkstyle & JaCoCo Coverage`; push directo desactivado. | [`evidencias/proteccion-*`](evidencias/) |
| Tablero Kanban | Configuración visible de límites WIP: *In Progress* ≤ 3 y *Code Review / Testing* ≤ 2 para controlar el flujo según la Ley de Little. | [`evidencias/tablero-kanban-limites-wip-2026-10-28.png`](evidencias/tablero-kanban-limites-wip-2026-10-28.png) |
| Colaboración en Pull Requests | El historial de GitHub registra Pull Requests del Bloque 2 con revisión de pares (DilanStiven, santiageao y Steven-L777), incluyendo CI (#53), entrega de arquitectura (#54), release Bloque 2 (#55), corrección Java 17 (#60) y cierre (#61). | [`bitacora-daily-scrum.md`](bitacora-daily-scrum.md), sección 7.1 |
| Corrección y alineación de versión | Reversión integral de Java 25 a Java 17 LTS en el PR #60 (29/10/2026) para asegurar conformidad absoluta con la guía académica de la asignatura. | Historial verificado de `main` en GitHub |

## 3. Demostración y retroalimentación

| Escenario demostrado | Resultado observado | Retroalimentación | Acción acordada |
| --- | --- | --- | --- |
| Registro de agricultor y obtención de token (`POST /api/v1/auth/register`) | Retorno de HTTP `201 Created` con token JWT válido (HMAC-SHA256) y validación de unicidad de cédula. Pruebas de integración (`AgricultorControllerTest`) pasando al 100%. | El contrato JSON de entrada y la seguridad por token aíslan de manera efectiva los recursos protegidos. | Mantener validación sintáctica mediante Bean Validation (`@Valid`) en todos los DTOs de solicitud. |
| Publicación de producto con evento Observer | Disparo sincrónico del evento `ProductoPublicadoEvent` desacoplado de la persistencia de cosechas; notificación capturada por el listener. | El desacoplamiento cumple con el patrón Observer sin sobrecargar la capa de servicio principal. | Incluir una prueba unitaria enfocada exclusivamente en el listener durante el siguiente ciclo. |
| Cálculo de flete por municipio (Strategy) | `CalculadorTarifaService` selecciona dinámicamente la estrategia de flete (Buga, Dagua, Palmira) retornando el costo exacto según el municipio. | Se evidencia la extensibilidad sin modificación de código fuente preexistente (Principio Open/Closed). | Incorporar municipios adicionales del Valle (Jamundí, Tuluá) bajo la misma interfaz `CalculoFleteStrategy`. |
| Filtro del catálogo por municipio y categoría (`GET /api/v1/productos`) | Filtrado relacional preciso por municipio y categoría con retorno de HTTP `200 OK` y listado estructurado de productos disponibles. | La consulta derivada responde de manera óptima y desacoplada del modelo JPA mediante DTOs. | Consolidar la restauración de `CategoriaRepository` en `main` mediante la finalización del PR #59. |

## 4. Calidad del incremento

| Verificación | Comando | Resultado |
| --- | --- | --- |
| Compilación, pruebas, cobertura y estilo | `./mvnw clean verify checkstyle:check` (Windows: `.\mvnw.cmd ...`) | La ejecución #103 de GitHub Actions en la rama `main` finalizó con estado **Success** utilizando Java 17. Se validó compilación limpia, 0 advertencias de Checkstyle, suite de pruebas unitarias/integración en verde y superación del umbral JaCoCo (80,26 % instrucciones, 73,08 % ramas, 81,13 % líneas). |

## 5. Trabajo no terminado

| Elemento | Motivo | Decisión para el backlog |
| --- | --- | --- |
| HU-06 — Registro de Vehículos de Carga | No superó la Definition of Ready: se identificó la necesidad de definir previamente tipos de vehículos y tarifas de transporte asociadas. | Se mantiene priorizada en el Product Backlog para el Sprint 3. |
| Prueba HTTP de persistencia de productos | Requiere inicialización de contenedor o instancia dedicada de PostgreSQL para pruebas end-to-end con datos transaccionales reales. | Se programa como tarea técnica al inicio del Sprint 3. |
