# Architecture Diagram

These diagrams describe the code and implemented Render staging setup. Proposed academic entities and unselected cloud alternatives are not represented as deployed components.

## Application Architecture

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart TD
    subgraph Client["API clients"]
        ApiClient["HTTP client"]
    end
    subgraph App["Spring Boot application"]
        Web["Spring MVC REST controllers"]
        Security["Spring Security JWT filter"]
        Services["Business services"]
        Events["Application events and listeners"]
        DataAccess["Spring Data JPA and Flyway"]
    end
    subgraph Storage["Databases"]
        PostgreSQL[("PostgreSQL")]
        H2[("H2 test database")]
    end
    subgraph Delivery["Build and deployment"]
        Actions["GitHub Actions CI"]
        Build["Maven build and tests"]
        Render["Render staging services"]
    end

    ApiClient -->|"HTTP requests with Bearer token"| Security
    Security -->|"authenticated request"| Web
    Web -->|"controller delegation"| Services
    Services -->|"queries and updates"| DataAccess
    DataAccess -->|"JDBC"| PostgreSQL
    DataAccess -->|"automated tests"| H2
    Services -->|"publishes domain events"| Events
    Events -->|"persists contact and delivery notifications"| DataAccess
    Actions -->|"runs Java 17 CI"| Build
    Actions -->|"deployment webhook after CI"| Render
    Render -->|"hosts Spring Boot service"| Web
~~~

### Technology Stack Summary

| Layer | Technology | Version | Purpose |
|---|---|---|---|
| Runtime | Java | 17 | Application runtime |
| Application | Spring Boot | 4.1.1 | REST API and dependency injection |
| Web and validation | Spring MVC and Jakarta Validation | Managed by Spring Boot | HTTP endpoints and request validation |
| Security | Spring Security, JJWT and BCrypt | JJWT 0.13.0 | Stateless JWT authentication and password hashing |
| Persistence | Spring Data JPA and Hibernate | Managed by Spring Boot | Relational persistence |
| Database | PostgreSQL | Local validation reported 17.11 | Application persistence through JDBC |
| Schema migration | Flyway | Managed by Spring Boot | Versioned database migrations |
| Test database | H2 | 2.4.240 in the integration test run | Automated migration and API tests |
| CI | GitHub Actions | Workflow uses actions/checkout v4 and setup-java v4 | Build, Checkstyle, tests and JaCoCo |
| Cloud deployment | Render | Staging | Hosts the Spring Boot service and PostgreSQL staging database |
| Document generation | Apache PDFBox | 3.0.5 | Creates downloadable receipts |

### Data Storage & External Services

The application uses PostgreSQL through environment-based connection settings; the staging profile reads its database and JWT settings from Render variables. Automated integration tests use H2. The payment integration is a simulated adapter inside the application; no external payment service is configured. GitHub Actions runs CI and triggers the Render deployment webhook only after quality gates pass.

### Key Architectural Decisions

- REST controllers delegate business behavior to services, which use Spring Data repositories.
- JWT authentication is stateless; farmer passwords are stored as BCrypt hashes.
- Flyway manages schema changes. Spring application events decouple product publication, contact requests, and delivery notifications persisted after transaction commit.

## Component Relationships

<!-- mermaid-checked: no \n, no em-dash/en-dash, no {} in labels, subgraphs are id["label"], arrows are -->|"label"|, all subgraphs closed by end, ids unique -->
~~~mermaid
flowchart LR
    subgraph cPresentation["Presentation"]
        cAuthController["AgricultorController"]
        cProductController["ProductoController"]
        cContactController["ContactoController"]
        cPaymentController["PagoController"]
        cLogisticsController["OrdenLogisticaController"]
        cJourneyController["ViajeController"]
        cNotificationController["NotificacionController"]
    end
    subgraph cBusiness["Business logic"]
        cFarmerService["AgricultorService"]
        cJwtService["JwtService"]
        cProductService["ProductoService"]
        cContactService["ContactoService"]
        cPaymentService["PagoService"]
        cPaymentAdapter["PasarelaPago simulated adapter"]
        cWalletService["BilleteraService"]
        cLogisticsService["OrdenLogisticaService"]
        cCheckpointService["CheckpointService"]
        cRatingService["CalificacionTransporteService"]
        cNotificationService["NotificacionService"]
    end
    subgraph cPersistence["Persistence"]
        cFarmerRepo["AgricultorRepository"]
        cProductRepo["ProductoRepository"]
        cOrderRepos["Pedido Pago and OrdenCompra repositories"]
        cLogisticsRepo["OrdenLogisticaRepository"]
        cCheckpointRepo["CheckpointViajeRepository"]
        cRatingRepo["CalificacionTransporteRepository"]
        cWalletRepo["BilleteraRepository"]
        cNotificationRepo["NotificacionRepository"]
        cPostgreSQL[("PostgreSQL")]
    end
    subgraph cCrossCutting["Cross-cutting"]
        cJwtFilter["JwtAuthenticationFilter"]
        cPasswordEncoder["BCrypt PasswordEncoder"]
        cProductListener["ProductoPublicadoListener"]
        cContactListener["SolicitudContactoCreadaListener"]
        cDeliveryListener["LlegadaDestinoListener"]
        cPublisher["ApplicationEventPublisher"]
    end

    cAuthController -->|"register and login"| cFarmerService
    cFarmerService -->|"queries and saves"| cFarmerRepo
    cFarmerService -->|"issues access token"| cJwtService
    cFarmerService -->|"hashes password"| cPasswordEncoder
    cProductController -->|"delegates"| cProductService
    cProductService -->|"loads owner and saves product"| cFarmerRepo
    cProductService -->|"persists product"| cProductRepo
    cProductService -->|"publishes product event"| cPublisher
    cPublisher -->|"product event"| cProductListener
    cContactController -->|"delegates"| cContactService
    cContactService -->|"checks product availability"| cProductRepo
    cContactService -->|"persists request and order"| cOrderRepos
    cContactService -->|"publishes contact event"| cPublisher
    cPublisher -->|"contact event"| cContactListener
    cContactListener -->|"records notification"| cNotificationService
    cNotificationService -->|"persists notification"| cNotificationRepo
    cPaymentController -->|"delegates"| cPaymentService
    cPaymentService -->|"updates payment and order"| cOrderRepos
    cPaymentService -->|"locks and updates stock"| cProductRepo
    cPaymentService -->|"simulates gateway response"| cPaymentAdapter
    cPaymentService -->|"credits approved payment"| cWalletService
    cWalletService -->|"persists balance"| cWalletRepo
    cPaymentService -->|"creates logistics order"| cLogisticsService
    cLogisticsController -->|"delegates"| cLogisticsService
    cLogisticsService -->|"queries and updates"| cLogisticsRepo
    cJourneyController -->|"arrival and delivery endpoints"| cCheckpointService
    cCheckpointService -->|"updates journey state"| cLogisticsRepo
    cCheckpointService -->|"records checkpoints"| cCheckpointRepo
    cCheckpointService -->|"publishes arrival event"| cPublisher
    cPublisher -->|"arrival after commit"| cDeliveryListener
    cDeliveryListener -->|"persists buyer notification"| cNotificationService
    cNotificationController -->|"lists buyer notifications"| cNotificationService
    cRatingService -->|"requires delivered journey"| cLogisticsRepo
    cRatingService -->|"persists transport rating"| cRatingRepo
    cNotificationService -->|"persists notification"| cNotificationRepo
    cJwtFilter -.->|"authenticates protected requests"| cPresentation
    cJwtFilter -->|"validates tokens"| cJwtService
    cFarmerRepo -->|"JDBC"| cPostgreSQL
    cProductRepo -->|"JDBC"| cPostgreSQL
    cOrderRepos -->|"JDBC"| cPostgreSQL
    cLogisticsRepo -->|"JDBC"| cPostgreSQL
    cCheckpointRepo -->|"JDBC"| cPostgreSQL
    cRatingRepo -->|"JDBC"| cPostgreSQL
    cNotificationRepo -->|"JDBC"| cPostgreSQL
    cWalletRepo -->|"JDBC"| cPostgreSQL
~~~

### Component Inventory

| Component | Layer | Type | Responsibility |
|---|---|---|---|
| AgricultorController | Presentation | REST controller | Farmer registration and login |
| ProductoController | Presentation | REST controller | Product publication and catalog queries |
| ContactoController | Presentation | REST controller | Contact request and pending order creation |
| PagoController | Presentation | REST controller | Payment processing |
| OrdenLogisticaController | Presentation | REST controller | Logistics order and route operations |
| ViajeController | Presentation | REST controller | Arrival and delivery confirmation for assigned transporters |
| NotificacionController | Presentation | REST controller | Authenticated farmer and buyer notification inboxes |
| AgricultorService | Business logic | Service | Farmer registration, credential verification and JWT issuance |
| ProductoService | Business logic | Transactional service | Product ownership, harvest-date validation and publication |
| ContactoService | Business logic | Transactional service | Contact message, pending order and contact event |
| PagoService | Business logic | Transactional service | Payment, stock update, purchase order, wallet and logistics coordination |
| OrdenLogisticaService | Business logic | Transactional service | Logistics order, preparation and route assignment |
| CheckpointService | Business logic | Transactional service | Records arrival and delivery checkpoints; releases the vehicle on delivery |
| CalificacionTransporteService | Business logic | Transactional service | Allows a buyer to rate transport after delivery |
| NotificacionService | Business logic | Service | Persists and lists farmer and buyer notifications |
| JwtAuthenticationFilter | Cross-cutting | Security filter | Validates Bearer JWTs on protected endpoints |
| ProductoPublicadoListener | Cross-cutting | Event listener | Logs product publication events |
| SolicitudContactoCreadaListener | Cross-cutting | Transactional event listener | Persists a notification for a new contact request |
| LlegadaDestinoListener | Cross-cutting | Transactional event listener | Persists a buyer notification after the arrival transaction commits |
| Spring Data repositories | Persistence | Repository interfaces | Database access for JPA entities |
