---
name: Reporte de Error (Bug Report)
about: Notificar un fallo, defecto o comportamiento anómalo en AgroValle Connect
title: '[BUG]: '
labels: bug
assignees: ''

---

## 1. Descripción del Fallo
Explicación detallada del problema identificado.

## 2. Pasos para Reproducir
1. Ejecutar solicitud HTTP a `[Endpoint REST]`
2. Enviar el payload / parámetros: `...`
3. Observar la respuesta devuelta por el servidor.

## 3. Comportamiento Observado (Real)
- Código de respuesta HTTP devuelto (ej. 500 Internal Server Error).
- Traza de error o mensaje devuelto en JSON.

## 4. Comportamiento Esperado
- Código HTTP esperado (ej. 400 Bad Request, 404 Not Found, 200 OK).
- Estructura DTO de respuesta esperada.

## 5. Entorno de Ejecución
- **Perfil:** Desarrollo Local / Staging PaaS
- **Rama:** `develop` / `main` / `feature/*`
- **Versión de Java:** 17 (Eclipse Temurin)
- **Base de Datos:** PostgreSQL / H2 en memoria
