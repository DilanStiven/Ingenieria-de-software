# Registro de Incidencias Técnicas — Staging

| Campo | Valor |
|---|---|
| Proyecto | AgroValle Connect |
| Ambiente | Render Web Service + Render PostgreSQL |
| Fecha del registro | 21/11/2026 |
| Alcance | Errores observados durante la configuración y validación del despliegue de staging |

Este registro documenta síntomas, causas identificadas, resolución y verificación. No incluye
contraseñas, tokens, JWT ni URLs de Deploy Hook.

## Incidentes resueltos

| Fecha | Síntoma | Causa identificada | Resolución y verificación |
|---|---|---|---|
| 21/11/2026 | GitHub Actions falló en `./mvnw clean compile` con `wget: Failed to fetch` para Apache Maven 3.9.16. | Fallo temporal de descarga desde Maven Central; no fue un error de compilación del proyecto. | Se reejecutó el job fallido y completó exitosamente. [Ejecución de CI](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/44939041937). |
| 21/11/2026 | Render no pudo iniciar la aplicación: Flyway reportó `UnknownHostException` al conectar con PostgreSQL. Render también informó que no detectaba puertos abiertos. | `DB_URL_VAR` usaba un hostname interno corto que no resolvía desde el servicio web. La aplicación no pudo completar el arranque y Render no pudo detectar el puerto. | Se reemplazó `DB_URL_VAR` por una URL JDBC con el hostname completo de la conexión externa de Render, puerto `5432` y SSL requerido. Luego se guardaron las variables y se desplegó de nuevo. El endpoint de salud confirmó HTTP 200 y conexión a PostgreSQL. |
| 21/11/2026 | La raíz `/` del servicio respondió HTTP 401. | La aplicación no expone una página pública en la raíz y las rutas no públicas requieren autenticación según Spring Security. | No requirió cambio de código. Para validar disponibilidad se usó `GET /api/v1/estado`, que está permitido públicamente y devolvió `estado: OK`, `perfil: staging` y `baseDatos: PostgreSQL Connected`. |
| 21/11/2026 | El primer despliegue manual comprobó que la app podía iniciar, pero no demostraba que GitHub Actions ejecutara el CD mediante el webhook. | El secreto `DEPLOY_HOOK_URL` se añadió después de la fusión que había activado el workflow; crear un secreto no dispara por sí solo un workflow. | Se guardó el Deploy Hook como secreto del repositorio y se reejecutó el workflow. Los jobs de verificación y despliegue terminaron en verde en `main`, commit `0ad30db`. [Ejecución exitosa de CI/CD](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/44940959390). |

## Consideraciones operativas

- El plan gratuito de Render puede dormir el Web Service tras un período de inactividad. La primera
  solicitud posterior puede tardar; esto no equivale a un fallo de despliegue.
- Render informa que la base PostgreSQL gratuita vence el 21/12/2026. Si staging debe seguir
  disponible después de esa fecha, se debe actualizar el plan o migrar la base antes del vencimiento.
