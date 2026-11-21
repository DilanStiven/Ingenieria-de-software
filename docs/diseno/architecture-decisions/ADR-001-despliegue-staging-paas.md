# ADR-001: Despliegue de staging en una plataforma PaaS

- **Estado:** Aprobado
- **Fecha:** 2026-11-21
- **Autores:** Célula de Desarrollo AgroValle Connect
- **Contexto Técnico:** Asignatura Ingeniería de Software II - UNIAJC

## Contexto

AgroValle Connect ejecuta Spring Boot con Java 17 y PostgreSQL. El workflow actual de GitHub Actions compila, ejecuta Checkstyle y verifica pruebas, pero no publica la aplicación. El repositorio no contiene Dockerfile ni configuración de staging.

Se requiere un entorno de staging separado para validar la aplicación antes de promover cambios. Las opciones discutidas son un PaaS para la aplicación (por ejemplo, Render o Railway) y un servicio PostgreSQL administrado (por ejemplo, Neon o Supabase). El repositorio ahora incluye el perfil `application-staging.yml`, el endpoint público de estado y el workflow CD que espera los controles de CI antes de invocar el webhook.

## Decisión

Usar una plataforma administrada (PaaS) para la aplicación de staging y una base PostgreSQL administrada, manteniendo el entorno separado de producción y gestionando credenciales mediante variables o secretos del proveedor.

Se aprueba una plataforma PaaS junto con GitHub Actions. El proveedor concreto puede ser Render o Railway y debe seleccionarse según compatibilidad con Java 17 y Spring Boot, conexión PostgreSQL, costos, límites del plan y configuración de secretos. La decisión no sustituye la aprovisionamiento real: el proveedor y el webhook deben existir antes del primer despliegue.

## Alternativas consideradas

1. **Render para la aplicación y Neon para PostgreSQL:** alternativa PaaS con servicios administrados; requiere verificar planes, límites y conectividad.
2. **Railway para la aplicación y Supabase para PostgreSQL:** alternativa con servicios administrados; requiere verificar planes, conectividad y configuración de la base.
3. **Mantener solo ejecución local y CI:** no requiere infraestructura adicional, pero no ofrece un entorno compartido para pruebas de staging.

## Consecuencias

- El staging no debe compartir credenciales ni datos con producción.
- La aplicación debe recibir URL, usuario, contraseña y secreto JWT desde configuración segura del entorno.
- El pipeline de despliegue, las migraciones y la política de datos de prueba quedan pendientes de definición e implementación.
- Hasta que se confirme proveedor y se configure el pipeline, GitHub Actions seguirá siendo únicamente CI.
- El diagrama de despliegue de [diagramas-uml.md](../architecture/diagramas-uml.md) diferencia explícitamente la infraestructura actual del destino propuesto.

## Pendientes de implementación

- Configurar los secretos `STAGING_DB_URL`, `STAGING_DB_USER`, `STAGING_DB_PASSWORD`, `JWT_SECRET_KEY` y `DEPLOY_HOOK_URL` en GitHub.
- Mapear las variables del proveedor a `DB_URL_VAR`, `DB_USER_VAR`, `DB_PASS_VAR`, `JWT_SECRET_VAR` y activar `SPRING_PROFILES_ACTIVE=staging`.
- Elegir el proveedor final de aplicación y PostgreSQL.
- Configurar el webhook de despliegue y registrar la URL pública para las pruebas end-to-end.
