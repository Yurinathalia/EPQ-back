# ECC Skills - Uso automático (sin comandos)

Este proyecto tiene instaladas las skills de [Everything Claude Code (ECC)](https://github.com/affaan-m/ecc) en `.agents/skills/`.

## Mecanismo de uso automático

- **No hace falta usar comandos** para aprovecharlas: ante cada tarea, revisa las skills disponibles y **carga proactivamente** con la herramienta `skill` las que sean relevantes, sin esperar a que el usuario lo pida.
- Elige por coincidencia de intención (descripción/nombre de la skill) y aplica sus instrucciones durante la ejecución.
- Puedes cargar varias skills si la tarea lo requiere (p. ej. planificación + TDD + seguridad).

## Guía rápida por tipo de tarea

- **Planificación / diseño**: `blueprint`, `orch-*` (según operación), `planner`/flujos de planificación, `context-and-decisions`, `architecture-decision-records`.
- **Implementación y calidad**: `tdd-workflow`, `coding-standards`, `verification-loop`, `git-workflow`, `error-handling`.
- **Backend Java/Spring Boot (este repositorio)**: `java-coding-standards`, `springboot-patterns`, `springboot-tdd`, `springboot-security`, `springboot-verification`, `jpa-patterns`, `api-design`, `database-migrations`.
- **Seguridad y revisión**: `security-review`, `agent-security-hardening` (cuando aplique), revisión de código.
- **Pruebas**: `tdd-workflow`, `e2e-testing` (si aplica), `java-testing`/skills de testing Java cuando sean pertinentes.
- **Investigación / dudas**: `search-first`, `documentation-lookup`, `deep-research` según necesidad.

## Reglas

1. Antes de empezar trabajo no trivial, identifica y carga la(s) skill(s) ECC aplicables.
2. Sigue el flujo que indiquen (p. ej. TDD: test que falla → implementación mínima → refactor).
3. Las skills están en `.agents/skills/<id>/SKILL.md`; para detalles adicionales lee archivos de soporte dentro de cada skill si la propia skill lo indica.
4. No inventes comandos de ECC: usa las skills/workflows disponibles; si el usuario pide un comando específico, ejecútalo según las capacidades del entorno.
