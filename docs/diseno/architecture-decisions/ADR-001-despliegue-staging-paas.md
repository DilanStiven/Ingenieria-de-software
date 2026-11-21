# ADR-001: Despliegue de staging en una plataforma PaaS

- **Estado:** Aprobado e implementado en staging
- **Fecha:** 2026-11-21
- **Autores:** Célula de Desarrollo AgroValle Connect
- **Contexto Técnico:** Asignatura Ingeniería de Software II - UNIAJC

## Contexto

AgroValle Connect ejecuta Spring Boot con Java 17 y PostgreSQL. El repositorio contiene un Dockerfile, un perfil de configuración para staging y workflows de GitHub Actions para CI y CD.

Se requiere un entorno de staging separado para validar la aplicación antes de promover cambios. El repositorio incluye el perfil `application-staging.yml`, un endpoint público de estado y un workflow CD que espera los controles de CI antes de invocar el webhook. La aplicación y la base de datos de staging ya están desplegadas en Render.

## Decisión

Usar Render para alojar la aplicación Spring Boot como contenedor Docker y PostgreSQL para los datos de staging. Los dos servicios están en Oregon (US West), separados de producción.

GitHub Actions ejecuta CI y, cuando pasa, invoca el Deploy Hook de Render. La aplicación recibe la configuración de base de datos y JWT desde las variables de entorno del servicio Render; GitHub Actions almacena únicamente el hook de despliegue como secreto.

## Alternativas consideradas

1. **Render para la aplicación y Neon para PostgreSQL:** alternativa PaaS con servicios administrados; requiere verificar planes, límites y conectividad.
2. **Railway para la aplicación y Supabase para PostgreSQL:** alternativa con servicios administrados; requiere verificar planes, conectividad y configuración de la base.
3. **Mantener solo ejecución local y CI:** no requiere infraestructura adicional, pero no ofrece un entorno compartido para pruebas de staging.

## Consecuencias

- El staging no debe compartir credenciales ni datos con producción.
- La aplicación recibe URL, usuario, contraseña y secreto JWT desde variables de entorno de Render.
- El workflow `.github/workflows/cd.yml` ejecuta `clean verify` y despliega mediante el secreto `DEPLOY_HOOK_URL`.
- El plan gratuito de Render puede suspender la instancia web por inactividad; la base de datos gratuita tiene vencimiento informado para el 21/12/2026. Se debe migrar o actualizar el plan antes de esa fecha si el entorno debe continuar disponible.
- El diagrama de despliegue de [diagramas-uml.md](../architecture/diagramas-uml.md) diferencia explícitamente la infraestructura actual del destino propuesto.

## Evidencia y operación

- Servicio web: `agrovalle-connect` en Render, configurado desde la rama `main` con Docker.
- Base de datos: `agrovalle-staging-db` en Render PostgreSQL.
- Health check: `GET https://agrovalle-connect-owri.onrender.com/api/v1/estado`; validado con estado `OK`, perfil `staging` y PostgreSQL conectado.
- Workflow CD exitoso en `main`, commit `0ad30db`: [ejecución de GitHub Actions](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/44940959390).
- Las variables de base de datos y JWT se administran en Render; no se copian al repositorio ni a GitHub Actions.
