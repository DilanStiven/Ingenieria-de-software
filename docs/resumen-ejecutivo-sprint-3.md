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
| **Control de Versiones de BD (Flyway)** | Migraciones automáticas | **17 versiones inmutables (`V1` a `V17`)** | Cumple |
| **Patrones de Diseño GoF** | Implementación explícita | DTO, Repository, Strategy, Observer | Cumple |
| **Estándar de Requerimientos** | ISO/IEC/IEEE 29148:2018 | SyRS formalizado + 15 HUs BDD | Cumple |

---

## 3. Resumen de Métricas Ágiles de Flujo (Sesiones 13-14)

Conforme a lo documentado en [`docs/metrics/sprint-metrics-corte3.md`](metrics/sprint-metrics-corte3.md):

1. **Velocidad del Equipo (Velocity):**
   - Sprint 1: 10 SP comprometidos / 8 SP completados (80.0%).
   - Sprint 2: 14 SP comprometidos / 12 SP completados (85.7%).
   - Sprint 3: 16 SP comprometidos / 16 SP completados (**100.0%**).
   - **Velocidad promedio:** **12.0 Story Points** por ciclo.
2. **Eficiencia de Entrega:**
   - **Lead Time Promedio:** **4.2 días** (desde definición de HU hasta Done).
   - **Cycle Time Promedio:** **1.8 días** (tiempo neto de desarrollo activo).
3. **Control del Tablero (WIP Limits):**
   - El Diagrama de Flujo Acumulado (CFD) demostró estabilidad operacional sin transgresión de los límites acordados (*In Progress: 3*, *Code Review: 2*).

---

## 4. Estado de Preparación para la Sustentación Final (Sesión 16 — 40%)

La célula Scrum cuenta con los elementos técnicos y metodológicos requeridos para la presentación ejecutiva de 15 minutos:

- **Minutos 0–3 (Diapositivas):** Problema regional de intermediación agrícola, stack tecnológico Java 17 / PostgreSQL y arquitectura en 4 capas.
- **Minutos 3–9 (Demo en Vivo End-to-End):** Flujo probado en Postman:
  $$\text{Registro} \longrightarrow \text{Login JWT} \longrightarrow \text{Publicar Cosecha} \longrightarrow \text{Consultar Precios} \longrightarrow \text{Crear Orden y Pagar}$$
- **Minutos 9–12 (Demostración DevOps):** Demostración de los pipelines de CI (`ci.yml`) y CD (`cd.yml`) en GitHub Actions.
- **Minutos 12–15 (Preguntas Técnicas):** Justificación de decisiones arquitectónicas en ADR-001/002, desacoplamiento con Strategy/Observer y análisis de métricas.

---

## 5. Dictamen del Scrum Master

El incremento funcional acumulado cumple a cabalidad con el contrato de calidad **Definition of Done (DoD)** y los estándares **ISO/IEC 25010** e **ISO/IEC/IEEE 29148:2018**, encontrándose el proyecto en estado óptimo para el cierre evaluativo del semestre.

**Dilan Stiven Castillo Valencia**  
*Scrum Master — Célula de Desarrollo AgroValle Connect (Grupo 5)*
