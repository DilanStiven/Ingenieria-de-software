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
| **Calidad y Auditoría** | [`clean-code-review.md`](clean-code-review.md) | **Clean Code Review:** Certificación por pares (Peer Review) que valida principios SOLID, nombres significativos y ausencia de código redundante. |
| **Gobernanza y Flujo** | [`kanban-policies.md`](kanban-policies.md) | **Políticas Kanban / Scrumban:** Políticas explícitas de entrada/salida por columna, WIP Limits (*In Progress: 3*, *Code Review: 2*) y Ley de Little. |
| **Gobernanza y Flujo** | [`bitacora-daily-scrum.md`](bitacora-daily-scrum.md) | **Bitácora de Daily Scrums:** Registro cronológico de sincronización diaria y resolución de impedimentos (Sprints 1, 2 y 3). |
| **Operación y Evidencias** | [`evidencias/registro-incidencias-staging.md`](evidencias/registro-incidencias-staging.md) | **Registro de incidencias de staging:** síntomas, causas, soluciones y validación del despliegue en Render y GitHub Actions. |
| **Requerimientos** | [`requirements/ieee-29148-syrs-agrovalle.md`](requirements/ieee-29148-syrs-agrovalle.md) | **Especificación de Requerimientos (SyRS):** Redacción prescriptiva formal bajo el estándar internacional ISO/IEC/IEEE 29148:2018. |
| **Requerimientos** | [`requirements/product-backlog-bdd.md`](requirements/product-backlog-bdd.md) | **Product Backlog BDD:** 15 Historias de Usuario priorizadas con MoSCoW, estimadas con Fibonacci y especificadas con escenarios *Given-When-Then* (GWT). |
| **Arquitectura UML** | [`architecture/diagramas-uml.md`](architecture/diagramas-uml.md) | **Diagramas UML Consolidados:** 7 diagramas en sintaxis nativa Mermaid.js (3 de secuencia, 2 de colaboración, 1 de componentes MVC 4 capas y 1 de despliegue físico). |
| **Decisiones de Diseño** | [`architecture-decisions/ADR-001-despliegue-staging-paas.md`](architecture-decisions/ADR-001-despliegue-staging-paas.md) | **ADR-001:** Adopción de plataforma PaaS (Render / Railway) y PostgreSQL gestionado para el ambiente de Staging. |
| **Decisiones de Diseño** | [`architecture-decisions/ADR-002-estrategia-despliegue-produccion.md`](architecture-decisions/ADR-002-estrategia-despliegue-produccion.md) | **ADR-002:** Estrategia de despliegue en producción con Zero-Downtime y alta disponibilidad. |
| **Métricas Ágiles** | [`metrics/sprint-metrics-corte3.md`](metrics/sprint-metrics-corte3.md) | **Reporte de Métricas de Flujo (Corte 3):** Análisis cuantitativo de Velocity (S1 a S3), Burndown diario del Sprint 3, CFD con diagnóstico y Lead/Cycle Time. |
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

## 4. Diseño de Base de Datos y Prototipado (`docs/diseno/`)

Documentos históricos y especificaciones de modelado de datos:

- [`diseno/Documento_de_arquitectura.md`](diseno/Documento_de_arquitectura.md): Arquitectura propuesta en 4 capas y patrones GoF.
- [`diseno/Documento_de_normalizacion.md`](diseno/Documento_de_normalizacion.md): Justificación de la normalización del modelo relacional en 3FN.
- [`diseno/Documento_de_prototipado.md`](diseno/Documento_de_prototipado.md): Flujo de pantallas y wireframes UI/UX.
- [`diseno/Anexo_de_diagramas.md`](diseno/Anexo_de_diagramas.md): Catálogo consolidado de diagramas conceptuales, físicos y pantallas.
- [`diseno/schema.sql`](diseno/schema.sql): DDL relacional de referencia.

---

## 5. Estado Tecnológico Actual (Línea Base del Tercer Corte)

- **Plataforma:** Java 17 LTS (Eclipse Temurin) con Spring Boot 4.1.1.
- **Control de Calidad:** Checkstyle (Google Java Style) con **0 violaciones estáticas**.
- **Pruebas Automatizadas:** **35 suites de pruebas en verde** (`JUnit 5 + Mockito + MockMvc`) con **0 fallos y 0 errores**.
- **Cobertura de Código JaCoCo:** **91% en instrucciones** y **76% en ramas** (superando el umbral contractual del 60%).
- **Persistencia:** PostgreSQL con 17 scripts de migración secuenciales e inmutables Flyway (`V1` a `V17`).
