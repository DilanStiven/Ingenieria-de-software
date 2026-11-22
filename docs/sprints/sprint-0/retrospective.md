# Sprint 0 Retrospective — AgroValle Connect

**Institución Universitaria Antonio José Camacho**  
**Facultad de Ingeniería | Programa de Ingeniería de Sistemas**  
**Asignatura:** Ingeniería de Software II | **Período:** 2-2026  
**Docente:** Paola Andrea Bedoya Toro  
**Proyecto Integrador:** AgroValle Connect  
**Ubicación:** `docs/sprints/sprint-0/retrospective.md`  

---

## 1. Dinámica de Retrospectiva: Start - Stop - Continue

Al finalizar el Sprint 0 de preparación técnica, el equipo realizó una sesión reflexiva para identificar aprendizajes y optimizar el flujo de trabajo para el inicio del desarrollo funcional en el Sprint 1:

### Start (Comenzar a hacer)
- **Desarrollo Guiado por Pruebas (TDD):** Escribir primero las pruebas unitarias en JUnit 5 y Mockito antes de implementar la lógica en los servicios.
- **Revisión Rigurosa de PRs:** Exigir que ningún PR se fusione sin que el pipeline de GitHub Actions esté en verde y se cuente con la aprobación formal de al menos un compañero.
- **Documentación Continua en Markdown:** Mantener los diagramas y especificaciones BDD sincronizados en texto plano junto al código.

### Stop (Dejar de hacer)
- **Commits con mensajes genéricos:** Eliminar cualquier intento de usar mensajes como "arreglos", "cambios", "subiendo"; forzar siempre la convención de `feat:`, `fix:`, `docs:`, etc.
- **Cambios locales sin actualizar rama base:** Dejar de trabajar en features desactualizadas respecto a `develop` para evitar conflictos complejos de merge.
- **Modificar configuraciones directamente en caliente:** Asegurar que los perfiles y variables de entorno estén desacoplados.

### Continue (Continuar haciendo)
- **Uso estricto de Checkstyle y Husky:** Mantener la disciplina de formateo limpio antes de cada push para prevenir deuda técnica.
- **Comunicación fluida en Daily Scrums:** Continuar identificando impedimentos a tiempo para proteger la velocidad del equipo.
- **Alineación con el DoD:** Usar la lista de chequeo de calidad en cada incremento de software.

---

## 2. Compromisos de Mejora para el Sprint 1

1. Mantener un umbral de cobertura mínimo del 60% medido con JaCoCo desde la primera historia de usuario.
2. Descomponer las historias de usuario en tareas técnicas con una estimación no superior a 3 Story Points por tarea para evitar bloqueos en el tablero Kanban.
