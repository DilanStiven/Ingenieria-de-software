# Sprint 0 Review — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect  
**Ubicación:** `docs/sprints/sprint-0/review.md`  

---

## 1. Demostración y Resultados del Sprint 0

En la sesión de Sprint Review del Sprint 0, el equipo demostró formalmente la operatividad de los cimientos tecnológicos del proyecto frente al Product Owner (Docente):

### 1.1 Configuración de Git y Estrategia de Ramificación
- Repositorio configurado con ramas `main` y `develop`.
- Protección de ramas activada: ningún desarrollador puede realizar `push --force` ni push directo a `main` ni a `develop`.
- Integraciones requeridas mediante Pull Requests con aprobación de pares.

### 1.2 Configuración del Linter de Estilo (Checkstyle)
- Reglas Google Java Style configuradas en `checkstyle.xml`.
- Plugin `maven-checkstyle-plugin` configurado en `pom.xml`.
- Validación ejecutada:
  ```bash
  ./mvnw checkstyle:check
  [INFO] Checkstyle lookance passed with 0 errors.
  ```

### 1.3 Automatización Pre-Commit con Husky
- Hook `.husky/pre-commit` configurado en el entorno local para interceptar commits:
  ```bash
  # Verificación automática antes de commit:
  ./mvnw checkstyle:check
  ```
- Cumplimiento estricto del estándar de **Conventional Commits**.

### 1.4 Aprovisionamiento de Spring Boot y Base de Datos
- Proyecto creado en Java 17 con Spring Boot.
- Configuración de dependencias Maven: Spring Data JPA, Spring Web MVC, Spring Security, Validation y PostgreSQL Driver.
- Validación de inicio de aplicación exitosa (`Tomcat started on port 8080`).

---

## 2. Estado de Tareas Comprometidas

| Tarea | Estado | Evidencia |
|---|:---:|---|
| T-001: Inicialización de Repositorio | Hecho | Archivo `.gitignore` y árbol limpio en GitHub. |
| T-002: Estrategia GitFlow | Hecho | Diagrama `gitGraph` en `README.md` y ramas creadas. |
| T-003: Conventional Commits + Husky | Hecho | Historial de commits con formato normativo y hook local. |
| T-004: Definition of Done (DoD) | Hecho | Documento normativo `docs/dod.md` basado en ISO/IEC 25010. |
| T-005: Checkstyle Maven | Hecho | Archivo `checkstyle.xml` y goal en `pom.xml`. |
| T-006: Setup Java 17 + Spring Boot + BD | Hecho | Estructura en 4 capas compilando (`BUILD SUCCESS`). |
