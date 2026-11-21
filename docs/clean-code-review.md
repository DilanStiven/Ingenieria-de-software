# Lista de Chequeo de Clean Code Review (Sesión 15) — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Ubicación:** `docs/clean-code-review.md`  

---

## 1. Objetivo de la Revisión por Pares (Peer Review)

Certificar formalmente la calidad interna del código fuente antes del **Code Freeze** y entrega final del Tercer Corte, verificando el cumplimiento de los estándares de **Clean Code**, principios **SOLID**, eliminación de código redundante o muerto y adherencia al modelo de calidad **ISO/IEC 25010** (mantenibilidad, legibilidad y auditabilidad).

---

## 2. Participantes del Code Review

- **Revisores (Reviewers):** Dilan Stiven Castillo Valencia, Kevin Estiven Lucumi Polo
- **Autores del Código (Authors):** Carlos Andres Rosales Lara, Santiago Edilmo Hurtado Cuenu
- **Fecha de Certificación:** Noviembre 2026
- **Alcance Auditado:** Paquetes `controllers`, `services`, `repositories`, `models`, `dtos`, `security` y `strategies`.

---

## 3. Matriz de Verificación Clean Code & SOLID

| Criterio Técnico | Aspecto Evaluado | Estado | Hallazgo / Acción Aplicada |
|---|---|:---:|---|
| **Nombres Significativos** | Clases, métodos y variables reflejan claramente la intención de negocio sin abreviaturas crípticas ni nombres ambiguos (ej. `calcularTarifaPorDistancia()` en vez de `calc()`). | Cumple | Clases como `CalculadorTarifaService`, `ProductoRequestDTO` y `SolicitudContactoCreadaEvent` usan nomenclatura descriptiva. |
| **Funciones Pequeñas y Monotarea** | Cada método realiza una única operación conceptual y tiene menos de 25 líneas efectivas. | Remediado; pendiente de pares | `PagoService.procesar()` delega ahora la autorización, validación, persistencia y cierre del pago en métodos auxiliares. La revisión de pares debe confirmar el criterio en el alcance completo. |
| **Eliminación de Redundancias (DRY)** | Ausencia de bloques duplicados de lógica o consultas SQL redundantes. | Cumple | Se centralizó la autenticación en `JwtAuthenticationFilter` y el cálculo de tarifas en el patrón `Strategy`. |
| **Manejo Centralizado de Excepciones** | Sin bloques `try/catch` vacíos; excepciones de negocio capturadas por `@ControllerAdvice`. | Cumple | `GlobalExceptionHandler` captura `CredencialesInvalidasException`, `RecursoNoEncontradoException` y retorna respuestas HTTP uniformes (401, 404, 400). |
| **SRP (Single Responsibility)** | Cada clase tiene una única razón de cambio. | Cumple | Controladores solo gestionan HTTP/DTOs; Servicios contienen exclusivamente lógica de dominio; Repositorios acceden a datos. |
| **OCP (Open/Closed Principle)** | El sistema está abierto a la extensión pero cerrado a la modificación. | Cumple | Patrón **Strategy** permite incorporar nuevas fórmulas de flete (urbano, intermunicipal) sin modificar `OrdenLogisticaService`. |
| **LSP (Liskov Substitution)** | Las implementaciones de interfaces pueden sustituirse sin alterar la corrección del programa. | Cumple | Estrategias de flete y adaptadores de pasarela cumplen fielmente sus interfaces de contrato. |
| **ISP (Interface Segregation)** | Interfaces específicas y cohesivas sin obligar a clientes a implementar métodos no utilizados. | Cumple | Interfaces de repositorios heredan puntualmente de `JpaRepository` sin métodos sobrantes. |
| **DIP (Dependency Inversion)** | Módulos de alto nivel dependen de abstracciones, no de clases concretas. Inyección por constructor. | Remediado; pendiente de pares | `PagoService` inyecta ahora `OrdenLogisticaService` por constructor y mantiene sus dependencias como campos `final`. Falta una nueva revisión de pares para certificar la lista completa. |
| **Ausencia de Código Muerto** | Sin imports no utilizados, variables locales no referenciadas ni comentarios de código viejo. | Cumple | Checkstyle verificado con 0 violaciones en imports no usados (`UnusedImportsCheck`) y variables locales (`FinalLocalVariableCheck`). |

---

## 4. Dictamen de la Revisión

Los dos hallazgos puntuales de `PagoService` se corrigieron en esta rama. Esta lista no se considera una nueva certificación: falta que los revisores vuelvan a validar el cambio y registren su aprobación.

- **Firma Revisor 1:** Dilan Stiven Castillo Valencia (Scrum Master / Reviewer)
- **Firma Revisor 2:** Kevin Estiven Lucumi Polo (Developer / Reviewer)
- **Firma Autor 1:** Carlos Andres Rosales Lara (Developer)
- **Firma Autor 2:** Santiago Edilmo Hurtado Cuenu (Developer)
