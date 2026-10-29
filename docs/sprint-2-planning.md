# Propuesta de Planning del Sprint 2 — AgroValle Connect

| Campo | Valor |
| --- | --- |
| Proyecto | AgroValle Connect |
| Asignatura | Ingeniería de Software II — UNIAJC |
| Equipo | Grupo 5 |
| Estado | Propuesta para revisión del equipo; no confirma que se haya realizado el Planning |
| Fecha, duración y fechas del Sprint | Pendientes de acordar |

> **Alcance de este documento:** el equipo autorizó seleccionar una historia del Product
> Backlog para proponerla al Sprint 2. La selección que sigue es una propuesta para discutir, no
> un compromiso Scrum ni evidencia de que el Sprint se haya ejecutado. Fechas, capacidad,
> asistentes, responsables, estimación y aceptación final se completan únicamente con datos del
> Planning real.

## 1. Contexto y selección propuesta

En Sprint 1 el equipo eligió HU-01, HU-02 y HU-04 de manera autónoma; la docente permitió que cada
equipo eligiera sus propias historias del Product Backlog, por lo que HU-07 del Backlog
(Programación y Asignación de Ruta) no formó parte del Sprint 1. Para Sprint 2 se propone refinar **HU-06 — Registro de Vehículos de Carga**
del [Product Backlog](../BACKLOG.md). La estimación de 2 Story Points es la estimación inicial del
Backlog y debe confirmarse por el equipo antes de comprometer trabajo.

Se propone HU-06 porque no depende de la implementación de ventas, pagos, pedidos ni entregas. Sin
embargo, el modelo actual solo autentica agricultores y no representa transportistas ni roles de
usuario. La historia no está lista para compromiso hasta acordar el alcance de identidad y
propiedad del vehículo.

## 2. Objetivo candidato del Sprint

> **Propuesta:** acordar y, si queda lista, implementar el registro autenticado de vehículos de
> carga con validaciones de placa y capacidad, persistencia relacional y pruebas automatizadas,
> respetando el límite de cobertura, Checkstyle y la revisión por Pull Request.

La selección final y el Sprint Goal se confirman en la reunión real de Planning. El equipo puede
seleccionar otra historia si HU-06 no supera la Definition of Ready.

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
- Historias finalmente seleccionadas y estimaciones confirmadas: pendientes.
- Responsables, Issues/PRs, dependencias e impedimentos: pendientes.
- Aprobación del alcance y decisiones de HU-06: pendientes.

No completar estos campos con fechas, responsables o compromisos reconstruidos a posteriori.

## 7. Referencias

- [Product Backlog](../BACKLOG.md)
- [Definition of Done](dod.md)
- [Políticas Kanban](kanban-policies.md)
- [Plantilla de Sprint Review](sprint-2-review.md)
- [Plantilla de retrospectiva](sprint-2-retrospective.md)
