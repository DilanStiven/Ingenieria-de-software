# Definition of Done (DoD) — AgroValle Connect

Contrato técnico basado en **ISO/IEC 25010**. Ningún incremento funcional se considera "terminado" si no cumple **todos** los puntos de este checklist.

- [ ] **Build Local:** El proyecto compila sin errores en el entorno local (`mvn clean install`).
- [ ] **Linter Pass (Checkstyle):** Cero advertencias estáticas, validado contra `checkstyle.xml` (Google Java Style).
- [ ] **Functional Correctness:** El 100% de las pruebas unitarias existentes pasan (`mvn test`), con cobertura mínima del 60% verificada con JaCoCo.
- [ ] **Peer Review:** Todo Pull Request fue revisado y aprobado por al menos un compañero de equipo antes de fusionar a `develop`/`main`.
- [ ] **Documentation:** El `README.md` y la documentación de `/docs` están actualizados con cada cambio relevante.
- [ ] **Commits:** El historial sigue estrictamente **Conventional Commits** (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).
- [ ] **Automatización:** Hooks de Husky (`.husky/pre-commit`) activos, bloqueando el commit si falla el linter o las pruebas.

## Firma del equipo

Al firmar, cada integrante confirma que comprende y se compromete a cumplir este contrato de calidad durante todo el proyecto.

| Nombre                          | Usuario GitHub | Fecha      |
|---                              |---             |---         |
| Dilan Stiven Castillo Valencia  | DilanStiven    | 16/09/2026 |
| Kevin Estiven Lucumi Polo       | Steven-L777    | 16/09/2026 |
| Carlos Andres Rosales Lara      | adnoireph      | 16/09/2026 |
| Santiago Edilno Hurtado Cuenu   | santiageao     | 16/09/2026 |
