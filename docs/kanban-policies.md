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

El control del flujo de trabajo en el tablero de GitHub Projects se rige por la **Ley de Little**:

$$\text{Cycle Time} = \frac{\text{Work in Progress (WIP)}}{\text{Throughput}}$$

* **Interpretación:** A mayor cantidad de tareas simultáneas en desarrollo (WIP alto), mayor será el tiempo que tarda una Historia de Usuario en completarse (Cycle Time).
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
  * Código implementado con pruebas unitarias pasando localmente (`./mvnw test`).
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
  1. Build limpio (`mvn clean install`).
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
