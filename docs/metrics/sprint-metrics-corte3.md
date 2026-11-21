# Reporte de Métricas Ágiles de Flujo (Corte 3) — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Ubicación:** `docs/metrics/sprint-metrics-corte3.md`  

---

## 1. Propósito del Documento

Documentar y auditar el comportamiento cuantitativo del equipo de desarrollo durante los **Sprints 1, 2 y 3**, demostrando el control sobre el ritmo de trabajo, la capacidad predictiva del equipo, la estabilidad del flujo y la identificación temprana de cuellos de botella bajo el marco Scrum y Kanban (Scrumban).

---

## 2. Velocidad del Equipo (Velocity)

Medición empírica de la cantidad de Story Points (SP) comprometidos frente a los realmente entregados que cumplen al 100% el **Definition of Done (DoD)** técnico establecido por el equipo.

| Sprint | Story Points Comprometidos | Story Points Completados (DoD 100%) | Factor de Cumplimiento (%) | Observaciones Técnicas |
|:---:|:---:|:---:|:---:|---|
| **Sprint 1** | 10 SP | 8 SP | 80.0% | Ajuste inicial en configuración de Spring Boot, modelos JPA y conexión inicial a PostgreSQL. |
| **Sprint 2** | 14 SP | 12 SP | 85.7% | Integración de TDD con JUnit 5, Mockito, umbral JaCoCo $\ge$ 60% y pipelines de CI en GitHub Actions. |
| **Sprint 3** | 16 SP | 16 SP | 100.0% | Estabilización del flujo con CD automatizado y despliegue a Staging PaaS. |
| **Promedio** | **13.3 SP** | **12.0 SP** | **90.2%** | **Velocidad de crucero establecida para planificación futura.** |

---

## 3. Gráfico de Trabajo Pendiente (Burndown Chart)

Registro del avance diario del **Sprint 3** con una duración de 14 días calendario y un total de 16 Story Points comprometidos.

| Día del Sprint | Trabajo Ideal Pendiente (SP) | Trabajo Real Pendiente (SP) | Estado y Bloqueos Identificados |
|:---:|:---:|:---:|---|
| **Día 1** | 16.0 | 16.0 | Sprint Planning y asignación de tareas en GitHub Projects. |
| **Día 3** | 13.7 | 14.0 | Ajuste de variables de entorno en `application-staging.yml`. |
| **Día 6** | 10.3 | 11.0 | Registro de secretos en GitHub Secrets (`STAGING_DB_URL`, etc.). |
| **Día 9** | 6.9 | 6.0 | Implementación de pipeline de CD (`cd.yml`) y Webhook de Render. |
| **Día 12** | 3.4 | 2.0 | Diagramación UML en Mermaid y pruebas de integración. |
| **Día 14** | 0.0 | 0.0 | **Sprint Goal alcanzado.** Incremento listo para sustentación. |

---

## 4. Diagrama de Flujo Acumulado (Cumulative Flow Diagram - CFD)

Seguimiento del estado de las tareas a través del ciclo de vida para verificar el cumplimiento riguroso de los **Límites de Trabajo en Progreso (WIP Limits)** acordados en las políticas del tablero Kanban.

| Día del Sprint | Backlog (Por Hacer) | In Progress (WIP Limit: 3) | Testing / Review (WIP Limit: 2) | Done (Desplegado en Staging) |
|:---:|:---:|:---:|:---:|:---:|
| **Día 1** | 10 tareas | 2 tareas | 0 tareas | 0 tareas |
| **Día 5** | 5 tareas | 3 tareas | 2 tareas | 2 tareas |
| **Día 10** | 1 tarea | 2 tareas | 1 tarea | 8 tareas |
| **Día 14** | 0 tareas | 0 tareas | 0 tareas | 12 tareas |

### Diagnóstico del CFD
El gráfico y la matriz de estados demuestran **alta estabilidad operacional**. En ningún momento la columna *In Progress* superó el límite de 3 tareas simultáneas por pareja de desarrolladores, previniendo la sobrecarga cognitiva, eliminando los cuellos de botella en revisión y garantizando un flujo continuo (Continuous Flow) hacia la columna *Done*.

---

## 5. Tiempos de Entrega (Lead Time y Cycle Time)

Métricas de eficiencia del proceso orientadas a la reducción del desperdicio y la entrega oportuna de valor:

- **Lead Time Promedio:** **4.2 días**  
  *(Tiempo transcurrido desde la creación y formalización de la Historia de Usuario en el Product Backlog hasta su verificación y despliegue exitoso en Staging).*
- **Cycle Time Promedio:** **1.8 días**  
  *(Tiempo neto de desarrollo desde que la célula de programadores mueve la tarea a `In Progress` hasta que pasa exitosamente todas las cláusulas del Definition of Done).*

---

## 6. Conclusión y Madurez Ágil del Equipo

La evolución del factor de cumplimiento del 80.0% en el Sprint 1 al **100.0% en el Sprint 3**, junto con un Cycle Time de solo 1.8 días, evidencia una curva de aprendizaje óptima en la adopción de ingeniería de software disciplinada, automatización de pruebas y entrega continua.
