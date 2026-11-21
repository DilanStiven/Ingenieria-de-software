# Sprint 0 Planning — AgroValle Connect

**Institución Universitaria Antonio José Camacho**
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026
**Docente:** Paola Andrea Bedoya Toro
**Proyecto Integrador:** AgroValle Connect
**Ubicación:** `docs/sprints/sprint-0/planning.md`

---

## 1. Origen del Alcance

El alcance de este Sprint de Alistamiento (Sprint 0) fue establecido por la docente del curso como la base técnica obligatoria de calidad e infraestructura para el proyecto AgroValle Connect. Las tareas cubrieron: inicialización del repositorio, configuración del stack tecnológico (Java 17 / Spring Boot), establecimiento de políticas de calidad (Checkstyle, Husky), estrategia de ramas GitFlow y formalización del Product Backlog con 15 Historias de Usuario en formato BDD.

---

## 2. Información General del Sprint 0

| Atributo | Detalle |
|---|---|
| **Tipo de Sprint** | Sprint 0 — Preparación Técnica / Architecture Spike & Readiness |
| **Duración** | 14/09/2026 — 16/09/2026 (Semanas 1–2 del período) |
| **Scrum Master** | Dilan Stiven Castillo Valencia |
| **Equipo de Desarrollo** | Kevin Estiven Lucumi Polo, Carlos Andres Rosales Lara, Santiago Edilmo Hurtado Cuenu |
| **Product Owner** | Paola Andrea Bedoya Toro (Docente) |
| **Archivo** | `docs/sprints/sprint-0/planning.md` |

---

## 3. Sprint Goal

> **Sprint Goal:** Configurar la infraestructura base del proyecto en Java 17 / Spring Boot, establecer las políticas de calidad (Checkstyle, Husky), definir la estrategia de ramificación GitFlow con Conventional Commits, formalizar la visión del producto y el Product Backlog base con 15 Historias de Usuario.

---

## 4. Backlog de Tareas del Sprint 0

| ID | Descripción de la Tarea | Responsable | SP | Criterio de Aceptación | Estado |
|:---:|---|---|:---:|---|:---:|
| **T-001** | Creación e inicialización del repositorio en GitHub con `.gitignore` para Java/Maven | Dilan Castillo | 1 | Repositorio activo; archivos `.class`, `.jar`, carpetas `target/` e `.idea/` excluidos. | Completado |
| **T-002** | Configuración del proyecto base **Spring Boot / Java 17** con estructura Maven (`pom.xml`) | Dilan Castillo | 3 | `mvn clean compile` exitoso; dependencias JPA, Security, Validation y Flyway declaradas. | Completado |
| **T-003** | Configuración de la estrategia de ramificación **GitFlow** (ramas `main` y `develop` protegidas) y adopción de **Conventional Commits** | Dilan Castillo | 2 | Ramas `main` y `develop` existentes; commits históricos siguen prefijos `feat:`, `fix:`, `docs:`, `chore:`. | Completado |
| **T-004** | Configuración de **Checkstyle** (Google Java Style Guide) enlazado en `pom.xml` | Santiago Hurtado | 2 | Archivo `checkstyle.xml` referenciado en `maven-checkstyle-plugin`; `mvn checkstyle:check` ejecutable. | Completado |
| **T-005** | Automatización de pre-commit hooks con **Husky** (`.husky/pre-commit`) que bloquea commits si Checkstyle o pruebas fallan | Kevin Lucumi | 2 | Hook ejecuta `mvnw checkstyle:check` y `mvnw clean verify`; commit bloqueado ante fallo. | Completado |
| **T-006** | Definición y firma del contrato técnico **Definition of Done** (`docs/dod.md`) bajo ISO/IEC 25010 | Carlos Rosales | 2 | Documento firmado por los 4 integrantes y publicado en el repositorio. | Completado |
| **T-007** | Declaración de la **Visión del Producto** y diagrama de estrategia Git (Mermaid GitGraph) en `README.md` | Dilan Castillo | 1 | Secciones "Visión del Producto" y "Estrategia de Ramificación" presentes y correctas en el README. | Completado |
| **T-008** | Construcción del **Product Backlog** con 15 Historias de Usuario y escenarios BDD en `BACKLOG.md` (referenciado en `docs/requirements/product-backlog-bdd.md`) | Equipo Completo | 2 | Backlog visible en el repositorio con HU-01 a HU-15 y al menos un escenario BDD por historia. | Completado |

**Total Story Points Sprint 0:** 15 SP

---

## 5. Artefactos Entregados y Verificados

| Artefacto | Ruta en el Repositorio | Commit de Referencia |
|---|---|---|
| Repositorio inicializado | GitHub — `main` / `develop` | `a165ff9` chore: primer commit de prueba |
| Proyecto Spring Boot + `pom.xml` | `/pom.xml` | `d1ec570` chore: inicializar proyecto Spring Boot con Maven |
| Estrategia GitFlow + Conventional Commits | Ramas activas en GitHub | Histórico de commits |
| `.gitignore` Java/Maven | `/.gitignore` | `d1ec570` |
| `checkstyle.xml` + plugin Maven | `/checkstyle.xml`, `pom.xml` líneas 115-121 | `ea9b3fa` |
| Pre-commit hook Husky | `/.husky/pre-commit` | `68eab81` fix: reubicar dod.md y pre-commit |
| Inicialización de Husky | `/package.json` (`prepare: husky`) | Configuración del proyecto |
| Definition of Done (`docs/dod.md`) | `docs/dod.md` | `2f5a73c` docs: completar firma del equipo |
| README.md con Visión y GitGraph | `/README.md` | `ea9b3fa` docs: agregar README completo |
| Product Backlog BDD (`BACKLOG.md`) | `/BACKLOG.md`, `docs/requirements/product-backlog-bdd.md` | `ea9b3fa` |

---

## 6. Comportamiento del Pre-commit Hook (Husky)

Husky se inicializa mediante `npm install` (script `prepare` de `package.json`). El hook ejecuta Checkstyle y la verificación Maven antes de permitir el commit. Requiere Node.js/npm, Git Bash en Windows y JDK 17; si Maven Wrapper no está disponible, usa Maven instalado en el sistema.

El archivo `.husky/pre-commit` implementa la siguiente lógica de bloqueo:

```sh
#!/usr/bin/env sh
echo "Ejecutando Checkstyle..."
./mvnw checkstyle:check || {
  echo "Commit bloqueado: hay advertencias de Checkstyle sin resolver."
  exit 1
}

echo "Ejecutando pruebas unitarias..."
./mvnw clean verify || {
  echo "Commit bloqueado: hay pruebas fallando."
  exit 1
}

echo "Checkstyle y pruebas OK. Continuando con el commit."
```

---

## 7. Definicion de Hecho (DoD) Tecnico del Sprint 0

- [x] Repositorio centralizado accesible por los 4 desarrolladores.
- [x] Proyecto Spring Boot compilable con `mvn clean compile`.
- [x] Maven Wrapper (`mvnw`, `mvnw.cmd`) operativo en Windows y Linux.
- [x] Reglas de estilo Checkstyle aplicadas y verificadas en cada commit.
- [x] Pre-commit hook Husky configurado para bloquear commits con fallos de calidad o pruebas; su instalación requiere `npm install`.
- [x] DoD firmado y publicado en el repositorio (`docs/dod.md`).
- [x] Product Backlog con 15 HU publicado y accesible en `BACKLOG.md`.
- [x] Visión del producto y estrategia Git documentadas en `README.md`.

---

## 8. Referencias

- Definition of Done: [`docs/dod.md`](../../dod.md)
- Product Backlog BDD: [`docs/requirements/product-backlog-bdd.md`](../../requirements/product-backlog-bdd.md)
- README principal: [`README.md`](../../../README.md)
- Review Sprint 0: [`docs/sprints/sprint-0/review.md`](review.md)
- Retrospectiva Sprint 0: [`docs/sprints/sprint-0/retrospective.md`](retrospective.md)
