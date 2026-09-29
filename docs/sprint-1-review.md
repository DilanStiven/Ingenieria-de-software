# Sprint 1 Review — AgroValle Connect

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Estiven Castillo CC 1089002055, Santiago Edilmo Cueno Hurtado CC 1089001065 |
| **Sprint** | Sprint 1 (24/09/2026 – 09/10/2026) |
| **Fecha de la Review** | 09/10/2026 — 16:00 (Google Meet) |
| **Asistentes** | Equipo de desarrollo y Docente (en el rol de Product Owner) |
| **Archivo** | `docs/sprint-1-review.md` |

---

## 1. Sprint Goal y resultado

> **Sprint Goal:** Entregar un incremento funcional en el que un agricultor pueda registrarse,
> obtener un token de acceso y publicar sus cosechas, y un comprador pueda filtrar el catálogo
> por municipio y categoría.

**Resultado:** Cumplido. Las tres historias comprometidas cumplen la Definition of Done
(`docs/dod.md`): endpoints REST operativos, persistencia en PostgreSQL, autenticación con JWT,
pruebas automatizadas en verde y Checkstyle sin errores.

## 2. Incremento entregado

| Historia | SP comprometidos | SP completados | Estado | Evidencia |
| --- | --- | --- | --- | --- |
| HU-01 — Registro de Agricultores | 3 | 3 | Done | Issues #1–#8 (un PR por tarea) |
| HU-02 — Publicación de Productos | 5 | 5 | Done | Issues #9–#15 (un PR por tarea) |
| HU-04 — Filtro de Categorías y Municipios | 2 | 2 | Done | Issues #16–#20 (un PR por tarea) |
| **Total** | **10** | **10** | | 20 Pull Requests fusionados |

**Velocidad real del Sprint:** 10 SP (igual a la velocidad inicial estimada).

## 3. Demo del incremento

Entorno: `http://localhost:8080` (aplicación Spring Boot + PostgreSQL 15).

### 3.1 HU-01 — Registro de Agricultores

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Registro exitoso | `POST /api/v1/auth/register` con nombre, ubicacion_valle y cedula | 201 + `id` + `token` | 201 Created con `id` y token JWT | `docs/evidencias/hu01-e1.png` |
| 2 | Cédula duplicada | Mismo cuerpo del caso 1 | 409 Conflict | 409 Conflict: "Ya existe un productor con la cédula: 1234567890" | `docs/evidencias/hu01-e2.png` |
| 3 | Datos inválidos | nombre vacío y cédula `abc` | 400 Bad Request | 400 Bad Request con el detalle de los campos inválidos | `docs/evidencias/hu01-e3.png` |

```bash
curl -i -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Pérez","ubicacion_valle":"Dagua","cedula":"1234567890"}'
```

### 3.2 HU-02 — Publicación de Productos

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Publicación exitosa | `POST /api/v1/productos` con `Authorization: Bearer <token>` | 201 + ID único | 201 Created con `id` = 1 | `docs/evidencias/hu02-e1.png` |
| 2 | Fecha anterior a hoy | Igual, con `fecha_cosecha` pasada | 400 Bad Request | 400: "La fecha de cosecha no puede ser anterior a hoy" | `docs/evidencias/hu02-e2.png` |
| 3 | Sin token | Igual, sin header `Authorization` | 401 Unauthorized | 401 Unauthorized | `docs/evidencias/hu02-e3.png` |

```bash
curl -i -X POST http://localhost:8080/api/v1/productos \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"tipo":"Mango","categoria":"Frutas","cantidad":50,
       "precio_unitario":2500,"fecha_cosecha":"2026-10-20"}'
```

### 3.3 HU-04 — Filtro de Categorías y Municipios

| # | Escenario BDD | Petición | Resultado esperado | Resultado real | Evidencia |
| --- | --- | --- | --- | --- | --- |
| 1 | Con resultados | `GET /api/v1/productos?municipio=Dagua&categoria=Frutas` | 200 + arreglo con ofertas | 200 OK con 1 producto (Mango) | `docs/evidencias/hu04-e1.png` |
| 2 | Sin resultados | `GET ...?municipio=Buga&categoria=Granos` | 200 + `[]` | 200 OK con arreglo vacío | `docs/evidencias/hu04-e2.png` |
| 3 | Municipio no válido | `GET ...?municipio=Otro` | 400 Bad Request | 400: "Municipio no válido: Otro" | `docs/evidencias/hu04-e3.png` |

```bash
curl -i "http://localhost:8080/api/v1/productos?municipio=Dagua&categoria=Frutas"
```

## 4. Calidad del incremento

| Verificación | Comando | Resultado | Evidencia |
| --- | --- | --- | --- |
| Pruebas automatizadas (BDD → JUnit 5) | `mvn test` | 13 pruebas ejecutadas, 13 exitosas (HU-01: 6, HU-02: 4, HU-04: 3) | `docs/evidencias/mvn-test.png` |
| Estilo de código | `mvn checkstyle:check` | 0 errores | `docs/evidencias/checkstyle.png` |
| Pre-commit hooks (Husky) | commit de prueba con código mal formateado | El hook bloqueó el commit hasta corregir el estilo | `docs/evidencias/husky-hook.png` |

## 5. Esfuerzo planificado vs. real

| Historia | Horas planificadas | Horas reales | Diferencia | Comentario |
| --- | --- | --- | --- | --- |
| HU-01 | 15 | 17 | +2 | `JwtService` (1.4) tomó 3 h en vez de 2 h, y las pruebas (1.7) requirieron 1 h adicional para los mocks. |
| HU-02 | 16 | 16 | 0 | Sin desviaciones. |
| HU-04 | 7 | 7 | 0 | Sin desviaciones. |
| **Total** | **38** | **40** | **+2** | Capacidad disponible: 48 h (utilización real ≈ 83 %). |

## 6. Trabajo no completado

| Tarea / Historia | Motivo | Decisión |
| --- | --- | --- |
| Ninguna | Las 20 tareas cumplieron la Definition of Done. | Se continúa con el Sprint 2. |

## 7. Retroalimentación recibida

| Origen | Comentario | Acción derivada |
| --- | --- | --- |
| Docente (Product Owner) | Las respuestas HTTP son consistentes. Se sugirió validar también en el DTO que el precio unitario no sea negativo, además de la restricción `CHECK` de la base de datos. | Se creará un Issue técnico para agregar `@PositiveOrZero` a `precio_unitario` en el Sprint 2. |

## 8. Actualización del Product Backlog

Elementos propuestos para el Sprint 2:

- Inicio de sesión con credenciales (login con contraseña), ya que en el Sprint 1 el token se
  emite al registrarse.
- Validación de precio unitario no negativo en el DTO (retroalimentación de esta Review).
- HU-03 y las demás historias pendientes de `BACKLOG.md`, priorizadas en el próximo Planning.

## 9. Enlaces

- Repositorio: [COMPLETAR: URL de GitHub]
- Tablero (GitHub Projects): [COMPLETAR: URL]
- Planning: [`docs/sprint-1-planning.md`](sprint-1-planning.md)
- Bitácora de Dailies: [`docs/bitacora-daily-scrum.md`](bitacora-daily-scrum.md)
- Retrospectiva: [`docs/sprint-1-retrospective.md`](sprint-1-retrospective.md)
