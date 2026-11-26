# Resumen Ejecutivo de Cierre Técnico — Sprint 3 (Tercer Corte)

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente Titular:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Equipo Scrum:** Grupo 5  
**Fecha de Corte:** 19 de Noviembre de 2026  
**Ubicación:** `docs/resumen-ejecutivo-sprint-3.md`  

---

## 1. Síntesis Ejecutiva del Sprint 3

El **Sprint 3** representó la fase de consolidación transaccional y cierre de entrega del proyecto integrador **AgroValle Connect**. Durante este ciclo, la célula de desarrollo alcanzó un **factor de cumplimiento del 100%**, integrando exitosamente los **12 Story Points** comprometidos distribuidos en cinco Historias de Usuario críticas para el negocio:

- **HU-05 (3 SP):** Contacto Directo e Intención de Compra.
- **HU-09 (3 SP):** Pago con Pasarela Virtual y descuento de inventario.
- **HU-10 (2 SP):** Generación y descarga de comprobante de pago en formato PDF.
- **HU-11 (2 SP):** Acreditación inmediata en la Billetera del Agricultor (`GET /api/v1/billetera`).
- **HU-12 (2 SP):** Notificación de pago rechazado y ventana de reintento de 15 minutos.

Todas las historias se encuentran aprobadas mediante revisión de pares (Code Review) y fusionadas en la rama de integración continua `develop`.

---

## 2. Indicadores Cuantitativos de Calidad Técnica (Línea Base)

| Indicador de Ingeniería | Umbral Exigido (Rúbrica) | Resultado Medido (Real) | Estado |
|---|:---:|:---:|:---:|
| **Compilación y Construcción** | `BUILD SUCCESS` limpio | Maven 3 / Java 17 Temurin sin errores | Cumple |
| **Verificación Estática (Checkstyle)** | 0 violaciones | **0 violaciones** (Google Java Style Guide) | Cumple |
| **Pruebas Automatizadas (JUnit 5 + Mockito)** | 100% pruebas en verde | **35 suites de pruebas (0 fallos, 0 errores)** | Cumple |
| **Cobertura de Código (JaCoCo)** | >= 60% global | **91% en instrucciones / 76% en ramas** | Supera ampliamente |
| **Control de Versiones de BD (Flyway)** | Migraciones automáticas | **17 versiones inmutables (`V1` a `V17`) al cierre del Sprint 3** | Cumple |
| **Patrones de Diseño GoF** | Implementación explícita | DTO, Repository, Strategy, Observer | Cumple |
| **Estándar de Requerimientos** | ISO/IEC/IEEE 29148:2018 | SyRS formalizado + 15 HUs BDD | Cumple |

---

## 3. Resumen de Métricas Ágiles de Flujo (Sesiones 13-14)

Conforme a lo documentado en [`docs/metrics/sprint-metrics-corte3.md`](metrics/sprint-metrics-corte3.md):

1. **Velocidad del Equipo (Velocity):**
   - Sprint 1: 10 SP comprometidos / 10 SP completados (100%), según su Review.
   - Sprint 2: puntos comprometidos y completados no verificables en las actas disponibles.
   - Sprint 3: 12 SP comprometidos / 12 SP completados (100%), según su Planning y retrospectiva.
   - No se calcula un promedio de tres sprints hasta verificar el dato faltante del Sprint 2.
2. **Eficiencia de Entrega:**
   - Lead Time y Cycle Time: pendientes de timestamps reproducibles del tablero.
3. **Control del Tablero (WIP Limits):**
   - Los límites configurados son *In Progress: 3* y *Code Review / Testing: 2*. Falta el historial diario para construir el CFD y evaluar estabilidad.

---

## 4. Estado de Preparación para la Sustentación Final (Sesión 16 — 40%)

La estructura de 15 minutos está preparada como guion, pero la demo E2E y las diapositivas requieren verificación/evidencia antes de declarar la sustentación lista:

- **Minutos 0–3 (Diapositivas):** Problema regional de intermediación agrícola, stack tecnológico Java 17 / PostgreSQL y arquitectura en 4 capas.
- **Minutos 3–9 (Demo en Vivo End-to-End):** Flujo propuesto en Postman, pendiente de ejecutar y guardar evidencia:
  $$\text{Registro} \longrightarrow \text{Login JWT} \longrightarrow \text{Publicar Cosecha} \longrightarrow \text{Consultar Precios} \longrightarrow \text{Crear Orden y Pagar}$$
- **Minutos 9–12 (Demostración DevOps):** Mostrar las ejecuciones exitosas de CI (`ci.yml`) y CD (`cd.yml`) en GitHub Actions.
- **Minutos 12–15 (Preguntas Técnicas):** Justificación de decisiones arquitectónicas en ADR-001/002, desacoplamiento con Strategy/Observer y análisis de métricas.

---

## 5. Dictamen del Scrum Master

El staging responde al health check y el Sprint 3 registra 12/12 SP. La revisión Clean Code quedó
aprobada por pares en el [PR #145](https://github.com/DilanStiven/Ingenieria-de-software/pull/145);
siguen pendientes los datos fuente de métricas y el Code Freeze depende de publicar el tag sobre el
commit final de `main`. HU-08, HU-13, HU-14 y HU-15 pertenecían al alcance planificado para Sprint 4,
no al Sprint 3. Su estado posterior se documenta en los artefactos de [Sprint 4](sprints/sprint-4/);
la implementación de las cuatro historias no elimina las brechas de integración ni reemplaza la
demostración final pendiente.

**Dilan Stiven Castillo Valencia**  
*Scrum Master — Célula de Desarrollo AgroValle Connect (Grupo 5)*
