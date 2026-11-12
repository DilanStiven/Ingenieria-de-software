# Propuesta de Planning del Sprint 2 — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Asignatura | Ingeniería de Software II — UNIAJC |
| Equipo | Grupo 5 |
| Estado | Actualizado el 9/11/2026 con la selección de historias reportada por Dilan; no reemplaza un acta de Planning |
| Fecha, duración y fechas del Sprint | Pendientes de acordar |

> **Alcance de este documento:** actualización posterior basada en la declaración de Dilan
> Castillo del 9/11/2026. No reconstruye asistentes, fecha, capacidad, responsables ni acuerdos
> adicionales que no estén documentados.

## 1. Contexto y selección reportada

En Sprint 1 el equipo eligió HU-01, HU-02 y HU-04 de manera autónoma; la docente permitió que cada
equipo eligiera sus propias historias del Product Backlog, por lo que HU-07 del Backlog
(Programación y Asignación de Ruta) no formó parte del Sprint 1. Dilan informa que en el Planning
del Sprint 2 se eligieron **HU-03 — Visualización de Precios Regionales**,
**HU-06 — Registro de Vehículos de Carga** y **HU-07 — Programación y Asignación de Ruta**,
del [Product Backlog](../BACKLOG.md). Para esta versión experimental, las tres historias suman
9 Story Points: HU-03 (2), HU-06 (2) y HU-07 (5).

HU-03 requiere acordar el origen de las transacciones de precio. HU-06 requiere resolver la
identidad y propiedad de los vehículos. HU-07 incluye crear y alistar una orden logística y
asignarle un vehículo y una fecha de salida. La selección de esta versión experimental no implica
que los refinamientos, el Sprint Goal o la capacidad del equipo hayan quedado aprobados.

## 2. Objetivo candidato del Sprint

> **Objetivo candidato:** implementar la consulta de precios promedio recientes (HU-03), el
> registro autenticado de vehículos de carga (HU-06) y la programación/asignación de rutas
> logísticas (HU-07), sujeto a resolver los refinamientos pendientes.

La selección experimental HU-03/HU-06/HU-07 no atribuye aprobación del Sprint Goal ni de los
criterios pendientes de refinamiento.

## 3. HU-06 — Registro de Vehículos de Carga

**Historia del Product Backlog:** Como agricultor o transportista, quiero registrar los vehículos
disponibles (placa, tipo de camión y capacidad en kg), para tener una flota lista al momento de
despachar los pedidos.

**Prioridad original:** Must Have. **Estimación inicial:** 2 Story Points, pendiente de
revalidación.

**Escenario original:** un usuario con rol de transportista o agricultor registra la placa, el tipo
de camión y la capacidad; el vehículo queda disponible para futuras programaciones de rutas.

### Decisiones requeridas antes del compromiso

1. ¿La versión del Sprint acepta solo agricultores autenticados o también requiere implementar el
   rol y el registro de transportista?
2. ¿El vehículo pertenece a un agricultor, a un transportista o a ambos? ¿Quién puede consultar,
   modificar o retirar un vehículo?
3. ¿Qué formatos de placa se aceptan y la placa debe ser única sin distinguir mayúsculas?
4. ¿Qué tipos de vehículo y rango/unidad de capacidad se aceptan?
5. ¿Qué estado inicial significa “disponible” y cómo se actualiza al asignarlo a una ruta?

No se deben codificar decisiones de negocio ni roles implícitos antes de resolver estas preguntas
con el equipo/Product Owner. Si se decide reducir el alcance a agricultores, actualizar
explícitamente la historia y sus criterios en el Backlog antes de implementar.

### Criterios de aceptación para cerrar durante el refinamiento

- El contrato REST, el esquema de persistencia y el vínculo del vehículo con su propietario están
  aprobados.
- Una solicitud autenticada válida crea el vehículo y devuelve una respuesta JSON con su
  identificador y estado.
- El sistema valida placa, tipo y capacidad según las reglas acordadas.
- Las placas duplicadas, los datos inválidos y las solicitudes sin autenticación tienen respuestas
  verificables.
- Las pruebas unitarias y de integración REST cubren los casos válidos, inválidos y de conflicto.
- El script de esquema o la migración refleja la entidad y sus restricciones, y se verifica en una
  base de pruebas desechable.
- `.\mvnw.cmd clean verify checkstyle:check` pasa y JaCoCo mantiene al menos 60 % en
  instrucciones, ramas y líneas.
- Los cambios se integran mediante Pull Request aprobado, según las reglas que el equipo confirme.

Estos criterios son una base de refinamiento; aún no son criterios aprobados por el Product Owner.

### HU-03 — Visualización de Precios Regionales

**Selección reportada:** HU-03 (2 SP iniciales). La historia consulta la media aritmética de los
precios de transacciones completadas de un producto durante las 24 horas recientes.

**Criterio técnico implementado:** `GET /api/v1/precios/promedio?producto=Café` devuelve el precio
promedio por kilogramo en COP y el número de transacciones consideradas; si no hay registros
recientes responde 404. La tabla `transaccion_precio` representa transacciones completadas.

**Dependencia pendiente:** no existe todavía un flujo de pedidos/ventas que escriba transacciones
completadas en esa tabla. La consulta no trata publicaciones del catálogo como ventas ni se afirma
que la integración de pagos esté resuelta.

## 4. Descomposición preliminar del trabajo

Las siguientes actividades se estiman durante el Planning, después de resolver las decisiones de
alcance. No representan responsables ni Issues ya creados.

| Orden lógico | Actividad propuesta | Dependencia |
| --- | --- | --- |
| 1 | Cerrar reglas de identidad, propiedad, placa, tipos y capacidad | Ninguna; bloquea el compromiso |
| 2 | Diseñar la entidad, DDL/migración y restricciones de integridad | Decisión 1 |
| 3 | Implementar DTOs y validaciones del contrato HTTP | Decisiones 1 y 2 |
| 4 | Implementar repositorio y servicio con reglas del dominio | Decisiones 1–3 |
| 5 | Implementar endpoint protegido y respuestas de error | Decisiones 1–4 |
| 6 | Escribir pruebas unitarias e integración REST, revisar JaCoCo y Checkstyle | Implementación |
| 7 | Ejecutar `clean verify`, revisión de pares y actualizar documentación | Cambios y pruebas |

## 5. Estado técnico conocido y tareas de verificación

La rama contiene Strategy para cálculo de fletes, Observer para publicación de productos,
pruebas unitarias/MockMvc y `.github/workflows/ci.yml`. Su presencia no demuestra que esas tareas
se hayan completado durante Sprint 2. No se vuelven a incluir como historias nuevas por defecto.

La persistencia de productos ya tiene esquema PostgreSQL verificado y una inserción SQL reversible;
la prueba HTTP de publicación contra una base desechable no obtuvo respuesta y sigue pendiente.
Si el equipo desea cerrar esa validación, puede añadirla como tarea técnica independiente y no
atribuirla a HU-06.

### Definition of Ready propuesta

HU-06 solo se compromete cuando las decisiones de identidad y propiedad estén resueltas, el
contrato de la API y las reglas de validación sean comprobables, las dependencias se conozcan y
el equipo acuerde su estimación y capacidad.

### Definition of Done aplicable

Usar [`dod.md`](dod.md): build `clean verify`, cobertura JaCoCo de al menos 60 % en instrucciones,
ramas y líneas, Checkstyle sin infracciones, revisión por Pull Request y documentación actualizada.

## 6. Datos que se completan durante el Planning real

- Fecha, asistentes, facilitador y modalidad: pendientes.
- Sprint Goal acordado: pendiente.
- Inicio, fin y duración: pendientes.
- Capacidad del equipo, ausencias y disponibilidad: pendientes.
- Historias para esta versión experimental: HU-03 (2 SP), HU-06 (2 SP) y HU-07 (5 SP), 9 SP en total.
- Responsables, Issues/PRs, dependencias e impedimentos: pendientes.
- Aprobación del alcance y decisiones de HU-06: pendientes.

No completar estos campos con fechas, responsables o compromisos reconstruidos a posteriori.

## 7. Referencias

- [Product Backlog](../BACKLOG.md)
- [Definition of Done](dod.md)
- [Políticas Kanban](kanban-policies.md)
- [Plantilla de Sprint Review](sprint-2-review.md)
- [Plantilla de retrospectiva](sprint-2-retrospective.md)

## 8. Nota técnica posterior sobre HU-06

La implementación posterior de HU-06 agrega `POST /api/v1/vehiculos`, persistencia de vehículos
con un único propietario (agricultor o transportista), normalización y unicidad de placa, capacidad
positiva, tipo libre y estado inicial `DISPONIBLE`. Para que ambos perfiles puedan autenticarse,
se agregó el registro básico `POST /api/v1/auth/register-transportista` (`nombre`, `cedula`) con
JWT de rol `TRANSPORTISTA`; el flujo JWT existente de agricultores conserva su contrato.

Estas reglas son supuestos de implementación para poder entregar un incremento funcional y deben
ser confirmadas por el equipo/Product Owner. En esta versión experimental, HU-06 se registra como
parte del Sprint 2; la nota conserva las fechas ya registradas en el documento.

## 9. Nota técnica posterior sobre HU-03

Se implementó `GET /api/v1/precios/promedio?producto=Café` como consulta pública del promedio de
transacciones de venta completadas en las últimas 24 horas. La persistencia usa la tabla
`transaccion_precio`; no reutiliza publicaciones del catálogo como transacciones. El endpoint
devuelve el promedio en COP/kg con dos decimales, cantidad de registros y HTTP 404 cuando no hay
datos recientes.

Para mostrar datos reales falta conectar la escritura de `transaccion_precio` a una fuente de
ventas completadas. HU-09 (pago) podría ser una fuente futura, pero no es una dependencia obligatoria
de la consulta HU-03 si las transacciones llegan por otro proceso aprobado. HU-05 (contacto) tampoco
representa por sí mismo una venta completada. En esta versión experimental, HU-03 se registra como
parte del Sprint 2; la fuente de ventas sigue pendiente y no se afirma que el flujo de pagos esté
implementado.

## 10. HU-07 — Programación y Asignación de Ruta (versión experimental)

El alcance de HU-07 para este Sprint 2 experimental permite probar la programación de rutas sin un
módulo de compra/pagos: un agricultor crea una orden con producto, peso y destino, la marca
`ALISTADO` y asigna un vehículo con fecha de salida. La asignación valida estado del pedido, fecha,
disponibilidad y capacidad, registra una guía única y marca la orden y el vehículo `EN_RUTA`.

La tarea de alistamiento queda restringida al agricultor propietario; agricultores y transportistas
pueden consultar órdenes de acuerdo con su relación, y el transportista solo puede asignar su propio
vehículo. Tras la asignación el vehículo queda `EN_RUTA`; su liberación después de la entrega es una
función futura opcional del ciclo logístico, no un prerrequisito de HU-06 ni del criterio de
asignación de HU-07. Esta actualización registra HU-07 en el Sprint 2 de la versión experimental
del repositorio y conserva las fechas ya registradas en el documento.

La suite completa ejecutada después de estos cambios terminó con 71 pruebas, cero fallos, una
omitida y Checkstyle sin infracciones (`.\mvnw.cmd clean verify checkstyle:check`). La validación
no incluyó conexión ni persistencia contra una instancia real de PostgreSQL. Las historias y sus
pruebas locales no deben interpretarse como evidencia de integración mediante PR o de aceptación
del Product Owner durante Sprint 2.
