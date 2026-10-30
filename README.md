# AgroValle Connect

![build](https://img.shields.io/badge/build-passing-brightgreen)
![coverage](https://img.shields.io/badge/coverage-60%25-yellow)
![java](https://img.shields.io/badge/Java-17-orange)
![spring](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)

## 🌱 Declaración de la Visión del Producto

> **Para** los productores del Valle del Cauca,
> **Que** necesitan vender directo y sin intermediarios excesivos,
> **AgroValle Connect** es una plataforma web empresarial en Java 17 / Spring Boot,
> **Que** conecta la oferta agrícola de las fincas del Valle con la demanda comercial urbana de Cali a precio justo,
> **A diferencia de** los intermediarios tradicionales y las cadenas de comercialización largas,
> **Nuestro producto** garantiza trazabilidad logística en tiempo real y contratos de API transparentes.

## 👥 Integrantes del Equipo

|        Nombre completo        | Rol en el equipo | Usuario de GitHub |
|-------------------------------|------------------|-------------------|
| Dilan Stiven Castillo Valencia| Scrum Master     |    DilanStiven    |
| Kevin Estiven Lucumi Polo     | Developer        |    Steven-L777    |
| Carlos Andres Rosales Lara    | Developer        |    adnoireph      |
| Santiago Edilmo Hurtado Cuenu | Developer        |    santiageao     |

## 🧱 Stack Tecnológico

- **Backend:** Java 17 + Spring Boot 4.1.1
- **Base de datos:** PostgreSQL
- **Arquitectura:** API REST en capas (`controllers`, `services`, `repositories`, `models`, `dtos`)
- **Patrones de diseño (GoF):** Repository, Strategy, Observer y DTO
- **Calidad:** Checkstyle (Google Java Style), JUnit 5, JaCoCo (≥60% cobertura), Husky (pre-commit hooks)
- **CI/CD:** GitHub Actions

## 🌿 Estrategia de Control de Versiones: GitFlow

Elegimos **GitFlow** porque el proyecto contempla módulos con lanzamientos versionados (Productores, Catálogo, Pedidos, Logística, Pagos, Calificaciones) que se integrarán de forma progresiva por sprint. Esto permite mantener `main` siempre estable para demos y usar `develop` como línea de integración continua de las ramas `feature/*`, minimizando el riesgo de romper la rama principal y reduciendo los tiempos de espera al aislar el trabajo en curso hasta que esté validado por Code Review.

```mermaid
gitGraph
    commit id: "Initial"
    branch develop
    checkout develop
    commit id: "Setup-Project"
    branch feature/HU-01
    checkout feature/HU-01
    commit id: "feat: logic-hu-01"
    checkout develop
    merge feature/HU-01
    branch release/v1.0.0
    checkout release/v1.0.0
    commit id: "fix: minor-bug"
    checkout main
    merge release/v1.0.0 tag: "v1.0.0"
    checkout develop
    merge release/v1.0.0
```

**Reglas del equipo:**
1. Nadie programa directamente sobre `main` ni `develop`.
2. Cada Historia de Usuario se trabaja en una rama `feature/HU-XX-descripcion`.
3. Toda rama se integra mediante Pull Request con al menos 1 aprobación (Peer Review).
4. Los mensajes de commit siguen **Conventional Commits** (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).

## ✅ Definition of Done (DoD)

Ver checklist completo firmado por el equipo en [`docs/dod.md`](docs/dod.md).

## 📋 Product Backlog

Ver las 15 Historias de Usuario priorizadas (MoSCoW) y especificadas en BDD en [`BACKLOG.md`](BACKLOG.md).

## ⚙️ Configuración local

Requisitos: JDK 17 y PostgreSQL. `JAVA_HOME` debe apuntar a un JDK 17 (no solo a un
runtime); comprueba la versión activa antes de compilar con `java -version`.

En PowerShell de Windows, configura el JDK 17 para la sesión actual (ajusta la ruta si está
instalado en otro directorio):

```powershell
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-17'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
java -version
```

En Linux/macOS, configura `JAVA_HOME` según la ubicación del JDK 17. Usa el Maven Wrapper
incluido para compilar, ejecutar las pruebas y verificar la cobertura:

```bash
git clone https://github.com/DilanStiven/Ingenieria-de-software.git
cd Ingenieria-de-software
./mvnw clean verify
```

En Windows PowerShell, ejecuta `.\mvnw.cmd clean verify`. Para revisar Checkstyle por separado:
`./mvnw checkstyle:check` (Windows: `.\mvnw.cmd checkstyle:check`). El hook de pre-commit
ejecuta Checkstyle, pruebas y el umbral JaCoCo.
