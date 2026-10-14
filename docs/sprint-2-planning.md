# Sprint 2 Planning — AgroValle Connect

| Campo | Valor |
| :--- | :--- |
| **Proyecto** | AgroValle Connect |
| **Asignatura** | Ingeniería de Software II — UNIAJC |
| **Docente** | Paola Andrea Bedoya Toro |
| **Equipo Scrum** | Grupo 5 |
| **Duración del Sprint** | 2 semanas |
| **Archivo** | `docs/sprint-2-planning.md` |
| **Norma de Calidad** | ISO/IEC 25010 |

---

## 1. Sprint Goal

> **Evolucionar la plataforma integrando refactorización bajo principios SOLID, patrones GoF (Strategy y Observer), automatización continua mediante CI con GitHub Actions y el servicio de cálculo tarifario regional.**

---

## 2. Historias y Deuda Técnica Seleccionadas

| ID / Tarea | Descripción | Patrón / Principio | SP | Responsable |
| :--- | :--- | :---: | :---: | :--- |
| **HU-02-REF** | Desacoplamiento de eventos de publicación de cosechas mediante `ApplicationEventPublisher`. | Observer (GoF) / SRP | 3 | Kevin Lucumi |
| **HU-CALC-01** | Servicio de cálculo de flete dinámico para municipios (Dagua, Palmira, Buga) mediante estrategias intercambiables. | Strategy (GoF) / OCP | 5 | Santiago Cueno |
| **CI-01** | Configuración de pipeline automatizado con GitHub Actions (`ci.yml`) ejecutando Checkstyle y JaCoCo. | Integración Continua | 3 | Carlos Rosales |
| **TEST-01** | Suite de pruebas unitarias para `AgricultorService` y blindaje de cobertura JaCoCo $\ge$ 60%. | TDD / JUnit 5 | 2 | Dilan Castillo |

**Capacidad total comprometida:** 13 Story Points.

---

## 3. Criterios de Aceptación Técnicos del Sprint 2

1. **Pipeline Activo:** Cada Pull Request hacia `develop` o `main` debe disparar el runner de GitHub Actions y pasar las fases de compilación, linter y pruebas.
2. **Patrón Strategy Operativo:** `CalculadorTarifaService` debe resolver el costo del flete según el municipio inyectando las estrategias concretas sin condicionales `if-else`.
3. **Patrón Observer Operativo:** La publicación de un producto debe emitir `ProductoPublicadoEvent` y ser capturada por `ProductoPublicadoListener`.
4. **Calidad de Código:** Mantener 0 errores en Checkstyle y cobertura global JaCoCo por encima del 60%.
