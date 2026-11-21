---
name: Solicitud de Funcionalidad (Feature Request)
about: Sugerir una nueva característica o requerimiento funcional para AgroValle Connect
title: '[FEAT]: '
labels: enhancement
assignees: ''

---

## 1. Descripción de la Funcionalidad
Descripción clara y concisa de la nueva característica a implementar.

## 2. Caso de Uso y Valor de Negocio
- **Como:** [Rol de usuario: Agricultor, Comprador, Transportista, Administrador]
- **Quiero:** [Acción o requerimiento específico]
- **Para:** [Beneficio o valor esperado en la plataforma]

## 3. Criterios de Aceptación (Escenarios BDD / GWT)
```gherkin
Given [contexto inicial previo a la acción]
When [el usuario realiza la acción en el sistema]
Then [resultado observable y estado persistido esperado]
```

## 4. Consideraciones Técnicas
- Capas afectadas: Controller / Service / Repository / Database
- Endpoints REST propuestos: `HTTP VERBO /api/v1/...`
- Dependencias o migraciones Flyway necesarias:
