# Reporte JaCoCo — CI Java 25, ejecución #91

> **Evidencia histórica:** este reporte refleja la ejecución del 28/10/2026 con Java 25.
> La guía académica exige Java 17; por lo tanto, estas métricas no se presentan como validación
> de la configuración vigente. La validación CI con Java 17 corresponde al
> [PR #60](https://github.com/DilanStiven/Ingenieria-de-software/pull/60).

| Dato | Resultado |
| --- | --- |
| Repositorio | AgroValle Connect |
| Rama | `ci/java25-verification` |
| Commit | `0c9d664fdd944904a9d7dc3518f199204a11c548` |
| Pull Request | [#56 hacia `develop`](https://github.com/DilanStiven/Ingenieria-de-software/pull/56) |
| Workflow | [GitHub Actions #91](https://github.com/DilanStiven/Ingenieria-de-software/actions/runs/40811041503) |
| JDK | Eclipse Temurin 25 |
| Artefacto | [Reporte JaCoCo completo en ZIP](jacoco-java25-run-91.zip) |

## Cobertura agregada

| Métrica | Cubiertas | Total | Cobertura | Mínimo configurado |
| --- | ---: | ---: | ---: | ---: |
| Instrucciones | 854 | 1.064 | **80,26 %** | 60 % |
| Ramas | 19 | 26 | **73,08 %** | 60 % |
| Líneas | 245 | 302 | **81,13 %** | 60 % |

Los valores se calcularon sumando los contadores por clase del `jacoco.csv` incluido en el
artefacto descargado de la ejecución #91. JaCoCo y el workflow completaron correctamente. El
reporte corresponde al commit de la rama del PR #56 y documenta esa ejecución; no sustituye la
evidencia de compilación y CI con Java 17.
