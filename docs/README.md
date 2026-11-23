# Índice de Documentación de Ingeniería — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Estándar de Calidad:** ISO/IEC 25010 & ISO/IEC/IEEE 29148:2018  
**Ubicación:** `docs/README.md`  

---

## 1. Propósito y Estructura del Repositorio Documental

Esta carpeta centraliza todos los artefactos de ingeniería de software, especificaciones formales, registros metodológicos por sprint, diagramas de diseño y decisiones de arquitectura del proyecto **AgroValle Connect**. Toda la documentación evoluciona en texto plano Markdown (`.md`) junto al código fuente Java 17 / Spring Boot para garantizar auditabilidad, trazabilidad y control de versiones mediante Git commits.

---

## 2. Catálogo Maestro de Documentos

| Categoría | Documento | Descripción / Alcance Técnico |
|---|---|---|
| **Calidad y Contratos** | [`dod.md`](dod.md) | **Definition of Done (DoD):** Contrato de calidad técnica no negociable basado en el modelo ISO/IEC 25010, firmado por la célula Scrum. |
| **Calidad y Auditoría** | [`clean-code-review.md`](clean-code-review.md) | **Clean Code Review:** Hallazgos de tamaño de método e inyección corregidos; pendiente una nueva aprobación de pares. |
| **Gobernanza y Flujo** | [`kanban-policies.md`](kanban-policies.md) | **Políticas Kanban / Scrumban:** Políticas explícitas de entrada/salida por columna, WIP Limits (*In Progress: 3*, *Code Review: 2*) y Ley de Little. |
| **Gobernanza y Flujo** | [`bitacora-daily-scrum.md`](bitacora-daily-scrum.md) | **Bitácora de Daily Scrums:** Registro cronológico de sincronización diaria y resolución de impedimentos (Sprints 1 a 4). |
| **Operación y Evidencias** | [`evidencias/registro-incidencias-staging.md`](evidencias/registro-incidencias-staging.md) | **Registro de incidencias de staging:** síntomas, causas, soluciones y validación del despliegue en Render y GitHub Actions. |
| **Requerimientos** | [`requirements/ieee-29148-syrs-agrovalle.md`](requirements/ieee-29148-syrs-agrovalle.md) | **Especificación de Requerimientos (SyRS):** Redacción prescriptiva formal bajo el estándar internacional ISO/IEC/IEEE 29148:2018. |
| **Requerimientos** | [`requirements/product-backlog-bdd.md`](requirements/product-backlog-bdd.md) | **Product Backlog BDD:** 15 Historias de Usuario priorizadas con MoSCoW, estimadas con Fibonacci y especificadas con escenarios *Given-When-Then* (GWT). |
| **Arquitectura UML** | [`diseno/architecture/diagramas-uml.md`](diseno/architecture/diagramas-uml.md) | **Diagramas UML:** cuatro secuencias, dos colaboraciones, componentes y despliegue; el documento identifica las diferencias entre el flujo solicitado y el código vigente. |
| **Decisiones de Diseño** | [`diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md`](diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md) | **ADR-001:** Decisión implementada de Render y PostgreSQL gestionado para el ambiente de staging. |
| **Decisiones de Diseño** | [`diseno/architecture-decisions/ADR-002-estrategia-despliegue-produccion.md`](diseno/architecture-decisions/ADR-002-estrategia-despliegue-produccion.md) | **ADR-002:** Estrategia propuesta de despliegue en producción con Zero-Downtime y alta disponibilidad. |
| **Métricas Ágiles** | [`metrics/sprint-metrics-corte3.md`](metrics/sprint-metrics-corte3.md) | **Reporte de Métricas de Flujo (Corte 3):** Incluye tablas de Velocity, Burndown, CFD y Lead/Cycle Time; falta completar y respaldar los registros diarios y los gráficos exigidos. |
| **Informes Ejecutivos** | [`resumen-ejecutivo-sprint-2.md`](resumen-ejecutivo-sprint-2.md) | **Resumen Ejecutivo Sprint 2:** Informe de avance técnico verificable al cierre del Segundo Corte. |
| **Informes Ejecutivos** | [`resumen-ejecutivo-sprint-3.md`](resumen-ejecutivo-sprint-3.md) | **Resumen Ejecutivo Sprint 3:** Consolidado final del Tercer Corte, métricas de calidad y preparación para sustentación. |

---

## 3. Ciclo de Vida Metodológico por Sprints (`docs/sprints/`)

El desarrollo incremental del proyecto se encuentra estructurado en carpetas dedicadas por sprint:

```
docs/sprints/
├── sprint-0/                   # Sprint de Preparación Técnica (Readiness & Setup)
│   ├── planning.md             # Sprint Goal, DoD inicial, estrategia GitFlow y Checkstyle
│   ├── review.md               # Evidencias de setup de Spring Boot, Maven Wrapper y Husky
│   └── retrospective.md        # Retrospectiva Start-Stop-Continue del Sprint 0
├── sprint-1/                   # Cimientos del Marketplace (HU-01, HU-02, HU-04 — 10 SP)
│   ├── planning.md             # Descomposición de tareas y estimación Fibonacci
│   ├── review.md               # Demos funcionales de API REST y persistencia
│   └── retrospective.md        # Compromisos de mejora técnica inicial
├── sprint-2/                   # Mercado y Logística Base (HU-03, HU-06, HU-07 — 9 SP)
│   ├── planning.md             # Selección de HUs para refactorización SOLID y Patrones GoF
│   ├── review.md               # Evidencias de CI en GitHub Actions y TDD (Segundo Corte)
│   └── retrospective.md        # Compromisos de mejora del Sprint 2
└── sprint-3/                   # Pedidos, Pagos y Staging (HU-05, HU-09..HU-12 — 12 SP)
    ├── planning.md             # Sprint Goal de entrega final y configuración de CD
    ├── review.md               # Evidencias de cierre 100%, demos y pruebas automatizadas
    └── retrospective.md        # Balance final del semestre y lecciones aprendidas de ingeniería
```

---

## 4. Diseño, Arquitectura, Base de Datos y Prototipado (`docs/diseno/`)

Los artefactos de arquitectura y diseño se mantienen bajo `docs/diseno/`, según la indicación de la docente. La guía del laboratorio muestra otra ruta (`docs/architecture/`); en este repositorio el documento canónico de UML está en `docs/diseno/architecture/`.

- [`diseno/architecture/diagramas-uml.md`](diseno/architecture/diagramas-uml.md): diagramas UML del sistema y diferencias frente a los casos académicos requeridos.
- [`diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md`](diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md): decisión de despliegue de staging en Render.
- [`diseno/architecture-decisions/ADR-002-estrategia-despliegue-produccion.md`](diseno/architecture-decisions/ADR-002-estrategia-despliegue-produccion.md): propuesta para despliegue de producción.
- [`diseno/Documento_de_arquitectura.md`](diseno/Documento_de_arquitectura.md): Arquitectura propuesta en 4 capas y patrones GoF.
- [`diseno/Documento_de_normalizacion.md`](diseno/Documento_de_normalizacion.md): Justificación de la normalización del modelo relacional en 3FN.
- [`diseno/Documento_de_prototipado.md`](diseno/Documento_de_prototipado.md): Flujo de pantallas y wireframes UI/UX.
- [`diseno/Anexo_de_diagramas.md`](diseno/Anexo_de_diagramas.md): Catálogo consolidado de diagramas conceptuales, físicos y pantallas.
- [`diseno/schema.sql`](diseno/schema.sql): DDL relacional de referencia.

---

## 5. Estado Tecnológico Actual (Línea Base del Tercer Corte)

- **Plataforma:** Java 17 LTS (Eclipse Temurin) con Spring Boot 4.1.1.
- **Control de Calidad:** Checkstyle (Google Java Style) con **0 violaciones estáticas**.
- **Pruebas Automatizadas:** **38 clases de pruebas** (`JUnit 5 + Mockito + MockMvc`); consultar las ejecuciones recientes para resultados.
- **Cobertura de Código JaCoCo:** **91% en instrucciones** y **76% en ramas** (superando el umbral contractual del 60%).
- **Persistencia:** PostgreSQL con 18 scripts de migración secuenciales e inmutables Flyway (`V1` a `V18`).
