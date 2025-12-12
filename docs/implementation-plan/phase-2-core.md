# Phase 2: Core Service Implementation

**Goal:** Implement the Hexagonal Architecture backend to handle business logic, persistence, and event publishing.

## Steps

### CORE-001: Core Service Scaffolding (Hexagonal)
**Description:**
- Setup Spring Boot project for `doc-core-service`.
- Create package structure: `domain`, `application`, `infrastructure`.
- Configure `pom.xml` with dependencies (Spring Data Mongo, AMQP, Web).

**Affected Files:**
- `/backend/doc-core-service/`

**Dependencies:**
- SETUP-001

**Business Value:**
- Establishes the core backend service structure enforcing separation of concerns.

**Acceptance Criteria:**
- Application compiles and starts.
- Package structure strictly separates Domain from Infrastructure.

---

### CORE-002: Domain Modeling & Ports
**Description:**
- Define entities: `Project`, `Document`, `Requirement`.
- Define Repository interfaces (Output Ports).
- Define Service interfaces (Input Ports/Use Cases).

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/domain/`

**Dependencies:**
- CORE-001

**Business Value:**
- Captures business rules without technical debt or framework coupling.

**Acceptance Criteria:**
- POJOs created for entities.
- Interfaces defined for Repositories and Use Cases.
- **No Spring annotations** in the Domain package.

---

### CORE-003: MongoDB Adapter Implementation
**Description:**
- Implement Repository interfaces using Spring Data MongoDB.
- Configure MongoDB connection.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/output/persistence/`

**Dependencies:**
- SETUP-002, CORE-002

**Business Value:**
- Enables data persistence.

**Acceptance Criteria:**
- Integration tests verify CRUD operations on MongoDB.
- Data is correctly stored in the `docgen` database.

---

### CORE-004: RabbitMQ Publisher Adapter
**Description:**
- Implement the `EventPublisher` port.
- Configure Spring AMQP to publish messages to the `doc-generation-exchange`.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/output/messaging/`

**Dependencies:**
- SETUP-002, CORE-002

**Business Value:**
- Decouples document generation requests from processing.

**Acceptance Criteria:**
- Publishing a message results in it appearing in the RabbitMQ queue.

---

### CORE-005: Application Services & REST API
**Description:**
- Implement Use Cases (`CreateProject`, `RequestGeneration`).
- Create REST Controllers (`ProjectController`, `DocumentController`).
- Secure endpoints with Spring Security + OAuth2 Resource Server.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/application/`
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/input/web/`

**Dependencies:**
- SETUP-003, CORE-003, CORE-004

**Business Value:**
- Exposes business logic to the outside world (BFF).

**Acceptance Criteria:**
- `POST /projects` creates a project.
- `POST /documents` triggers a message to RabbitMQ.
- Endpoints require a valid JWT from Keycloak.

---

### CORE-006: Hexagonal Refactoring & API Hardening
**Description:**
- **Hexagonal Purity:** Refactor Controllers to interact *only* with Use Case interfaces (Input Ports), removing any direct dependencies on Repositories.
- **DTO Implementation:** Introduce `record` based DTOs (`CreateProjectRequest`, `ProjectResponse`) to decouple API contracts from Domain Entities.
- **Mapper Strategy:** Implement `MapStruct` interfaces for type-safe, efficient conversion between DTOs and Domain Models.
- **Boilerplate Reduction:** Apply `Lombok` (@Data, @Builder, @RequiredArgsConstructor) across the codebase to reduce verbosity.
- **Documentation:** Integrate `Swagger/OpenAPI` with dedicated interface files (`*Doc.java`) to keep Controllers clean.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/input/web/dto/`
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/input/web/mapper/`
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/input/web/doc/`

**Dependencies:**
- CORE-005

**Business Value:**
- Ensures long-term maintainability and strict adherence to architectural patterns.
- Provides clear, interactive API documentation for frontend developers.
- Improves code readability and testability.

**Acceptance Criteria:**
- Controllers have 0 direct dependencies on Repositories.
- Swagger UI (`/swagger-ui.html`) is accessible and fully documented.
- All DTOs are immutable `records`.
- 100% Test Coverage for Mappers and DTOs.

