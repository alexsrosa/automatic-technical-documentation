# Phase 4: AI Worker & Processing

**Goal:** Implement the asynchronous worker service to process generation requests using LLMs.

## Steps

### WORKER-001: Worker Service Setup
**Description:**
- Setup Spring Boot project for `ai-worker-service`.
- Configure RabbitMQ listener.
- Configure Spring AI (or LangChain4j) for LLM integration.
- **Architectural Standards:**
    - Apply **Hexagonal Architecture** (Domain, Application, Infrastructure).
    - Use **Lombok** for boilerplate reduction.
    - Implement **DTOs (Records)** for internal/external data structures.

**Affected Files:**
- `/backend/ai-worker-service/`

**Dependencies:**
- SETUP-001, SETUP-002

**Business Value:**
- dedicated service for heavy AI processing.

**Acceptance Criteria:**
- Service connects to RabbitMQ and consumes messages.
- Service connects to OpenAI API (or compatible provider).
- Architecture mirrors `doc-core-service` standards.

---

### WORKER-002: Prompt Engineering & Generation Logic
**Description:**
- Implement the "Golden Prompts" strategy.
- Create logic to generate text and diagrams based on requirements.
- Parse LLM output (JSON/Markdown) and validate structure.
- **Best Practices:**
    - Use strict **Java Records** for parsing LLM structured outputs.
    - Implement validation layers using **Bean Validation** on parsed objects.

**Affected Files:**
- `/backend/ai-worker-service/src/main/java/com/docgen/worker/domain/prompts/`

**Dependencies:**
- WORKER-001

**Business Value:**
- The core value proposition: converting requirements to docs.

**Acceptance Criteria:**
- Given a requirement, the LLM returns a structured document.
- Output is validated before further processing.

---

### WORKER-003: Result Handling & Feedback Loop
**Description:**
- Post the generated result back to Core Service (via API or Queue).
- Handle failures and retries (Dead Letter Queues).
- **Integration:**
    - Use **MapStruct** if mapping between internal models and Core DTOs is needed.
    - Ensure robust error handling for "Human-in-the-loop" scenarios.

**Affected Files:**
- `/backend/ai-worker-service/src/main/java/com/docgen/worker/application/`

**Dependencies:**
- CORE-005, WORKER-002

**Business Value:**
- Closes the loop, making the document available to the user.

**Acceptance Criteria:**
- Generated document is persisted in Core Service.
- Frontend receives the update notification.
