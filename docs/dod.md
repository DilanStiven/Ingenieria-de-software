# Definition of Done (DoD) — Contrato de Calidad AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect (Plataforma Agrícola del Valle del Cauca)  
**Marco Normativo de Calidad:** ISO/IEC 25010 & ISO/IEC/IEEE 29148:2018  
**Ubicación:** `docs/dod.md`  

---

## 1. Declaración de Compromiso y Principio de No Negociabilidad

En el marco metodológico ágil (Scrum / Scrumban) del proyecto **AgroValle Connect**, la **Definition of Done (DoD)** constituye el acuerdo contractual vinculante de calidad entre el equipo de desarrollo y el Product Owner (Docente). 

> **Regla de Oro:** Ninguna Historia de Usuario (HU), tarea técnica o incremento de software puede ser promovido al estado **Done**, integrado a la rama `develop` o desplegado en `main` si no cumple satisfactoriamente el **100% de los criterios** descritos en este contrato.

---

## 2. Matriz de Criterios del Definition of Done (Evolución Sprints 1 a 4)

La siguiente lista de verificación categoriza los requisitos del DoD alineados con los atributos de calidad de la norma **ISO/IEC 25010**:

### A. Adecuación Funcional y Pruebas Automatizadas (TDD)
- [x] **Criterios de Aceptación BDD:** HU-08 registra `EN_DESTINO`, permite al transportista asignado confirmar `ENTREGADA` y persiste una notificación consultable por el comprador. HU-13 y HU-14 requieren que la entrega esté completada. La Review de Sprint 4 conserva el hallazgo inicial y su actualización posterior.
- [x] **Pruebas Unitarias e Integración (JUnit 5 + Mockito):**
  - Cobertura de pruebas unitarias sobre toda la lógica de negocio en la capa de servicios (`@Service`).
  - Pruebas de integración sobre controladores REST mediante `MockMvc` validando códigos HTTP (200, 201, 400, 401, 403, 404).
  - Última verificación limpia: 171 pruebas, 0 fallos, 0 errores y 1 omitida (`clean verify`).
- [x] **Umbral Estricto de Cobertura JaCoCo:**
  - La ejecución de `./mvnw verify` (o `.\mvnw.cmd verify`) certifica una cobertura global superior o igual al **60%** en instrucciones y ramas.
  - Último reporte local verificado: **90% en instrucciones y 72% en ramas** (25/11/2026).

### B. Seguridad y Protección de Datos Sensibles
- [x] **Autenticación y Autorización Stateless (Spring Security + JWT):**
  - Endpoints protegidos mediante tokens criptográficos JWT firmados (HMAC-SHA256) con expiración controlada.
  - Roles implementados en `RolUsuario`: `AGRICULTOR` y `TRANSPORTISTA`. No existe un rol independiente de comprador; revisar este límite respecto a la autorización de las operaciones de compra y calificación.
- [x] **Cifrado de Contraseñas:** Ninguna credencial se persiste en texto plano; almacenamiento mediante hash irreversible con `BCryptPasswordEncoder`.
- [ ] **Aislamiento de Secretos (Zero Hardcoded Secrets):**
  - Prohibición absoluta de credenciales, IPs o tokens quemados en código fuente o archivos versionados.
  - La aplicación obtiene DB/JWT desde variables de entorno de Render (`DB_URL_VAR`, `DB_USER_VAR`, `DB_PASS_VAR`, `JWT_SECRET_VAR`); GitHub Actions usa `DEPLOY_HOOK_URL`.
  - La guía pide cinco variables en GitHub Secrets (`STAGING_DB_URL`, `STAGING_DB_USER`, `STAGING_DB_PASSWORD`, `JWT_SECRET_KEY`, `DEPLOY_HOOK_URL`). La configuración actual no coincide y queda pendiente validar con la docente antes de cambiar dónde se administran las credenciales.

### C. Mantenibilidad, Clean Code y Patrones de Diseño
- [x] **Verificación Estática de Código (Checkstyle):**
  - Cero violaciones de estilo contra la configuración `checkstyle.xml` (Google Java Style Guide).
  - Imports limpios sin comodines (`.*`), indentación de 4 espacios, comentarios Javadoc en clases y métodos públicos, y variables inmutables con `final`.
- [x] **Arquitectura en 4 Capas Desacoplada:**
  - Separación estricta de responsabilidades: `controller` (HTTP/DTOs), `service` (negocio/transacciones), `repository` (Spring Data JPA) y `model` (entidades JPA).
- [x] **Patrones de Diseño GoF Implementados:**
  - **DTO:** Desacoplamiento total entre modelos JPA de base de datos y contratos REST de entrada/salida.
  - **Repository:** Abstracción del acceso a datos.
  - **Strategy:** Lógica de cálculo de tarifas y fletes logísticos intercambiable y desacoplada (`TarifaStrategy`).
  - **Observer:** Manejo desacoplado de eventos y notificaciones asíncronas (`ApplicationEventPublisher` y `@EventListener`).
- [x] **Clean Code Review:** Los dos hallazgos de `PagoService` se corrigieron y la aprobación por pares quedó registrada en el [PR #145](https://github.com/DilanStiven/Ingenieria-de-software/pull/145) por `@santiageao`; el control de CI pasó el 21 de noviembre de 2026. El alcance de la aprobación corresponde a los cambios del PR, no a una certificación integral de todos los criterios de esta lista.

### D. Persistencia y Control de Migraciones de Base de Datos
- [x] **Normalización Relacional en Tercera Forma Normal (3FN):** Esquema relacional libre de anomalías de inserción, actualización y borrado.
- [x] **Control de Versiones de BD con Flyway:**
  - Toda modificación de estructura se documenta mediante scripts SQL inmutables y versionados secuencialmente en `src/main/resources/db/migration/` (`V1__...` a `V22__...`).
  - La aplicación inicia y migra la base de datos automáticamente sin intervención manual.

### E. Integración y Despliegue Continuo (CI/CD DevOps)
- [x] **Pipeline de Integración Continua (CI):**
  - Todo Pull Request dispara automáticamente `.github/workflows/ci.yml` ejecutando compilación limpia, linter Checkstyle, suite JUnit 5 y verificación JaCoCo.
- [x] **Pipeline de Despliegue Continuo (CD):**
  - Todo merge a la rama `main` ejecuta `.github/workflows/cd.yml` condicionado a `needs: verify-and-test`, activando el Webhook de despliegue automatizado hacia el entorno Staging PaaS (Render / Railway).
- [x] **Verificación de Salud Remota (Health Check):**
  - El servicio publicado en la nube responde satisfactoriamente `HTTP 200 OK` en el endpoint público `/api/v1/estado`.

### F. Trazabilidad, Documentación y Gestión Ágil
- [x] **Estrategia de Ramificación GitFlow:**
  - Trabajo exclusivo en ramas `feature/HU-XX-descripcion`.
  - Integración a `develop` y `main` únicamente mediante Pull Requests con al menos 1 aprobación formal (Peer Review).
- [x] **Conventional Commits:** Historial de commits estandarizado con prefijos semánticos (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).
- [x] **Automatización Pre-Commit (Husky):** Hook local que valida Checkstyle y pruebas antes de permitir cualquier commit.
- [ ] **Trazabilidad de Requerimientos y Diseño:**
  - Requerimientos formalizados bajo ISO/IEC/IEEE 29148:2018 y Backlog BDD en `docs/requirements/`.
  - Diagramas UML en `docs/diseno/architecture/diagramas-uml.md`. La guía usa HU-12/HU-13 para escenarios distintos a los del Backlog aprobado; HU-08, HU-13, HU-14 y HU-15 ya se implementaron según el Backlog, aunque el flujo integrado de entrega conserva brechas descritas en la Review de Sprint 4.
  - Decisiones de arquitectura en `docs/diseno/architecture-decisions/ADR-001-despliegue-staging-paas.md` y `ADR-002-estrategia-despliegue-produccion.md`.
  - [ ] Completar los puntos diarios y los gráficos de Burndown/CFD y añadir evidencia fuente en `docs/metrics/sprint-metrics-corte3.md`; faltan datos históricos del tablero.
- [ ] **Code Freeze:** Pendiente crear el Git Tag `v3.0.0-final` sobre el commit final de `main`. El tag no existe en el repositorio ni en el remoto consultado.

---

## 3. Ratificación y Firmas de la Célula Scrum

El equipo de desarrollo ratifica su compromiso con este contrato de calidad técnica, el cual ha guiado la evolución del producto desde el Sprint 0 hasta el cierre del Sprint 3 y la entrega final del Tercer Corte:

| Rol | Nombre Completo | Cédula de Ciudadanía | Usuario GitHub | Firma de Compromiso |
|---|---|---|---|:---:|
| **Scrum Master** | Dilan Stiven Castillo Valencia | CC 1089002055 | `@DilanStiven` | *Aprobado y Ratificado* |
| **Developer** | Kevin Estiven Lucumi Polo | CC 1030534697 | `@Steven-L777` | *Aprobado y Ratificado* |
| **Developer** | Carlos Andres Rosales Lara | CC 1030020342 | `@adnoireph` | *Aprobado y Ratificado* |
| **Developer** | Santiago Edilmo Hurtado Cuenu | CC 1089001065 | `@santiageao` | *Aprobado y Ratificado* |

**Fecha de Actualización y Cierre de Contrato:** 19 de Noviembre de 2026  
**Aprobación del Product Owner:** Paola Andrea Bedoya Toro (Docente Titular)
