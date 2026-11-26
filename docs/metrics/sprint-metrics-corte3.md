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

Solo se reportan cifras respaldadas por los artefactos de sprint disponibles. El Sprint 2 no tiene
un compromiso y cierre de puntos verificable en sus actas; por eso no se calcula un promedio de
tres sprints.

| Sprint | Story Points comprometidos | Story Points completados | Cumplimiento | Fuente / observación |
|:---:|:---:|:---:|:---:|---|
| **Sprint 1** | 10 SP | 10 SP | 100 % | La Review registra las tres historias como Done ([review](../sprints/sprint-1/review.md)). |
| **Sprint 2** | No verificable | No verificable | N/D | El Planning presenta 9 SP como selección experimental, no como compromiso aprobado; la Review no registra cierre de velocidad ([planning](../sprints/sprint-2/planning.md), [review](../sprints/sprint-2/review.md)). |
| **Sprint 3** | 12 SP | 12 SP | 100 % | Planning y retrospectiva registran cinco historias, 12/12 SP ([planning](../sprints/sprint-3/planning.md), [retrospectiva](../sprints/sprint-3/retrospective.md)). |

**Promedio de tres sprints:** pendiente. No es válido calcularlo hasta verificar los datos del
Sprint 2 con el registro del tablero o con el equipo.

### Conciliación con las cifras de la guía

La guía presenta como ejemplos Sprint 1 = 10/8 SP, Sprint 2 = 14/12 SP y Sprint 3 = 16/16 SP,
además de un burndown de 16 SP en 14 días, conteos de CFD y promedios de 4,2/1,8 días. Esos valores
no coinciden con las actas del proyecto ni incluyen una fuente histórica verificable; por eso no se
copian como mediciones reales. Los cierres respaldados aquí son Sprint 1 = 10/10 y Sprint 3 = 12/12;
el cierre del Sprint 2 sigue sin poder calcularse.

---

## 3. Gráfico de Trabajo Pendiente (Burndown Chart)

El Planning disponible registra un Sprint de una semana y 12 SP comprometidos, pero no se encontró
la serie diaria real ni el historial diario de GitHub Projects. Por tanto, no se presenta una curva
ideal/real ni se reutilizan valores de ejemplo como si fueran mediciones del equipo.

| Dato requerido | Estado |
|---|---|
| Línea base | 12 SP comprometidos ([planning del Sprint 3](../sprints/sprint-3/planning.md)) |
| Trabajo real restante por día | Pendiente de exportar o reconstruir desde el historial del tablero |
| Gráfico Burndown diario | Pendiente de la serie real; no se puede derivar de los resúmenes de sprint |

La serie de 14 días y 16 SP de la guía no corresponde al Planning documentado del Sprint 3.

---

## 4. Diagrama de Flujo Acumulado (Cumulative Flow Diagram - CFD)

Los límites definidos son *In Progress* ≤ 3 y *Code Review / Testing* ≤ 2. La captura del tablero
([evidencia](../evidencias/tablero-kanban-limites-wip-2026-10-28.png)) acredita la configuración
visible de esos límites, pero no es un historial de estados ni permite reconstruir un CFD.

| Evidencia requerida | Estado |
|---|---|
| Límites WIP acordados | Documentados en [políticas Kanban](../kanban-policies.md) y visibles en la captura del tablero |
| Conteo de tareas por estado a diario | Pendiente del historial/export del tablero |
| Gráfico CFD acumulado | Pendiente de datos diarios; no se presentan conteos de ejemplo como mediciones |

---

## 5. Tiempos de Entrega (Lead Time y Cycle Time)

Lead Time y Cycle Time requieren timestamps de creación, inicio de trabajo y finalización por
historia. El repositorio no incluye esos registros ni un cálculo reproducible; se retiran los
promedios de 4.2 y 1.8 días del borrador anterior hasta adjuntar una fuente verificable.

El Cycle Time aproximado de una tarjeta documentado en las políticas Kanban es una observación
puntual, no una muestra suficiente para calcular un promedio del equipo.

---

## 6. Conclusión y Madurez Ágil del Equipo

Con la información disponible se verifican los cierres de Sprint 1 (10/10 SP) y Sprint 3 (12/12
SP). No se concluye una tendencia de madurez ni estabilidad del flujo hasta completar los registros
del Sprint 2, el Burndown, el CFD y los tiempos con evidencia fuente.
