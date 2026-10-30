# Sprint 2 Retrospectiva — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Equipo | Grupo 5 |
| Sprint | Sprint 2 |
| Fecha, hora y modalidad | [completar] |
| Asistentes | [completar] |
| Técnica utilizada | Start / Stop / Continue |

> **Estado del documento:** borrador redactado en tercera persona a partir de hechos comprobables en el
> repositorio. El equipo confirma, corrige o amplía cada observación durante la sesión y registra el resultado
> en la columna de acuerdos.

## 1. Revisión del Sprint Goal

- **Objetivo:** consolidar el Sprint 1 con SOLID, patrones GoF, pruebas con cobertura mínima de 60 %, CI y
  tablero con límites WIP.
- **Resultado:** el equipo implementó los patrones Strategy y Observer, configuró CI y protección de ramas y
  superó el umbral de cobertura (véase [`sprint-2-review.md`](sprint-2-review.md)).
- **Qué ayudó:** el equipo inició con el trabajo de diseño y la documentación (incluida la especificación
  IEEE 29148) y corrigió errores heredados del Sprint 1.
- **Qué dificultó el resultado:** problemas de compatibilidad, fallos con la aprobación de los Pull Requests y
  errores derivados del uso de Copilot. En particular, la actualización automática a Java 25 contradijo la guía y obligó a revertir
  el cambio con el PR #60 el 29/10/2026.

## 2. Start / Stop / Continue

| Tipo | Observación | Evidencia | Acuerdo del equipo |
| --- | --- | --- | --- |
| Start | El equipo debe registrar las Dailies del Sprint 2 en la bitácora el mismo día en que ocurren. | La bitácora detallada cubre solo el Sprint 1. | [completar] |
| Start | El equipo debe comparar cada cambio de versión o de herramienta con la guía académica antes de integrarlo. | PR #56 (Java 25) y PR #60 (reversión a Java 17). | [completar] |
| Stop | El equipo debe dejar de integrar cambios automáticos sin una revisión previa de otra persona. | Historial de `main` del 28 y 29/10/2026. | [completar] |
| Start | El equipo debe revisar el código y los cambios generados con Copilot antes de integrarlos. | El uso intensivo de Copilot produjo errores de compatibilidad, entre ellos el cambio a Java 25. | [completar] |
| Start | El equipo debe acordar quién revisa y aprueba cada Pull Request para evitar bloqueos. | El equipo reportó fallos con la aprobación requerida al subir Pull Requests. | [completar] |
| Continue | El equipo continúa con un Pull Request por tarea y la verificación de CI obligatoria. | Reglas de protección de `main` y `develop`. | [completar] |
| Continue | El equipo continúa escribiendo pruebas junto con el código y vigilando la cobertura. | JaCoCo con 80,26 % de instrucciones en el run analizado. | [completar] |

## 3. Flujo y colaboración

- **WIP e impedimentos:** los límites configurados son *In Progress* ≤ 3 y *Code Review / Testing* ≤ 2.
  Las capturas conservadas muestran la configuración, no el cumplimiento histórico.
- **Revisión por pares:** las reglas de protección exigen una aprobación; el equipo revisa quién aprobó cada
  Pull Request del Sprint 2: [completar].
- **Dailies del Sprint 2:** [completar con las fechas realmente realizadas].

## 4. Acciones de mejora

| Acción | Responsable acordado | Fecha objetivo | Criterio de éxito verificable |
| --- | --- | --- | --- |
| Registrar cada Daily en `docs/bitacora-daily-scrum.md` | [completar] | [completar] | Una entrada por Daily con fecha y asistentes |
| Revisar la guía antes de aceptar cambios de versión | [completar] | [completar] | Cada PR de cambio de versión cita el requisito de la guía |
| Ejecutar la prueba HTTP de persistencia con JDK 17 | [completar] | [completar] | Resultado registrado en `docs/README.md` |

## 5. Cierre

Aprendizajes y temas para el siguiente Planning: [completar].
