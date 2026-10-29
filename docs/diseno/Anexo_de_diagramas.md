# Anexo de diagramas

Sprint de diseño: modelo de datos, arquitectura y prototipado

Ingeniería de Software II \| Proyecto AgroValle \| Estudiante(s): Grupo 5

> **Nota de alcance:** estos diagramas son artefactos del diseño académico inicial. El modelo
> físico, el MER y el modelo de dominio representan cinco entidades (`Municipio`, `Agricultor`,
> `Producto`, `Cosecha` y `Oferta`) que no corresponden literalmente al modelo JPA actual. El
> diagrama de despliegue y las capturas son referencias de diseño, no fuente de verdad para el
> esquema vigente. El modelo JPA implementado actualmente tiene `Agricultor` y `Producto` y usa
> Java 25. La vista actual y su DDL están documentados en
> [`Documento_de_normalizacion.md`](Documento_de_normalizacion.md) y [`schema.sql`](schema.sql).

## Índice de diagramas

| **N.º** | **Diagrama**                | **Qué representa**                                             | **Origen**         |
|---------|-----------------------------|----------------------------------------------------------------|--------------------|
| 1       | Captura de pgAdmin (ERD)    | Referencia histórica del modelo académico de cinco tablas; no acredita el esquema actual | Captura del equipo |
| 2       | Modelo físico (referencia)  | Diseño propuesto de cinco tablas con tipos, PK, FK y UK        | Generado           |
| 3       | MER conceptual              | Diseño académico propuesto, previo a implementación            | Generado           |
| 4       | Modelo de dominio UML       | Diseño académico propuesto; no son cinco entidades JPA vigentes | Generado           |
| 5       | Secuencia: Registrar oferta | Recorrido de una petición por las capas del sistema            | Generado           |
| 6       | Componentes                 | Paquetes del sistema y sus dependencias                        | Generado           |
| 7       | Despliegue                  | Nodos físicos donde corre el sistema                           | Generado           |
| 8       | Flujo de pantallas          | Navegación entre las 6 pantallas                               | Generado           |
| 9 a 14  | Mockups (6 pantallas)       | Wireframes de baja fidelidad de cada pantalla                  | Generado           |

## Punto 1. Modelo de datos

### Diagrama 1. Captura de pgAdmin (ERD de la base de datos)

**Qué es:** captura de referencia compartida por el equipo, identificada como PostgreSQL 18.
Representa el modelo académico de cinco tablas; no demuestra el esquema de la aplicación
actualmente configurada en el repositorio.

**Uso y limitación:** sirve como referencia visual del diseño propuesto. No se presenta como
evidencia de que el script se ejecutó correctamente ni de que esas cinco tablas existan en la
base desplegada.

![Figura 1. Captura de pgAdmin (ERD de la base de datos)](images/diagrama-01-pgadmin-erd.png)

*Figura 1. Captura de pgAdmin (ERD de la base de datos)*

### Diagrama 2. Modelo físico (diagrama de referencia)

**Qué es:** representación del mismo modelo con los tipos de dato y las restricciones: PK (llave primaria), FK (llave foránea) y UK (valor único).

**Para qué sirve:** documentar la estructura exacta de cada tabla, y ver de un vistazo cómo se enlazan las llaves foráneas.

![Figura 2. Modelo físico (diagrama de referencia)](images/diagrama-02-modelo-fisico.png)

*Figura 2. Modelo físico (diagrama de referencia)*

### Diagrama 3. MER conceptual

**Qué es:** modelo entidad-relación en su nivel conceptual: muestra las entidades, sus atributos y las relaciones uno a muchos, sin llaves foráneas.

**Para qué sirve:** representar el negocio antes de pensar en tablas; es el diagrama que se hace primero, antes de normalizar.

![Figura 3. MER conceptual](images/diagrama-03-mer-conceptual.png)

*Figura 3. MER conceptual*

### Diagrama 4. Modelo de dominio UML

**Qué es:** diagrama de clases con las cinco entidades, sus atributos con tipos Java y las asociaciones con su multiplicidad (1 a 0..\*).

**Para qué sirve:** mostrar cómo se ve el modelo de datos desde la programación orientada a objetos; es la base de las entidades JPA.

![Figura 4. Modelo de dominio UML](images/diagrama-04-modelo-dominio-uml.png)

*Figura 4. Modelo de dominio UML*

### Modelo relacional actualmente implementado

Este DER representa las dos entidades JPA vigentes y la relación de propiedad de los productos.
Las cinco entidades de los diagramas anteriores se conservan como propuesta académica.

```mermaid
erDiagram
    AGRICULTOR ||--o{ PRODUCTOS : publica
    AGRICULTOR {
        bigint id PK
        varchar nombre
        varchar ubicacion_valle
        varchar cedula UK
    }
    PRODUCTOS {
        bigint id PK
        varchar nombre
        varchar categoria
        varchar municipio
        numeric cantidad
        numeric precio_unitario
        date fecha_cosecha
        varchar estado
        bigint agricultor_id FK
    }
```

## Punto 2. Arquitectura de software

### Diagrama 5. Secuencia: Registrar oferta

**Qué es:** diagrama de secuencia del caso de uso "Registrar oferta", desde el agricultor hasta la base de datos, pasando por vista, controlador, servicio y repositorios.

**Para qué sirve:** explicar el orden de las llamadas entre las capas y qué mensaje intercambia cada una.

![Figura 5. Secuencia: Registrar oferta](images/diagrama-05-secuencia-registrar-oferta.png)

*Figura 5. Secuencia: Registrar oferta*

### Diagrama 6. Componentes

**Qué es:** diagrama de los paquetes de la arquitectura MVC en capas (controller, service, repository, dto, mapper y model) y de quién depende cada uno.

**Para qué sirve:** mostrar la estructura estática del sistema y comprobar que cada capa solo se comunica con la contigua.

![Figura 6. Componentes](images/diagrama-06-componentes.png)

*Figura 6. Componentes*

### Diagrama 7. Despliegue

**Qué es:** diagrama de los tres nodos físicos: navegador del usuario, servidor de aplicaciones (Spring Boot) y servidor de base de datos (PostgreSQL), con sus protocolos y puertos.

**Para qué sirve:** indicar dónde se ejecuta cada pieza del sistema y cómo se comunican entre sí.

![Figura 7. Despliegue](images/diagrama-07-despliegue.png)

*Figura 7. Despliegue*

## Punto 3. Prototipado

### Diagrama 8. Flujo de pantallas

**Qué es:** diagrama de navegación entre las seis pantallas del sistema, con la acción que lleva de una a otra.

**Para qué sirve:** validar con el Product Owner el recorrido completo del usuario.

![Figura 8. Flujo de pantallas](images/diagrama-08-flujo-pantallas.png)

*Figura 8. Flujo de pantallas*

### Diagrama 9. Mockup: Login

**Qué es:** wireframe de la pantalla de inicio de sesión.

**Para qué sirve:** validar los campos de acceso y el enlace al registro.

![Figura 9. Mockup: Login](images/diagrama-09-mockup-login.png)

*Figura 9. Mockup: Login*

### Diagrama 10. Mockup: Registro de agricultor

**Qué es:** wireframe del formulario de registro de un nuevo agricultor.

**Para qué sirve:** validar los datos que se piden al crear la cuenta.

![Figura 10. Mockup: Registro de agricultor](images/diagrama-10-mockup-registro-agricultor.png)

*Figura 10. Mockup: Registro de agricultor*

### Diagrama 11. Mockup: Panel principal

**Qué es:** wireframe de la pantalla de inicio del agricultor, con las tres opciones principales y sus últimas cosechas.

**Para qué sirve:** validar el menú de acceso a las funciones del sistema.

![Figura 11. Mockup: Panel principal](images/diagrama-11-mockup-panel-principal.png)

*Figura 11. Mockup: Panel principal*

### Diagrama 12. Mockup: Registrar cosecha

**Qué es:** wireframe del formulario para registrar una cosecha.

**Para qué sirve:** validar los campos de producto, cantidad y fecha.

![Figura 12. Mockup: Registrar cosecha](images/diagrama-12-mockup-registrar-cosecha.png)

*Figura 12. Mockup: Registrar cosecha*

### Diagrama 13. Mockup: Publicar oferta

**Qué es:** wireframe del formulario para publicar una cosecha como oferta de venta.

**Para qué sirve:** validar los campos de precio y cantidad disponible.

![Figura 13. Mockup: Publicar oferta](images/diagrama-13-mockup-publicar-oferta.png)

*Figura 13. Mockup: Publicar oferta*

### Diagrama 14. Mockup: Lista de ofertas

**Qué es:** wireframe de la pantalla que lista las ofertas con filtros y paginación.

**Para qué sirve:** validar los filtros y las columnas de la tabla.

![Figura 14. Mockup: Lista de ofertas](images/diagrama-14-mockup-lista-ofertas.png)

*Figura 14. Mockup: Lista de ofertas*
