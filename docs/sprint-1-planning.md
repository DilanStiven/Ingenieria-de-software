# Sprint 1 Planning — AgroValle Connect

| Campo | Valor |
| --- | --- |
| **Proyecto** | AgroValle Connect |
| **Asignatura** | Ingeniería de Software II — UNIAJC |
| **Período / Docente** | 2-2026 / Paola Andrea Bedoya Toro |
| **Equipo Scrum** | Grupo 5 |
| **Integrantes** | Carlos Andres Rosales Lara CC 1030020342, Kevin Estiven Lucumi Polo CC 1030534697, Dilan Estiven Castillo CC 1089002055, Santiago Edilmo Cueno Hurtado CC 1089001065|
| **Duración del Sprint** | 2 semanas (24/09/2026 – 09/10/2026) |
| **Archivo** | `docs/sprint-1-planning.md` |
| **Norma de calidad** | ISO/IEC 25010:2011 |

---

## 1. Sprint Goal

> **Entregar un incremento funcional en el que un agricultor pueda registrarse, obtener un token
> de acceso y publicar sus cosechas, y un comprador pueda filtrar el catálogo por municipio y
> categoría.**

Al finalizar el Sprint 1 el sistema permitirá:

1. Registrar un productor mediante `POST /api/v1/auth/register`, con persistencia verificada en
   PostgreSQL. La respuesta incluye un token JWT de acceso.
2. Publicar una cosecha en un endpoint protegido con JWT, validando datos y fecha de cosecha.
3. Consultar el catálogo con `GET /api/v1/productos?municipio=&categoria=`.

---

## 2. Capacidad del Equipo y Selección

### 2.1 Capacidad

| Métrica | Valor |
| --- | --- |
| Velocidad inicial | **10 Story Points** |
| Integrantes | 4 |
| Dedicación | 6 h por persona por semana |
| **Capacidad total** | 4 × 6 h × 2 semanas = **48 h** |
| Horas planificadas en tareas | **38 h** (≈ 79 % de la capacidad) |
| Reserva para ceremonias e imprevistos | **10 h** (≈ 21 %): Daily Scrums, Sprint Review, Retrospectiva, revisión de PRs y correcciones |
| Historias seleccionadas | 3 (HU-01, HU-02, HU-04) |
| Prioridad global | Must Have (MoSCoW) |
| Stack | Java 25, Spring Boot 3.4+, Spring Security + JWT, PostgreSQL 15, Flyway, JUnit 5, Mockito, Checkstyle |

Las 38 h planificadas caben en la capacidad de 48 h y dejan margen de seguridad, lo que justifica
comprometer **10 Story Points** en este Sprint.

### 2.2 Historias seleccionadas

| ID | Historia de Usuario | Prioridad | SP | Horas |
| --- | --- | --- | --- | --- |
| HU-01 | Registro de Agricultores | Must Have | 3 | 15 h |
| HU-02 | Publicación de Productos | Must Have | 5 | 16 h |
| HU-04 | Filtro de Categorías y Municipios | Must Have | 2 | 7 h |
| — | **Total** | | **10** | **38 h** |

> **Nota:** HU-03 no entra en este Sprint y permanece en el Product Backlog (`BACKLOG.md`). La
> numeración de tareas de HU-04 conserva el prefijo `4.x` para mantener trazabilidad con el
> backlog.

### 2.3 Asignación de responsables (propuesta inicial)

| Integrante | Tareas | Horas |
| --- | --- | --- |
| Carlos Andres Rosales Lara | 1.1, 1.2, 1.3, 1.7, 1.8, 4.1 | 10 h |
| Kevin Estiven Lucumi Polo | 1.4, 1.5, 2.3, 2.5, 2.7 | 10 h |
| Dilan Estiven Castillo | 1.6, 2.1, 2.2, 4.2, 4.3 | 9 h |
| Santiago Edilmo Cueno Hurtado | 2.4, 2.6, 4.4, 4.5 | 9 h |
| **Total** | 20 tareas | **38 h** |

La asignación queda balanceada (≈ 9,5 h por persona frente a 12 h de capacidad individual) y
puede reajustarse en la Daily Scrum según el avance.

### 2.4 Orden de ejecución y dependencias

```
1.1 → 1.2 → 1.3 → 1.4 (JwtService) → 1.5 → 1.6 → 1.7 → 1.8      (HU-01)
                     │
                     └→ 2.3 (filtro JWT + SecurityConfig)
                            │
      1.1 → 2.1 → 2.2 ──────┴→ 2.4 → 2.5 → 2.6 → 2.7            (HU-02)
                  │
                  └→ 4.1 → 4.2 → 4.3 → 4.4 → 4.5                  (HU-04)
```

- HU-02 depende de HU-01 (existe el productor y el `JwtService`) y de la tarea 2.3.
- HU-04 depende de la tabla `productos` creada en la tarea 2.1.

### 2.5 Decisión de alcance: autenticación en el Sprint 1

El BDD aprobado de HU-01 no incluye contraseña. Por lo tanto, en este Sprint **no existe
`/login`**: el token JWT se emite al momento del registro (`201 Created`) y se usa para
publicar productos. El inicio de sesión con credenciales queda como historia futura en el
Product Backlog.

---

## 3. HU-01 — Registro de Agricultores (3 SP)

### 3.1 Historia de Usuario

*Como* Agricultor,
*quiero* registrarme en la plataforma,
*para* ofrecer mis productos y recibir un acceso autenticado.

### 3.2 Escenarios BDD (Given-When-Then)

**Escenario 1 — Registro exitoso**

```gherkin
Given que el usuario accede a POST /api/v1/auth/register
When  envía un JSON con nombre, ubicacion_valle y cedula válida
Then  el sistema responde 201 Created, el registro persiste en PostgreSQL
      y la respuesta incluye un token JWT
```

**Escenario 2 — Cédula duplicada**

```gherkin
Given que ya existe un productor con la cédula "1234567890"
When  se intenta registrar otro productor con la misma cédula
Then  el sistema responde 409 Conflict y no inserta ningún registro
```

**Escenario 3 — Datos inválidos**

```gherkin
Given que el usuario accede a POST /api/v1/auth/register
When  envía un JSON con nombre vacío o cédula no numérica
Then  el sistema responde 400 Bad Request con el detalle del campo inválido
```

### 3.3 Descomposición Técnica

#### Tarea 1.1 — Entidad `Productor` y migración DDL (2 h) — *Carlos*

- **Componente:** PostgreSQL 15, Flyway, Spring Data JPA.
- **ISO/IEC 25010:** *Mantenibilidad → Modificabilidad* (DDL versionado y reproducible);
  *Fiabilidad → Madurez* (restricciones `NOT NULL`/`UNIQUE` evitan datos inconsistentes).
- **Ubicación:** `src/main/java/com/agrovalle/models/Productor.java` y
  `src/main/resources/db/migration/V1__create_usuarios_productores.sql`

```sql
-- V1__create_usuarios_productores.sql
CREATE TABLE usuarios_productores (
  id              BIGSERIAL    PRIMARY KEY,
  nombre          VARCHAR(150) NOT NULL,
  ubicacion_valle VARCHAR(100) NOT NULL,
  cedula          VARCHAR(20)  NOT NULL UNIQUE,
  fecha_registro  TIMESTAMP    NOT NULL DEFAULT NOW()
);
```

```java
/** Productor agrícola registrado en la plataforma. */
@Entity
@Table(name = "usuarios_productores")
public class Productor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String nombre;

  @Column(name = "ubicacion_valle", nullable = false, length = 100)
  private String ubicacionValle;

  @Column(unique = true, nullable = false, length = 20)
  private String cedula;

  @Column(name = "fecha_registro", nullable = false, updatable = false)
  private LocalDateTime fechaRegistro = LocalDateTime.now();

  // Getters y setters omitidos por brevedad
}
```

#### Tarea 1.2 — `ProductorRepository` (1 h) — *Carlos*

- **Componente:** Java 25, Spring Data JPA (métodos derivados).
- **ISO/IEC 25010:** *Adecuación Funcional → Corrección funcional* (la consulta de existencia
  es exacta y se resuelve en la base de datos).
- **Ubicación:** `src/main/java/com/agrovalle/repositories/ProductorRepository.java`

```java
/** Acceso a datos de productores. */
@Repository
public interface ProductorRepository extends JpaRepository<Productor, Long> {

  boolean existsByCedula(String cedula);

  Optional<Productor> findByCedula(String cedula);
}
```

#### Tarea 1.3 — DTOs, validaciones y excepción de dominio (2 h) — *Carlos*

- **Descripción:** Crear `ProductorRequestDTO` (con Bean Validation), `ProductorResponseDTO`,
  `ProductorDuplicadoException` y `ErrorResponse`.
- **Componente:** Jakarta Bean Validation (`@NotBlank`, `@Size`, `@Pattern`).
- **ISO/IEC 25010:** *Seguridad → Integridad* (se rechazan datos malformados antes de llegar a
  la lógica de negocio); *Fiabilidad → Tolerancia a fallos* (errores controlados).
- **Ubicación:** `src/main/java/com/agrovalle/dto/` y `.../exceptions/`

```java
/** Datos de entrada para el registro de un productor. */
public record ProductorRequestDTO(
    @NotBlank @Size(max = 150) String nombre,
    @JsonProperty("ubicacion_valle") @NotBlank @Size(max = 100) String ubicacionValle,
    @NotBlank @Pattern(regexp = "\\d{6,10}", message = "Cédula inválida") String cedula) {}

/** Respuesta del registro, con el token de acceso. */
public record ProductorResponseDTO(Long id, String mensaje, String token) {}

/** Estructura estándar de error de la API. */
public record ErrorResponse(int status, String mensaje) {}
```

#### Tarea 1.4 — `JwtService`: emisión y validación de tokens (2 h) — *Kevin*

- **Componente:** Spring Security, librería jjwt.
- **ISO/IEC 25010:** *Seguridad → Autenticidad* (el token firmado identifica al productor);
  *Seguridad → Integridad* (firma HMAC impide alterar el token).
- **Ubicación:** `src/main/java/com/agrovalle/security/JwtService.java`
- **Configuración:** `jwt.secret` y `jwt.expiration-ms` se leen de variables de entorno; el
  secreto **no** se sube al repositorio.

```java
/** Emisión y validación de tokens JWT. */
@Service
public class JwtService {

  private final SecretKey key;
  private final long expiracionMs;

  public JwtService(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration-ms}") long expiracionMs) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expiracionMs = expiracionMs;
  }

  /** Genera un token cuyo subject es la cédula del productor. */
  public String generarToken(String cedula) {
    Date ahora = new Date();
    return Jwts.builder()
        .subject(cedula)
        .issuedAt(ahora)
        .expiration(new Date(ahora.getTime() + expiracionMs))
        .signWith(key)
        .compact();
  }

  /** Valida el token y retorna la cédula contenida en él. */
  public String extraerCedula(String token) {
    return Jwts.parser().verifyWith(key).build()
        .parseSignedClaims(token).getPayload().getSubject();
  }
}
```

#### Tarea 1.5 — `ProductorService` (2 h) — *Kevin*

- **Componente:** Spring (`@Service`, `@Transactional`), `JwtService`.
- **ISO/IEC 25010:** *Seguridad → Integridad* (unicidad de cédula);
  *Fiabilidad → Tolerancia a fallos* (excepción controlada, sin estados inconsistentes).
- **Ubicación:** `src/main/java/com/agrovalle/services/ProductorService.java`

```java
/** Lógica de negocio del registro de productores. */
@Service
public class ProductorService {

  private final ProductorRepository repository;
  private final JwtService jwtService;

  public ProductorService(ProductorRepository repository, JwtService jwtService) {
    this.repository = repository;
    this.jwtService = jwtService;
  }

  /** Registra un productor validando la unicidad de la cédula y emite su token. */
  @Transactional
  public ProductorResponseDTO registrarProductor(ProductorRequestDTO dto) {
    if (repository.existsByCedula(dto.cedula())) {
      throw new ProductorDuplicadoException(
          "Ya existe un productor con la cédula: " + dto.cedula());
    }
    Productor productor = new Productor();
    productor.setNombre(dto.nombre());
    productor.setUbicacionValle(dto.ubicacionValle());
    productor.setCedula(dto.cedula());
    Productor guardado = repository.save(productor);
    String token = jwtService.generarToken(guardado.getCedula());
    return new ProductorResponseDTO(
        guardado.getId(), "Productor registrado exitosamente", token);
  }
}
```

#### Tarea 1.6 — `ProductorController` y manejador global de errores (2 h) — *Dilan*

- **Componente:** Spring Boot (`@RestController`, `@Valid`, `@RestControllerAdvice`).
- **ISO/IEC 25010:** *Compatibilidad → Interoperabilidad* (HTTP/JSON estándar);
  *Mantenibilidad → Modularidad* (separación Controller/Service/Repository).
- **Ubicación:** `.../controllers/ProductorController.java` y
  `.../exceptions/GlobalExceptionHandler.java`

```java
/** Endpoints de registro de productores. */
@RestController
@RequestMapping("/api/v1/auth")
public class ProductorController {

  private final ProductorService service;

  public ProductorController(ProductorService service) {
    this.service = service;
  }

  @PostMapping("/register")
  public ResponseEntity<ProductorResponseDTO> registrar(
      @Valid @RequestBody ProductorRequestDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarProductor(dto));
  }
}

/** Traduce excepciones de dominio a respuestas HTTP. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductorDuplicadoException.class)
  public ResponseEntity<ErrorResponse> duplicado(ProductorDuplicadoException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(409, ex.getMessage()));
  }

  @ExceptionHandler({FechaCosechaInvalidaException.class, MunicipioNoValidoException.class})
  public ResponseEntity<ErrorResponse> reglaNegocio(RuntimeException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> invalido(MethodArgumentNotValidException ex) {
    String detalle = ex.getBindingResult().getFieldErrors().stream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .collect(Collectors.joining("; "));
    return ResponseEntity.badRequest().body(new ErrorResponse(400, detalle));
  }
}
```

#### ⚠️ Tarea 1.7 — Traducción BDD → Pruebas JUnit 5 **[OBLIGATORIA]** (3 h) — *Carlos*

- **Descripción:** Traducir los 3 escenarios BDD de HU-01 a pruebas unitarias
  (`ProductorServiceTest`, Mockito) e integradas (`ProductorControllerTest`, MockMvc). Añadir
  `ProductorRepositoryIT` con Testcontainers (PostgreSQL 15) que verifique la persistencia real.
- **Componente:** JUnit 5, Mockito, Spring Boot Test (`@WebMvcTest`, `@DataJpaTest`),
  Testcontainers.
- **ISO/IEC 25010:** *Adecuación Funcional → Corrección funcional* (el comportamiento cumple
  las reglas de negocio); *Mantenibilidad → Capacidad de ser probado* (suite que previene
  regresiones).
- **Ubicación:** `src/test/java/com/agrovalle/services/ProductorServiceTest.java`,
  `.../controllers/ProductorControllerTest.java`, `.../repositories/ProductorRepositoryIT.java`

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductorService — HU-01")
class ProductorServiceTest {

  @Mock private ProductorRepository repository;
  @Mock private JwtService jwtService;
  @InjectMocks private ProductorService service;

  private final ProductorRequestDTO dto =
      new ProductorRequestDTO("Juan Pérez", "Dagua", "1234567890");

  @Test
  @DisplayName("DADO cédula nueva, CUANDO registra, ENTONCES guarda y retorna id y token")
  void dadoCedulaNueva_cuandoRegistra_entoncesGuardaYRetornaToken() {
    when(repository.existsByCedula("1234567890")).thenReturn(false);
    when(jwtService.generarToken("1234567890")).thenReturn("token-jwt");
    when(repository.save(any(Productor.class))).thenAnswer(inv -> {
      Productor p = inv.getArgument(0);
      p.setId(1L);
      return p;
    });

    ProductorResponseDTO respuesta = service.registrarProductor(dto);

    assertEquals(1L, respuesta.id());
    assertEquals("token-jwt", respuesta.token());
    verify(repository).save(any(Productor.class));
  }

  @Test
  @DisplayName("DADO cédula existente, CUANDO registra, ENTONCES lanza excepción")
  void dadoCedulaExistente_cuandoRegistra_entoncesLanzaExcepcion() {
    when(repository.existsByCedula("1234567890")).thenReturn(true);

    assertThrows(ProductorDuplicadoException.class, () -> service.registrarProductor(dto));
    verify(repository, never()).save(any());
  }
}
```

```java
@WebMvcTest(ProductorController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("ProductorController — HU-01")
class ProductorControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ProductorService service;

  private static final String BODY_VALIDO = """
      {"nombre":"Juan Pérez","ubicacion_valle":"Dagua","cedula":"1234567890"}""";

  @Test
  @DisplayName("Escenario 1: registro exitoso → 201 con token")
  void registroExitoso() throws Exception {
    given(service.registrarProductor(any())).willReturn(
        new ProductorResponseDTO(1L, "Productor registrado exitosamente", "token-jwt"));

    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(BODY_VALIDO))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.token").value("token-jwt"));
  }

  @Test
  @DisplayName("Escenario 2: cédula duplicada → 409")
  void cedulaDuplicada() throws Exception {
    given(service.registrarProductor(any()))
        .willThrow(new ProductorDuplicadoException("Cédula duplicada"));

    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(BODY_VALIDO))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Escenario 3: datos inválidos → 400")
  void datosInvalidos() throws Exception {
    String invalido = """
        {"nombre":"","ubicacion_valle":"Dagua","cedula":"abc"}""";

    mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(invalido))
        .andExpect(status().isBadRequest());
  }
}
```

> Si el proyecto usa Spring Boot anterior a 3.4, reemplazar `@MockitoBean` por `@MockBean`.

#### Tarea 1.8 — Auditoría Checkstyle (1 h) — *Carlos*

- **Descripción:** Ejecutar `mvn checkstyle:check` sobre las tareas 1.1–1.7 hasta obtener 0
  errores (Google Java Style: indentación de 2 espacios, máximo 100 caracteres por línea,
  Javadoc en clases públicas).
- **Componente:** Maven, `checkstyle.xml`, `maven-checkstyle-plugin`.
- **ISO/IEC 25010:** *Mantenibilidad → Analizabilidad* (código homogéneo, más fácil de revisar
  en Code Review).
- **Ubicación:** `checkstyle.xml` y `pom.xml` (raíz del repositorio)

---

## 4. HU-02 — Publicación de Productos (5 SP)

### 4.1 Historia de Usuario

*Como* Agricultor autenticado,
*quiero* publicar mis cosechas indicando tipo, categoría, cantidad, precio y fecha,
*para* que sean visibles a los compradores.

### 4.2 Escenarios BDD (Given-When-Then)

**Escenario 1 — Publicación exitosa**

```gherkin
Given un agricultor autenticado con token JWT válido
When  publica un producto con tipo, categoria, cantidad, precio y fecha_cosecha válida
Then  el sistema valida la fecha, responde 201 Created y retorna un ID de producto único
```

**Escenario 2 — Fecha de cosecha anterior a hoy**

```gherkin
Given un agricultor autenticado con token JWT válido
When  publica un producto con fecha_cosecha anterior a la fecha actual
Then  el sistema responde 400 Bad Request y no persiste el producto
```

**Escenario 3 — Sin autenticación**

```gherkin
Given un usuario sin token JWT
When  intenta publicar un producto en POST /api/v1/productos
Then  el sistema responde 401 Unauthorized
```

### 4.3 Descomposición Técnica

#### Tarea 2.1 — Entidad `Producto` y migración DDL (3 h) — *Dilan*

- **Componente:** PostgreSQL 15, Flyway, Spring Data JPA (`@ManyToOne`, `@JoinColumn`).
- **ISO/IEC 25010:** *Fiabilidad → Madurez* (la FK y los `CHECK` garantizan integridad
  referencial y de dominio); *Mantenibilidad → Modificabilidad* (esquema versionado).
- **Ubicación:** `.../models/Producto.java` y
  `src/main/resources/db/migration/V2__create_productos.sql`

```sql
-- V2__create_productos.sql
CREATE TABLE productos (
  id              BIGSERIAL     PRIMARY KEY,
  tipo            VARCHAR(100)  NOT NULL,
  categoria       VARCHAR(50)   NOT NULL,
  municipio       VARCHAR(50)   NOT NULL,
  cantidad        NUMERIC(10,2) NOT NULL CHECK (cantidad > 0),
  precio_unitario NUMERIC(12,2) NOT NULL CHECK (precio_unitario >= 0),
  fecha_cosecha   DATE          NOT NULL,
  estado          VARCHAR(20)   NOT NULL DEFAULT 'ACTIVO',
  productor_id    BIGINT        NOT NULL REFERENCES usuarios_productores (id)
);
```

> **Decisión de diseño:** `municipio` se copia del productor (`ubicacion_valle`) al momento de
> publicar, y `estado = 'ACTIVO'` representa las "ofertas activas" que consulta HU-04.

#### Tarea 2.2 — `ProductoRepository` (1 h) — *Dilan*

- **ISO/IEC 25010:** *Adecuación Funcional → Completitud funcional* (cubre las consultas que
  requieren HU-02 y HU-04).
- **Ubicación:** `.../repositories/ProductoRepository.java`

```java
/** Acceso a datos de productos. */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

  List<Producto> findByMunicipioAndEstado(String municipio, String estado);

  List<Producto> findByMunicipioAndCategoriaAndEstado(
      String municipio, String categoria, String estado);
}
```

#### Tarea 2.3 — Filtro JWT y `SecurityConfig` (3 h) — *Kevin*

- **Descripción:** Implementar `JwtAuthFilter` (lee el header `Authorization: Bearer <token>`,
  valida con `JwtService` y carga la cédula como principal) y `SecurityConfig` para que
  `POST /api/v1/productos` exija token, mientras `/api/v1/auth/**` y
  `GET /api/v1/productos` sean públicos. Sin token o con token inválido responde `401`.
- **Componente:** Spring Security (sesión *stateless*).
- **ISO/IEC 25010:** *Seguridad → Autenticidad* y *Responsabilidad* (solo usuarios con token
  válido publican y queda identificado quién lo hizo).
- **Ubicación:** `.../security/JwtAuthFilter.java` y `.../security/SecurityConfig.java`

```java
/** Configuración de seguridad stateless con JWT. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtFilter)
      throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
            .anyRequest().authenticated())
        .exceptionHandling(e -> e.authenticationEntryPoint(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
```

#### Tarea 2.4 — `ProductoService`: validación y publicación (3 h) — *Santiago*

- **ISO/IEC 25010:** *Fiabilidad → Tolerancia a fallos* (entradas inválidas producen error
  controlado, no datos corruptos); *Adecuación Funcional → Corrección funcional*.
- **Ubicación:** `.../services/ProductoService.java`

```java
/** Lógica de negocio de productos. */
@Service
public class ProductoService {

  private final ProductoRepository productoRepository;
  private final ProductorRepository productorRepository;

  public ProductoService(ProductoRepository productoRepository,
      ProductorRepository productorRepository) {
    this.productoRepository = productoRepository;
    this.productorRepository = productorRepository;
  }

  /** Publica una cosecha del productor identificado por su cédula. */
  @Transactional
  public ProductoResponseDTO publicar(ProductoRequestDTO dto, String cedulaProductor) {
    if (dto.fechaCosecha().isBefore(LocalDate.now())) {
      throw new FechaCosechaInvalidaException(
          "La fecha de cosecha no puede ser anterior a hoy");
    }
    Productor productor = productorRepository.findByCedula(cedulaProductor)
        .orElseThrow(() -> new IllegalStateException("Productor no encontrado"));
    Producto producto = new Producto();
    producto.setTipo(dto.tipo());
    producto.setCategoria(dto.categoria());
    producto.setMunicipio(productor.getUbicacionValle());
    producto.setCantidad(dto.cantidad());
    producto.setPrecioUnitario(dto.precioUnitario());
    producto.setFechaCosecha(dto.fechaCosecha());
    producto.setEstado("ACTIVO");
    producto.setProductor(productor);
    return new ProductoResponseDTO(productoRepository.save(producto).getId());
  }
}
```

#### Tarea 2.5 — `ProductoController`: endpoint POST protegido (2 h) — *Kevin*

- **ISO/IEC 25010:** *Seguridad → Autenticidad*; *Compatibilidad → Interoperabilidad*.
- **Ubicación:** `.../controllers/ProductoController.java`

```java
@PostMapping("/productos")
public ResponseEntity<ProductoResponseDTO> publicar(
    @Valid @RequestBody ProductoRequestDTO dto, Authentication auth) {
  ProductoResponseDTO respuesta = productoService.publicar(dto, auth.getName());
  return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
}
```

#### ⚠️ Tarea 2.6 — Traducción BDD → Pruebas JUnit 5 **[OBLIGATORIA]** (3 h) — *Santiago*

- **Descripción:** Automatizar los 3 escenarios de HU-02: publicación exitosa con ID único
  (Service + Controller), fecha pasada → 400 y ausencia de token → 401 (`@WebMvcTest` con
  `@Import(SecurityConfig.class)` y `@MockitoBean JwtService`).
- **ISO/IEC 25010:** *Adecuación Funcional → Corrección funcional*; *Mantenibilidad →
  Capacidad de ser probado*.
- **Ubicación:** `.../services/ProductoServiceTest.java`,
  `.../controllers/ProductoControllerPublicarTest.java`

```java
@Test
@DisplayName("DADO fecha pasada, CUANDO publica, ENTONCES lanza FechaCosechaInvalida")
void dadoFechaPasada_cuandoPublica_entoncesLanzaExcepcion() {
  ProductoRequestDTO dto = new ProductoRequestDTO(
      "Mango", "Frutas", new BigDecimal("50"), new BigDecimal("2500"),
      LocalDate.now().minusDays(1));

  assertThrows(FechaCosechaInvalidaException.class,
      () -> service.publicar(dto, "1234567890"));
  verify(productoRepository, never()).save(any());
}

@Test
@DisplayName("DADO sin token, CUANDO POST /productos, ENTONCES 401")
void dadoSinToken_cuandoPublica_entonces401() throws Exception {
  mockMvc.perform(post("/api/v1/productos")
          .contentType(MediaType.APPLICATION_JSON).content("{}"))
      .andExpect(status().isUnauthorized());
}
```

#### Tarea 2.7 — Auditoría Checkstyle (1 h) — *Kevin*

- Ejecutar `mvn checkstyle:check` sobre las tareas 1.4 y 2.1–2.6 hasta alcanzar 0 errores.
- **ISO/IEC 25010:** *Mantenibilidad → Analizabilidad*.

---

## 5. HU-04 — Filtro de Categorías y Municipios (2 SP)

### 5.1 Historia de Usuario

*Como* Comprador,
*quiero* filtrar las cosechas por municipio (Dagua, Palmira, Buga) y categoría,
*para* encontrar rápidamente productos locales de mi interés.

### 5.2 Escenarios BDD (Given-When-Then)

**Escenario 1 — Búsqueda con resultados**

```gherkin
Given que existen productos ACTIVOS en el municipio "Dagua" y categoría "Frutas"
When  el usuario hace GET /api/v1/productos?municipio=Dagua&categoria=Frutas
Then  el sistema responde 200 OK y un arreglo JSON con las ofertas correspondientes
```

**Escenario 2 — Búsqueda sin resultados**

```gherkin
Given que no existen productos ACTIVOS en "Buga" con categoría "Granos"
When  el usuario hace GET /api/v1/productos?municipio=Buga&categoria=Granos
Then  el sistema responde 200 OK y un arreglo JSON vacío
```

**Escenario 3 — Municipio no válido**

```gherkin
Given que el usuario consulta un municipio fuera de Dagua, Palmira y Buga
When  hace GET /api/v1/productos?municipio=Otro
Then  el sistema responde 400 Bad Request
```

### 5.3 Descomposición Técnica

#### Tarea 4.1 — Índices de base de datos (1 h) — *Carlos*

- **ISO/IEC 25010:** *Eficiencia de Desempeño → Comportamiento temporal* (búsquedas rápidas
  por municipio y categoría).
- **Ubicación:** `src/main/resources/db/migration/V3__index_productos_filtro.sql`

```sql
-- V3__index_productos_filtro.sql
CREATE INDEX idx_productos_municipio_categoria_estado
  ON productos (municipio, categoria, estado);
```

#### Tarea 4.2 — `ProductoService`: lógica de filtrado (2 h) — *Dilan*

- **Descripción:** Validar que el municipio pertenezca a {Dagua, Palmira, Buga}
  (`MunicipioNoValidoException` si no) y filtrar por categoría cuando se envíe.
- **ISO/IEC 25010:** *Adecuación Funcional → Corrección funcional*.
- **Ubicación:** `.../services/ProductoService.java`

```java
private static final Set<String> MUNICIPIOS = Set.of("Dagua", "Palmira", "Buga");

/** Filtra ofertas activas por municipio y, opcionalmente, categoría. */
@Transactional(readOnly = true)
public List<ProductoDTO> filtrarProductos(String municipio, String categoria) {
  if (!MUNICIPIOS.contains(municipio)) {
    throw new MunicipioNoValidoException("Municipio no válido: " + municipio);
  }
  List<Producto> resultado = (categoria == null || categoria.isBlank())
      ? productoRepository.findByMunicipioAndEstado(municipio, "ACTIVO")
      : productoRepository.findByMunicipioAndCategoriaAndEstado(
          municipio, categoria, "ACTIVO");
  return resultado.stream().map(ProductoDTO::desde).toList();
}
```

#### Tarea 4.3 — `ProductoController`: endpoint GET (1 h) — *Dilan*

- **ISO/IEC 25010:** *Compatibilidad → Interoperabilidad*; *Usabilidad → Operabilidad*
  (parámetros de consulta simples).
- **Ubicación:** `.../controllers/ProductoController.java`

```java
@GetMapping("/productos")
public ResponseEntity<List<ProductoDTO>> filtrar(
    @RequestParam String municipio,
    @RequestParam(required = false) String categoria) {
  return ResponseEntity.ok(productoService.filtrarProductos(municipio, categoria));
}
```

#### ⚠️ Tarea 4.4 — Traducción BDD → Pruebas JUnit 5 **[OBLIGATORIA]** (2 h) — *Santiago*

- **Descripción:** Automatizar los 3 escenarios de HU-04 con `@WebMvcTest` y MockMvc.
- **ISO/IEC 25010:** *Adecuación Funcional → Corrección funcional*; *Mantenibilidad →
  Capacidad de ser probado*.
- **Ubicación:** `.../controllers/ProductoControllerFiltroTest.java`

```java
@WebMvcTest(ProductoController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("ProductoController — HU-04: Filtro de Productos")
class ProductoControllerFiltroTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ProductoService productoService;

  @Test
  @DisplayName("Escenario 1: con resultados → 200 y 2 elementos")
  void conResultados() throws Exception {
    given(productoService.filtrarProductos("Dagua", "Frutas")).willReturn(List.of(
        new ProductoDTO(1L, "Mango", "Dagua", "Frutas",
            new BigDecimal("50"), new BigDecimal("2500")),
        new ProductoDTO(2L, "Papaya", "Dagua", "Frutas",
            new BigDecimal("20"), new BigDecimal("1800"))));

    mockMvc.perform(get("/api/v1/productos")
            .param("municipio", "Dagua").param("categoria", "Frutas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].municipio").value("Dagua"));
  }

  @Test
  @DisplayName("Escenario 2: sin resultados → 200 y arreglo vacío")
  void sinResultados() throws Exception {
    given(productoService.filtrarProductos("Buga", "Granos")).willReturn(List.of());

    mockMvc.perform(get("/api/v1/productos")
            .param("municipio", "Buga").param("categoria", "Granos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Escenario 3: municipio no válido → 400")
  void municipioNoValido() throws Exception {
    given(productoService.filtrarProductos("Otro", null))
        .willThrow(new MunicipioNoValidoException("Municipio no válido: Otro"));

    mockMvc.perform(get("/api/v1/productos").param("municipio", "Otro"))
        .andExpect(status().isBadRequest());
  }
}
```

#### Tarea 4.5 — Auditoría Checkstyle (1 h) — *Santiago*

- Ejecutar `mvn checkstyle:check` sobre las tareas 4.1–4.4 hasta alcanzar 0 errores.
- **ISO/IEC 25010:** *Mantenibilidad → Analizabilidad*.

---

## 6. Trazabilidad BDD → Pruebas Automatizadas

| HU | Escenario BDD | Resultado esperado | Clase de prueba | Tarea |
| --- | --- | --- | --- | --- |
| HU-01 | E1 Registro exitoso | 201 + token | `ProductorServiceTest`, `ProductorControllerTest` | 1.7 |
| HU-01 | E2 Cédula duplicada | 409 | `ProductorServiceTest`, `ProductorControllerTest` | 1.7 |
| HU-01 | E3 Datos inválidos | 400 | `ProductorControllerTest` | 1.7 |
| HU-02 | E1 Publicación exitosa | 201 + ID | `ProductoServiceTest`, `ProductoControllerPublicarTest` | 2.6 |
| HU-02 | E2 Fecha pasada | 400 | `ProductoServiceTest` | 2.6 |
| HU-02 | E3 Sin token | 401 | `ProductoControllerPublicarTest` | 2.6 |
| HU-04 | E1 Con resultados | 200 + lista | `ProductoControllerFiltroTest` | 4.4 |
| HU-04 | E2 Sin resultados | 200 + [] | `ProductoControllerFiltroTest` | 4.4 |
| HU-04 | E3 Municipio inválido | 400 | `ProductoControllerFiltroTest` | 4.4 |

---

## 7. Resumen de Características ISO/IEC 25010 aplicadas

| Característica | Subcaracterística | Tareas |
| --- | --- | --- |
| Adecuación Funcional | Corrección, Completitud | 1.2, 1.7, 2.2, 2.4, 2.6, 4.2, 4.4 |
| Eficiencia de Desempeño | Comportamiento temporal | 4.1 |
| Compatibilidad | Interoperabilidad | 1.6, 2.5, 4.3 |
| Usabilidad | Operabilidad | 4.3 |
| Fiabilidad | Madurez, Tolerancia a fallos | 1.1, 1.3, 1.5, 2.1, 2.4 |
| Seguridad | Integridad, Autenticidad, Responsabilidad | 1.3, 1.4, 1.5, 2.3, 2.5 |
| Mantenibilidad | Modularidad, Modificabilidad, Analizabilidad, Capacidad de ser probado | 1.1, 1.6, 1.7, 1.8, 2.1, 2.6, 2.7, 4.5 |

---

## 8. Riesgos y Supuestos del Sprint

| Riesgo / Supuesto | Impacto | Mitigación |
| --- | --- | --- |
| Sin login con credenciales: si el productor pierde su token no puede reingresar | Medio | Alcance aceptado para el Sprint 1 (BDD aprobado sin contraseña); el login se agrega al Product Backlog para el Sprint 2 |
| La configuración de Spring Security + JWT (tareas 1.4 y 2.3) puede tomar más de lo estimado | Alto | Iniciarla en los primeros días del Sprint; trabajar en pareja Kevin + Santiago |
| Dependencias entre tareas de distintos integrantes (p. ej. 2.4 depende de 2.1, 2.2 y 2.3) | Medio | Revisar bloqueos en cada Daily Scrum; PRs pequeños y frecuentes |
| Testcontainers requiere Docker en los equipos del grupo | Medio | Alternativa: H2 en modo PostgreSQL para `ProductorRepositoryIT` |
| El secreto JWT no debe subirse al repositorio | Alto | Leerlo desde variable de entorno (`jwt.secret`) |
| El municipio de un producto se toma de `ubicacion_valle` del productor | Bajo | Documentado como decisión de diseño; revisable en Sprint 2 |
| Supuesto: HU-03 no forma parte de este Sprint | Bajo | Permanece en `BACKLOG.md` |

---

## 9. Referencias

- Definición de Hecho: [`docs/dod.md`](dod.md)
- Product Backlog: [`BACKLOG.md`](../BACKLOG.md)
- Guía de flujo Git/GitHub: ramas `feature/HU-XX-descripcion` → PR → `develop`
