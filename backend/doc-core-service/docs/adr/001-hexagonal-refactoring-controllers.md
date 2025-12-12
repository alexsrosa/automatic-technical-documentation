# ADR 001: Refactoring Controllers to Hexagonal Architecture

## Status
Accepted

## Date
2025-12-12

## Context
The `doc-core-service` project was initiated following Hexagonal Architecture. However, during initial development, some pattern violations were introduced in the Controllers (`DocumentController` and `ProjectController`). Specifically:
1. Controllers were directly accessing Output Ports (Repositories).
2. Business rules (ID generation, status definition, "not found" logic) were residing in the Input Adapter layer (Web).

This violates the Single Responsibility Principle (SRP) and the decoupling proposed by Hexagonal Architecture, making it difficult to test business rules in isolation and making the web layer aware of persistence details.

## Decision
Refactor the Input Adapter layer (Controllers) to remove any direct dependency on Output Ports (Repositories) and move all business logic to the Application layer (UseCases).

### Changes Implemented:

1.  **Creation of Input Ports:**
    *   `CreateDocumentUseCase`: Interface for document creation.
    *   `GetDocumentUseCase`: Interface for document retrieval (by ID and by Project).
    *   `GetProjectUseCase`: Interface for project retrieval (by ID, by Owner, and All).

2.  **Implementation of Application Services:**
    *   `CreateDocumentService`: Implements `CreateDocumentUseCase`. Contains UUID generation and initial status logic.
    *   `GetDocumentService`: Implements `GetDocumentUseCase`. Orchestrates repository search.
    *   `GetProjectService`: Implements `GetProjectUseCase`. Orchestrates repository search.

3.  **Controller Refactoring:**
    *   `DocumentController`: Now exclusively depends on `CreateDocumentUseCase`, `GetDocumentUseCase`, and `RequestGenerationUseCase`.
    *   `ProjectController`: Now exclusively depends on `CreateProjectUseCase` and `GetProjectUseCase`.
    *   Removal of `DocumentRepositoryPort` and `ProjectRepositoryPort` injection in controllers.

4.  **DTOs and Mappers:**
    *   Introduction of DTOs (Records) for API requests and responses (`CreateProjectRequest`, `ProjectResponse`, etc.) to decouple the API contract from the Domain Model.
    *   Implementation of Mappers (`WebProjectMapper`, `WebDocumentMapper`) using MapStruct to handle conversion between DTOs and Domain objects.

5.  **Tests:**
    *   Update of controller unit tests to mock UseCases instead of Repositories.
    *   Creation of unit tests for the new Application Services.
    *   Verification of Mapper logic.

## Consequences

### Positive
*   **Hexagonal Architecture Adherence**: The input layer (Web) is now completely unaware of the output layer (Persistence).
*   **Decoupling**: API contracts (DTOs) can evolve independently of the Domain Model.
*   **Business Rule Isolation**: Rules like ID generation and default status are encapsulated in the Application Core (Services), not the Web layer.
*   **Testability**: Controllers test only HTTP orchestration. Services test business rules. Mappers are tested independently.
*   **Maintainability**: Changes in persistence or business rules do not affect controllers, as long as contracts (UseCases) are maintained.

### Negative
*   **Increased Complexity**: Introduction of more classes (Interfaces and Implementations) for simple CRUD operations (boilerplate).
*   **Overhead**: Small increase in the number of classes to maintain.

## Compliance
This change complies with SOLID principles (especially SRP and DIP) and the Clean Architecture/Hexagonal Architecture guidelines adopted in the project.
