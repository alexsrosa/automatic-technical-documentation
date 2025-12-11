# Phase 2: Core Service Implementation

**Goal:** Implement the Hexagonal Architecture backend to handle business logic, persistence, and event publishing.

## Steps

### STEP-005: Core Service Scaffolding (Hexagonal)
**Description:**
- Setup Spring Boot project for `doc-core-service`.
- Create package structure: `domain`, `application`, `infrastructure`.
- Configure `pom.xml` with dependencies (Spring Data Mongo, AMQP, Web).

**Affected Files:**
- `/backend/doc-core-service/`

**Dependencies:**
- STEP-001

**Business Value:**
- Establishes the core backend service structure enforcing separation of concerns.

**Acceptance Criteria:**
- Application compiles and starts.
- Package structure strictly separates Domain from Infrastructure.

---

### STEP-006: Domain Modeling & Ports
**Description:**
- Define entities: `Project`, `Document`, `Requirement`.
- Define Repository interfaces (Output Ports).
- Define Service interfaces (Input Ports/Use Cases).

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/domain/`

**Dependencies:**
- STEP-005

**Business Value:**
- Captures business rules without technical debt or framework coupling.

**Acceptance Criteria:**
- POJOs created for entities.
- Interfaces defined for Repositories and Use Cases.
- **No Spring annotations** in the Domain package.

---

### STEP-007: MongoDB Adapter Implementation
**Description:**
- Implement Repository interfaces using Spring Data MongoDB.
- Configure MongoDB connection.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/output/persistence/`

**Dependencies:**
- STEP-002, STEP-006

**Business Value:**
- Enables data persistence.

**Acceptance Criteria:**
- Integration tests verify CRUD operations on MongoDB.
- Data is correctly stored in the `docgen` database.

---

### STEP-008: RabbitMQ Publisher Adapter
**Description:**
- Implement the `EventPublisher` port.
- Configure Spring AMQP to publish messages to the `doc-generation-exchange`.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/output/messaging/`

**Dependencies:**
- STEP-002, STEP-006

**Business Value:**
- Decouples document generation requests from processing.

**Acceptance Criteria:**
- Publishing a message results in it appearing in the RabbitMQ queue.

---

### STEP-009: Application Services & REST API
**Description:**
- Implement Use Cases (`CreateProject`, `RequestGeneration`).
- Create REST Controllers (`ProjectController`, `DocumentController`).
- Secure endpoints with Spring Security + OAuth2 Resource Server.

**Affected Files:**
- `/backend/doc-core-service/src/main/java/com/docgen/core/application/`
- `/backend/doc-core-service/src/main/java/com/docgen/core/infrastructure/adapters/input/web/`

**Dependencies:**
- STEP-003, STEP-007, STEP-008

**Business Value:**
- Exposes business logic to the outside world (BFF).

**Acceptance Criteria:**
- `POST /projects` creates a project.
- `POST /documents` triggers a message to RabbitMQ.
- Endpoints require a valid JWT from Keycloak.
