# Sprint 4 Planning — AgroValle Connect

> **Alcance de este artefacto:** registra el plan al inicio del Sprint; los nombres de clases, tablas y
> tareas pueden diferir de la implementación final. Para el resultado reportado, consultar
> [Sprint 4 Review](review.md).

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Asignatura** | Ingeniería de Software II — UNIAJC |
| **Período / Docente** | 2-2026 / Paola Andrea Bedoya Toro |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Stiven Castillo Valencia CC 1089002055, Santiago Edilmo Hurtado Cuenu CC 1089001065 |
| **Duración del Sprint** | 23/11/2026 – 25/11/2026 (Sprint 4 «Entrega y Reputación») |
| **Fecha de la reunión de Planning** | 23/11/2026 |
| **Reparto de historias y tareas** | 23/11/2026 |
| **Archivo** | `docs/sprints/sprint-4/planning.md` |
| **Norma de calidad** | ISO/IEC 25010:2011 |

---

## 1. Sprint Goal

> **Entregar un incremento funcional en el que el comprador pueda actualizar el estado de entrega
> de su pedido, calificar al agricultor y al producto tras recibirlo, y el sistema registre la
> reseña actualizando el perfil del agricultor.**

Al finalizar el Sprint 4 el sistema permitirá:

1. Actualizar el estado de entrega de un pedido a «Entregado» (HU-08).
2. Calificar de 1 a 5 estrellas la calidad de la cosecha y dejar un comentario sobre el agricultor (HU-13).
3. Calificar el servicio de transporte del viaje (HU-14).
4. Consultar la reputación promedio del agricultor (HU-15).

---

## 2. Capacidad del Equipo y Selección

### 2.1 Capacidad

| Métrica | Valor |
| --- | --- |
| Velocidad estimada | **9 Story Points** |
| Integrantes | 4 |
| Dedicación | 6 h por persona por semana |
| **Capacidad total** | 4 × 6 h × 1 semana = **24 h** |
| Horas planificadas en tareas | **9 h** (≈ 38 % de la capacidad); el desglose disponible solo detalla las tareas de HU-13 (2,5 h) |
| Reserva para ceremonias e imprevistos | **15 h** (≈ 62 %): Daily Scrums, Review, Retrospectiva, revisión de PRs y correcciones |
| Historias seleccionadas | 4 (HU-08, HU-13, HU-14, HU-15) |
| Stack | Java 17, Spring Boot, Spring Security + JWT, PostgreSQL, JUnit 5, Mockito, Checkstyle |

### 2.2 Historias seleccionadas

| ID | Historia de Usuario | Prioridad | SP | Tareas | Horas |
| --- | --- | --- | --- | --- | --- |
| HU-08 | Actualización de Estado de Entrega (Checkpoints) | Should Have | 3 | No registrado | No registrado |
| HU-13 | Calificación del Producto y del Agricultor | Must Have | 2 | 4 | 2,5 h |
| HU-14 | Calificación del Servicio de Transporte | Should Have | 2 | No registrado | No registrado |
| HU-15 | Visualización de Reputación Promedio del Productor | Must Have | 2 | No registrado | No registrado |
| — | **Total** | | **9** | No registrado | 9 h estimadas (desglose parcial) |

### 2.3 Asignación de responsables (propuesta, 23/11/2026)

| Integrante | Tareas | Horas |
| --- | --- | --- |
| Kevin Estiven Lucumi Polo | HU-13: 13.1, 13.2, 13.3, 13.4 | 2,5 h |
| Carlos Andres Rosales Lara | HU-08 (propuesta; subtareas no registradas) | No registrado |
| Santiago Edilmo Hurtado Cuenu | HU-14 (propuesta; subtareas no registradas) | No registrado |
| Dilan Stiven Castillo Valencia | HU-15 (propuesta; subtareas no registradas) | No registrado |
| **Total** | 4 historias | **Desglose parcial** |

La auditoría de Checkstyle se hace antes de cada Pull Request (`mvn checkstyle:check`), no como tarea aparte.

### 2.4 Orden de ejecución y dependencias

```
13.1 → 13.2 → 13.3 → 13.4                        (HU-13)
HU-08, HU-14 y HU-15: dependencias/tareas no detalladas en el acta de Planning
```

- HU-13 depende de que exista una orden de compra marcada como «Entregada» (HU-08) y de la autenticación JWT (Sprint 1).
- HU-14 y HU-15: dependencias por definir.

---

## 3. HU-13 — Calificación del Producto y del Agricultor (2 SP)

**Historia:** Como comerciante, quiero calificar de 1 a 5 estrellas la calidad de la cosecha y dejar
un comentario, para evaluar al agricultor que me vendió.

**Escenario BDD** (aprobado en el Backlog)

```gherkin
Escenario 1 — Calificación exitosa
Given que una orden de compra fue marcada como "Entregada"
When  el comerciante asigna 5 estrellas y escribe "Excelente tomate"
Then  el sistema guarda la reseña y actualiza el perfil del agricultor

Escenario 2 (propuesto) — Sin token
Given que la petición no incluye el header Authorization
When  se intenta registrar una calificación
Then  el sistema responde 401 Unauthorized
```

| Tarea | Descripción | Horas | Responsable | ISO/IEC 25010 |
| --- | --- | --- | --- | --- |
| 13.1 | Entidad `Reseña` y migración Flyway correspondiente | 0,5 | Kevin | Mantenibilidad → Modificabilidad |
| 13.2 | `ResenaService`: validar orden entregada, persistir reseña y actualizar perfil del agricultor | 0,5 | Kevin | Adecuación Funcional → Corrección |
| 13.3 | `ResenaController`: endpoint POST protegido con JWT | 0,5 | Kevin | Compatibilidad → Interoperabilidad |
| 13.4 | **[OBLIGATORIA]** Traducción BDD → JUnit 5 | 1 | Kevin | Mantenibilidad → Capacidad de ser probado |

---

## 4. HU-08 — Actualización de Estado de Entrega (Checkpoints)

**Escenario BDD aprobado:** el conductor de un viaje «En Ruta» indica su llegada al destino para
actualizar la ubicación y notificar al comprador que el pedido está por ser entregado.

El desglose de tareas y su responsable no quedaron registrados en el acta de Planning.

---

## 5. HU-14 — Calificación del Servicio de Transporte

**Escenario BDD aprobado:** el comprador califica con cuatro estrellas la puntualidad del camión
durante la recepción y el sistema asocia la métrica al conductor del viaje.

El desglose de tareas y su responsable no quedaron registrados en el acta de Planning.

---

## 6. HU-15 — Visualización de Reputación Promedio del Productor

**Escenario BDD aprobado:** el comprador consulta el perfil del agricultor o el catálogo y ve el
promedio de sus calificaciones (por ejemplo, «4.8 Estrellas»).

El desglose de tareas y su responsable no quedaron registrados en el acta de Planning.

---

## 7. Trazabilidad BDD → Pruebas Automatizadas

| HU | Escenario BDD | Resultado esperado | Tarea |
| --- | --- | --- | --- |
| HU-13 | E1 Calificación exitosa | Reseña guardada + perfil del agricultor actualizado | 13.4 |
| HU-13 | E2 Sin token (propuesto) | 401 | 13.4 |
| HU-08 | Llegada del conductor al destino | Actualizar ubicación y notificar al comprador | No quedó documentada en Planning |
| HU-14 | Calificación de puntualidad del transportista | Asociar la calificación al conductor | No quedó documentada en Planning |
| HU-15 | Consulta del perfil o lote en catálogo | Mostrar promedio de calificaciones | No quedó documentada en Planning |

---

## 8. Riesgos y Supuestos del Sprint

| Riesgo / Supuesto | Impacto | Mitigación |
| --- | --- | --- |
| Sprint muy corto (3 días: 23–25/11/2026) | Alto | Priorizar las historias seleccionadas; mantener PRs pequeños |
| Las tareas se estimaron en 30 min (1 h las pruebas) contando con GitHub Copilot | Medio | Revisar y entender el código antes de cada PR |
| HU-13 requiere una orden marcada como «Entregada» | Alto | Verificar que el estado alcanzado en HU-08 satisface el criterio de elegibilidad de HU-13 |
| Pruebas y Checkstyle al final del Sprint | Medio | Pruebas en el mismo PR de cada tarea; `mvn test` y `mvn checkstyle:check` antes de cada PR |

---

## 9. Referencias

- Definición de Hecho: [`docs/dod.md`](../../dod.md)
- Product Backlog: [`product-backlog-bdd.md`](../../requirements/product-backlog-bdd.md)
- Guía de flujo Git/GitHub: ramas `feature/HU-XX-descripcion` → PR → `develop`

---

## 10. Registro de cambios

| Fecha | Cambio |
| --- | --- |
| 23/11/2026 | HU-13 iniciada y completada el mismo día; se ajustó a 2 SP según la reestimación del Backlog. PR #150 fusionado en `develop`. Resultados y estado de integración en [Sprint 4 Review](review.md). |
