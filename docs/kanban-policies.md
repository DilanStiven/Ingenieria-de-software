# Políticas Explícitas del Tablero Kanban y Métricas de Flujo — Scrumban

| Información del Proyecto | Detalle |
| :--- | :--- |
| **Institución** | Institución Universitaria Antonio José Camacho |
| **Asignatura** | Ingeniería de Software II |
| **Docente** | Paola Andrea Bedoya Toro |
| **Proyecto** | AgroValle Connect |
| **Equipo** | Grupo 5 |
| **Marco de Trabajo** | Híbrido Scrumban (Scrum + Flujo continuo Kanban) |
| **Archivo** | `docs/kanban-policies.md` |

---

## 1. Fundamentación Matemática: Ley de Little

El control del flujo de trabajo en el tablero de GitHub Projects se interpreta con la **Ley de Little**, en régimen estable y usando las mismas unidades de tiempo:

$$\text{Cycle Time} = \frac{\text{Work in Progress (WIP)}}{\text{Throughput}}$$

* **Interpretación:** El Cycle Time promedio se relaciona con el WIP promedio y el Throughput promedio; un WIP alto, sin un aumento del Throughput, suele alargar el tiempo de entrega.
* **Objetivo:** Reducir el WIP en las columnas activas para minimizar el Cycle Time y maximizar la entrega continua de valor (*"Stop Starting, Start Finishing"*).

---

## 2. Límites de Trabajo en Progreso (WIP Limits)

| Columna del Tablero | Límite WIP | Justificación Operativa |
| :--- | :---: | :--- |
| **Product Backlog** | $\infty$ | Repositorio general priorizado según MoSCoW. |
| **Sprint Backlog / To Do** | $\le 10$ SP | Tareas descompuestas y estimadas para el sprint activo. |
| **In Progress** | **Máximo 3** | Restringe la multitarea en el equipo de 4 desarrolladores. Obliga a terminar antes de iniciar. |
| **Code Review / PR** | **Máximo 2** | Evita que los Pull Requests se acumulen esperando revisión; previene cuellos de botella. |
| **Done** | $\infty$ | Incremento terminado que cumple al 100% con la Definition of Done (DoD). |

---

## 3. Políticas Explícitas de Entrada y Salida (Definition of Workflow)

### 3.1 Columna: In Progress
* **Criterio de Entrada (Pull):**
  * La columna no debe haber alcanzado su WIP Limit (máximo 3 tareas simultáneas).
  * La tarea debe tener rama asignada bajo la convención `feature/HU-XX-descripcion`.
* **Criterio de Salida:**
  * Código implementado con pruebas y umbral de cobertura pasando localmente (`./mvnw clean verify`).
  * Pull Request abierto hacia la rama `develop` vinculando el Issue correspondiente.

### 3.2 Columna: Code Review (Revisión de Pares)
* **Criterio de Entrada:**
  * La columna no debe superar el límite de 2 PRs simultáneos.
  * El pipeline automatizado de CI en GitHub Actions debe estar en estado verde (**Passed**).
* **Criterio de Salida:**
  * Al menos una aprobación formal (Peer Review) de otro integrante del equipo Scrum.
  * Cero comentarios de cambio pendientes.
  * Verificación de 0 errores estáticos con Checkstyle (`./mvnw checkstyle:check`).

### 3.3 Columna: Done
* **Criterio de Entrada:** Cumplimiento total de la **Definition of Done (DoD)**:
  1. Build limpio y cobertura validada (`./mvnw clean verify`).
  2. 0 advertencias en Checkstyle (Google Java Style).
  3. Cobertura de pruebas JaCoCo $\ge$ 60%.
  4. PR fusionado en `develop`.

---

## 4. Diagnóstico de Flujo: Diagrama de Flujo Acumulado (CFD)

* **Bandas Paralelas:** Representan un ritmo de entrega higiénico y predecible entre desarrollo y revisión.
* **Banda Ensanchada (Alerta de Cuello de Botella):** Si la banda de *Code Review* se ensancha, el equipo detiene la toma de nuevas tareas en *To Do* y se enfoca colectivamente en revisar y aprobar los PRs represados.
* **Lead Time vs. Cycle Time:**
  * **Lead Time:** Mide el tiempo transcurrido desde que la necesidad entra al backlog hasta su despliegue operativo.
  * **Cycle Time:** Mide el tiempo de desarrollo activo desde que la tarjeta entra a *In Progress* hasta que cumple el DoD.

## 5. Registro y lectura de métricas

Para poder calcular estas métricas, el tablero debe conservar la fecha de creación de cada
historia, la fecha de entrada a *In Progress*, la fecha de cumplimiento del DoD y, cuando
corresponda, la fecha de despliegue. Reportar Lead Time y Cycle Time en días con el mismo
calendario y periodo; reportar Throughput como elementos completados por semana o por Sprint,
indicando si se cuentan historias, tareas o Story Points. No mezclar esas unidades en una sola
tasa.

En cada Sprint, el equipo debe revisar el CFD de GitHub Projects, señalar las bandas que crecen
o se contraen, y registrar el cuello de botella y la acción acordada en la Review o
Retrospectiva. Una tarjeta que excede su WIP Limit debe quedar registrada como impedimento; el
equipo debe detener la toma de trabajo en la etapa anterior hasta reducir el WIP.

### Línea base disponible del Sprint 1

| Medida | Valor disponible | Evidencia / limitación |
| --- | --- | --- |
| Historias completadas | 3 historias (HU-01, HU-02 y HU-04) | `docs/sprint-1-review.md` |
| Throughput | 3 historias por Sprint; 10 SP completados | Un único Sprint reportado; no es una tendencia histórica |
| Pull Requests fusionados | 20 | `docs/sprint-1-review.md` |
| Horas planificadas / reales | 38 / 40 | `docs/sprint-1-review.md` |
| Excesos de WIP | 1 exceso reportado en Code Review | `docs/sprint-1-retrospective.md`; no aparece en las cuatro Dailies detalladas |
| Muestra de tiempo de HU-01, tarea #10 | Creada el 29/09/2026 a las 17:15 y movida a Done el 01/10/2026 a las 18:00 (GMT-5): 2 días y 45 minutos | Historial de la tarjeta #10 compartido por el equipo. Es tiempo creación→Done; no se confirma fecha de despliegue. |
| Cycle Time de la tarjeta #10 | Aproximadamente 2 días calendario (29/09/2026 a 01/10/2026) | El equipo confirma que entró a *In Progress* el mismo día de su creación (29/09); llegó a Done el 01/10 a las 18:00 (GMT-5). Como no se conserva la hora exacta de entrada, es una estimación por fechas, no una duración exacta verificable en el historial. |
| CFD | GitHub Projects muestra un gráfico *Burn up* y un conteo actual por estado, no un CFD | El Burn up no muestra inventario acumulado por etapa; falta evidencia de un CFD para analizar cuellos de botella. |
| Configuración WIP del tablero | In Progress: máximo 3; Code Review / Testing: máximo 2 | Captura compartida el 28/10/2026 y archivada en [evidencias](evidencias/tablero-kanban-limites-wip-2026-10-28.png); muestra 0/3 y 0/2 al momento de la captura. No demuestra el cumplimiento histórico durante todo el Sprint. |
| Protección de `develop` | Pull Request obligatorio; al menos 1 aprobación; check requerido `Build, Checkstyle & JaCoCo Coverage`; force push y eliminación desactivados en las opciones mostradas | Capturas de configuración compartidas el 28/10/2026: [resumen](evidencias/proteccion-develop-resumen-2026-10-28.png), [check requerido](evidencias/proteccion-develop-check-ci-2026-10-28.png) y [opciones finales](evidencias/proteccion-develop-opciones-finales-2026-10-28.png). |
| Protección de `main` | Pull Request obligatorio; al menos 1 aprobación; check requerido `Build, Checkstyle & JaCoCo Coverage`; force push y eliminación desactivados en las opciones mostradas | Capturas de configuración compartidas el 28/10/2026: [resumen](evidencias/proteccion-main-resumen-2026-10-28.png), [check requerido](evidencias/proteccion-main-check-ci-2026-10-28.png) y [opciones finales](evidencias/proteccion-main-opciones-finales-2026-10-28.png). |
| CI remoto | Pull Request [#56](https://github.com/DilanStiven/Ingenieria-de-software/pull/56), ejecución [#91](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503): Java 25, compilación, Checkstyle y JaCoCo aprobados el 28/10/2026. | Commit `0c9d664` de `ci/java25-verification`; PR abierto hacia `develop`, pendiente de al menos una aprobación. Evidencia: [captura del PR](evidencias/ci-java25-pr-56-2026-10-28.png) y [anexo JaCoCo](evidencias/anexo-evidencias-jacoco-kanban.pdf). No está integrado aún. La ejecución anterior #86 en `main` usó Java 17. |

Las capturas del 28/10/2026 muestran la misma política en `main` y `develop`: PR obligatorio,
una aprobación y el check requerido `Build, Checkstyle & JaCoCo Coverage`. En ambas se ven
desactivadas las opciones de force push y eliminación. Las capturas son de la configuración
mostrada ese día; no demuestran por sí solas su estado en fechas históricas anteriores.

**Aclaración del equipo (28/10/2026):** se reportan cuatro reuniones de Sprint con bitácora y
tres revisiones adicionales para revisar pendientes y trabajo completo. No se guardaron las fechas
ni el detalle de estas tres revisiones. El equipo tampoco conservó capturas o historial de la línea
de tiempo del tablero; por tanto, no es posible reconstruir las transiciones ni calcular métricas
históricas de cada tarjeta a partir de este repositorio. No se solicitan capturas retroactivas ni
se inventan datos. El Cycle Time aproximado de la tarjeta #10 permanece como una muestra puntual,
basada en la fecha de inicio que compartió el equipo, no como sustituto del historial completo.

El elemento de Insights disponible es un gráfico *Burn up* y un gráfico de conteo por estado;
ninguno es un CFD. No se debe presentar como evidencia de un CFD ni inferir de él tiempos de
permanencia en las columnas. La falta de la serie histórica de transiciones se registra como una
limitación de evidencia, no como prueba de que el equipo no trabajó en el tablero.
