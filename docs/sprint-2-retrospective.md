# Sprint 2 Retrospectiva — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Equipo | Grupo 5 |
| Sprint | Sprint 2 (Bloque 2, Segundo Corte) |
| Fecha, hora y modalidad | 29/10/2026, 19:45 – 20:30 (Sincrónico virtual vía Google Meet) |
| Asistentes | Dilan Stiven Castillo Valencia, Kevin Estiven Lucumi Polo, Carlos Andres Rosales Lara, Santiago Edilmo Hurtado Cuenu |
| Técnica utilizada | Start / Stop / Continue |

> **Estado del documento:** acta formal de retrospectiva del equipo redactada en tercera persona. Consolida las
> observaciones empíricas del proceso de desarrollo en Scrumban, los acuerdos de trabajo colaborativo y el
> plan de acción de mejora continua para el siguiente ciclo.

## 1. Revisión del Sprint Goal

- **Objetivo:** consolidar el incremento del Sprint 1 con los 5 principios SOLID, patrones GoF (Strategy y Observer), pruebas con cobertura mínima del 60 %, pipeline de CI en GitHub Actions y tablero con límites WIP.
- **Resultado:** el equipo alcanzó los objetivos técnicos satisfactoriamente: los patrones Strategy y Observer están integrados en producción, el pipeline de CI en Java 17 está activo en verde, y la cobertura JaCoCo superó el umbral requerido alcanzando un 80,26 % de instrucciones (véase [`sprint-2-review.md`](sprint-2-review.md)).
- **Qué ayudó:** el diseño arquitectónico previo en 4 capas MVC, el modelado formal de datos (MER y DER en 3FN), la especificación IEEE 29148 y la disciplina de pruebas automatizadas con JUnit 5 y Mockito.
- **Qué dificultó el resultado:** desfases en la gestión de versiones (actualización automática no planificada a Java 25 que requirió reversión controlada a Java 17 en el PR #60), cuellos de botella iniciales en la aprobación de Pull Requests y la necesidad de supervisión rigurosa sobre el código asistido por IA.

## 2. Start / Stop / Continue

| Tipo | Observación | Evidencia | Acuerdo del equipo |
| --- | --- | --- | --- |
| **Start** | Registrar las Dailies del Sprint en la bitácora inmediatamente tras su realización. | La bitácora requirió consolidación documental al cierre del bloque. | El Scrum Master (Dilan Castillo) actualizará la bitácora el mismo día de cada sesión de sincronización. |
| **Start** | Contrastar cualquier cambio de versión o dependencia con las guías académicas antes de su aprobación. | PR #56 (Java 25) y posterior reversión obligatoria en PR #60 (Java 17). | Se incluye una verificación mandatoria en el checklist de PR para confirmar compatibilidad estricta con Java 17 LTS. |
| **Stop** | Integrar cambios o actualizaciones automáticas sin revisión previa de otro compañero. | Historial de commits en `main` del 28/10/2026. | Queda prohibido el merge automático o directo sin la aprobación expresa de un par en GitHub (Peer Review). |
| **Start** | Revisar exhaustivamente todo código o fragmento generado mediante herramientas de IA. | El uso intensivo de Copilot generó discrepancias de compatibilidad y estilo. | Todo código generado con asistencia de IA debe ser sometido a revisión humana línea por línea y validación con Checkstyle. |
| **Start** | Formalizar la matriz de responsabilidades para la revisión de Pull Requests. | Se presentaron tiempos de espera en la columna Code Review. | Se define que Kevin Lucumi revisa los PRs de backend central, Carlos Rosales los de arquitectura/patrones y Santiago Hurtado los de módulos de consulta. |
| **Continue** | Mantener la regla de un Pull Request por tarea y la verificación de CI obligatoria. | Reglas de protección activas en `main` y `develop`. | Ninguna rama podrá incorporarse a `main` si el workflow de GitHub Actions no finaliza con éxito en verde. |
| **Continue** | Escribir pruebas unitarias y de integración de manera paralela al desarrollo (TDD). | Cobertura JaCoCo del 80,26 % superando ampliamente el mínimo de 60 %. | Preservar el patrón AAA (Arrange-Act-Assert) y la suite de pruebas automatizadas con Mockito y MockMvc. |

## 3. Flujo y colaboración

- **WIP e impedimentos:** los límites de trabajo en progreso configurados (*In Progress* ≤ 3 y *Code Review / Testing* ≤ 2) demostraron ser eficaces para evitar la dispersión del equipo y reducir el tiempo de ciclo (Cycle Time estimado en aproximadamente 2 días calendario para tarjetas clave).
- **Revisión por pares:** las reglas de branch protection forzaron la colaboración: los PRs #53, #54, #55, #60 y #61 contaron con la intervención y aprobación cruzada de Kevin Lucumi (`Steven-L777`), Dilan Castillo (`DilanStiven`) y Carlos Rosales.
- **Dailies del Sprint 2:** se realizaron de forma continua del 26 al 29 de octubre de 2026 a las 20:00 h, manteniendo el foco en el flujo, los cuellos de botella y la corrección de versiones.

## 4. Acciones de mejora

| Acción | Responsable acordado | Fecha objetivo | Criterio de éxito verificable |
| --- | --- | --- | --- |
| Registrar y sincronizar cada Daily en `docs/bitacora-daily-scrum.md` | Dilan Castillo (Scrum Master) | Al cierre de cada reunión diaria | Entrada registrada con fecha, asistentes, impedimentos y métricas de tablero |
| Validar compatibilidad de versión en cada PR (JDK 17 LTS) | Carlos Rosales / Kevin Lucumi | En cada Code Review activo | Descripción del PR incluye confirmación de compatibilidad con Java 17 |
| Aumentar la cobertura del módulo de eventos (listener del Observer) | Santiago Hurtado | Inicio del Sprint 3 | Prueba unitaria de `ProductoPublicadoListener` que eleve la cobertura del paquete `events` a más del 80 % |
| Ejecutar prueba HTTP de persistencia en ambiente controlado | Kevin Lucumi | Sprint 3 Planning | Script de prueba HTTP documentado en el repositorio |

## 5. Cierre

El equipo cierra el Sprint 2 cumpliendo con todos los criterios de excelencia del Bloque 2: arquitectura MVC desacoplada, patrones de diseño GoF operando en producción, integración continua automatizada, umbral de cobertura JaCoCo ampliamente superado y reglas de gobernanza de código activas en GitHub.
