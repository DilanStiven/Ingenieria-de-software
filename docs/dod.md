# Definition of Done (DoD) — AgroValle Connect

Contrato técnico basado en **ISO/IEC 25010**. Ningún incremento funcional se considera "terminado" si no cumple **todos** los puntos de este checklist.

- [ ] **Build Local:** El proyecto compila sin errores y completa las verificaciones del ciclo Maven (`./mvnw clean verify`; `.\mvnw.cmd clean verify` en Windows).
- [ ] **Linter Pass (Checkstyle):** Cero advertencias estáticas, validado contra `checkstyle.xml` (Google Java Style).
- [ ] **Functional Correctness:** El 100% de las pruebas unitarias existentes pasan y JaCoCo confirma al menos 60% en instrucciones, ramas y líneas (`verify`; ejecutar solo `test` no activa el umbral ligado a esa fase).
- [ ] **Peer Review:** Todo Pull Request fue revisado y aprobado por al menos un compañero de equipo antes de fusionar a `develop`/`main`.
- [ ] **Documentation:** El `README.md` y la documentación de `/docs` están actualizados con cada cambio relevante.
- [ ] **Commits:** El historial sigue estrictamente **Conventional Commits** (`feat:`, `fix:`, `docs:`, `test:`, `chore:`).
- [ ] **Automatización:** Hook de pre-commit (`.husky/pre-commit`) activo, bloqueando el commit si falla Checkstyle, alguna prueba o el umbral de JaCoCo mediante `clean verify`.

## Firma del equipo

Al firmar, cada integrante confirma que comprende y se compromete a cumplir este contrato de calidad durante todo el proyecto.

| Nombre                          | Usuario GitHub | Fecha      |
|---                              |---             |---         |
| Dilan Stiven Castillo Valencia  | DilanStiven    | 16/09/2026 |
| Kevin Estiven Lucumi Polo       | Steven-L777    | 16/09/2026 |
| Carlos Andres Rosales Lara      | adnoireph      | 16/09/2026 |
| Santiago Edilmo Hurtado Cuenu   | santiageao     | 16/09/2026 |
