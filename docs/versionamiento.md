# Versionamiento de AgroValle Connect

Este proyecto usa **Semantic Versioning (SemVer)** en tags Git para identificar los incrementos
de cada Sprint. Las etiquetas de los Sprints 1 a 3 se añaden retrospectivamente a los commits
históricos de `main`; representan hitos del código, no publicaciones binarias realizadas en esas
fechas.

## Versiones por Sprint

| Sprint | Versión | Commit objetivo | Estado |
|---|---|---|---|
| Sprint 1 | `v0.1.0` | `f84265b` — último commit de `main` previo al cierre registrado | Tag histórico |
| Sprint 2 | `v0.2.0` | `a08544c` — cierre documental del Segundo Corte | Tag histórico |
| Sprint 3 | `v0.3.0` | `7f300fb` — último commit de `main` antes del Sprint 4 | Tag histórico |
| Sprint 4 | `v1.0.0` | Commit final de Sprint 4 en `main` | Pendiente de integrar, verificar y aprobar el incremento |

`v1.0.0` se reserva como primera versión estable del producto. No se crea ese tag mientras el
incremento final siga en una rama de trabajo o no haya pasado la revisión y los controles del
repositorio.

## Reglas de uso

- Los tags son anotados y siguen el formato `vMAJOR.MINOR.PATCH`.
- Antes de `1.0.0`, cada incremento compatible puede avanzar `MINOR` (`0.1.0`, `0.2.0`, etc.);
  cambios correctivos aprobados pueden incrementar `PATCH`.
- Después de `1.0.0`, cambios compatibles incrementan `MINOR`, correcciones compatibles incrementan
  `PATCH`, y cambios incompatibles de API o comportamiento requieren incrementar `MAJOR`.
- Los tags de release se publican sobre commits aprobados de `main`, después del PR y de CI. No se
  mueve ni reutiliza un tag ya publicado.
- La versión de un Sprint se refiere al estado de su incremento, no a la versión de Java ni a la
  versión de esquema de Flyway.

El número de versión Maven del artefacto (`pom.xml`) se administra por separado; debe alinearse con
el primer artefacto formal de release antes de publicar `v1.0.0`.
