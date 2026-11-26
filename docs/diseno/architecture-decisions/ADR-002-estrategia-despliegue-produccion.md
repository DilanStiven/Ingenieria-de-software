# ADR-002: Estrategia de Despliegue en Producción y Disponibilidad de Servicio

* **Estado:** Propuesto / Planificado
* **Fecha:** 2026-11-15
* **Autores:** Célula de Desarrollo AgroValle Connect
* **Contexto Técnico:** Asignatura Ingeniería de Software II - UNIAJC

---

## 1. Contexto y Problemática

El proyecto integrador **AgroValle Connect** conectará a productores agrícolas del Valle del Cauca con compradores comerciales urbanos. A diferencia del ambiente de Staging (destinado a pruebas funcionales y validaciones del Product Owner), el entorno de **Producción** requiere:

1. Alta disponibilidad y resiliencia ante picos de tráfico (cosechas de temporada y jornadas de comercialización).
2. Prevención de caídas de servicio por periodos de inactividad (evitar tiempos de arranque en frío o *Cold Starts* de las capas gratuitas de PaaS).
3. Estrategia de despliegue con tiempo de inactividad cero (*Zero-Downtime Deployment*).
4. Persistencia transaccional robusta con réplicas de lectura y respaldos automatizados.

---

## 2. Opciones Consideradas

### Opción 1: Despliegue en Contenedores Kubernetes (GKE / EKS)
- **Ventajas:** Máxima escalabilidad horizontal, orquestación granular, balanceo avanzado y soporte nativo para despliegues *Canary* o *Blue/Green*.
- **Desventajas:** Alta complejidad operativa, sobrecosto de administración de clúster para la escala inicial del proyecto y curva de aprendizaje pronunciada.

### Opción 2: Plataforma de Contenedores Gestionada (AWS ECS / Google Cloud Run / Render Pro)
- **Ventajas:** Ejecución de contenedores Docker con autoescalado elástico, certificados SSL automáticos, soporte de *Zero-Downtime Rolling Updates* y costos proporcionales al consumo real.
- **Desventajas:** Menor flexibilidad en la configuración de redes de bajo nivel en comparación con Kubernetes.

### Opción 3: Despliegue en Máquinas Virtuales IaaS Tradicionales (Compute Engine / EC2)
- **Ventajas:** Control total del sistema operativo y configuración del servidor web.
- **Desventajas:** Mantenimiento manual de parches de seguridad, configuración compleja de balanceadores y dificultad para automatizar despliegues continuos sin herramientas externas.

---

## 3. Decisión Adoptada

Se aprueba la **Opción 2: Plataforma de Contenedores Gestionada (PaaS / CaaS Comercial)** para el entorno de producción futuro:

1. **Estrategia de Lanzamiento:** Despliegues progresivos mediante **Rolling Updates** (o Blue/Green) activados desde la rama `main` tras la aprobación de un *Release Tag* oficial (por ejemplo, `v1.0.0`); las reglas están en [`versionamiento.md`](../../versionamiento.md).
2. **Capa de Persistencia:** Base de datos PostgreSQL gestionada con alta disponibilidad (Multi-AZ), copias de seguridad continuas y cifrado en reposo.
3. **Monitoreo y Salud:** Verificación continua a través de endpoints de telemetría y health checks automatizados.

---

## 4. Consecuencias y Mantenibilidad

### Consecuencias Positivas:
- **Disponibilidad Continua:** Los usuarios (agricultores y compradores) experimentan transacciones sin interrupciones durante las actualizaciones de versión.
- **Seguridad en Producción:** Aislamiento estricto de secretos de producción en gestores dedicados (Secret Manager).
- **Escalabilidad Elástica:** Capacidad de absorber picos de demanda durante temporadas de cosecha en los municipios del Valle.

### Consecuencias Negativas / Mitigaciones:
- **Costo Operativo:** A diferencia del ambiente de Staging (gratuito), la producción requiere un presupuesto mensual para garantizar SLA y evitar suspensiones por inactividad.
- **Mitigación:** Aprovisionamiento inicial con instancias reservadas o escalado a cero en horas no comerciales tras análisis de tráfico.
