# Phase 4: AI Worker & Processing

**Goal:** Implement the asynchronous worker service to process generation requests using LLMs.

## Steps

### STEP-014: Worker Service Setup
**Description:**
- Setup Spring Boot project for `ai-worker-service`.
- Configure RabbitMQ listener.
- Configure Spring AI (or LangChain4j) for LLM integration.

**Affected Files:**
- `/backend/ai-worker-service/`

**Dependencies:**
- STEP-001, STEP-002

**Business Value:**
- dedicated service for heavy AI processing.

**Acceptance Criteria:**
- Service connects to RabbitMQ and consumes messages.
- Service connects to OpenAI API (or compatible provider).

---

### STEP-015: Prompt Engineering & Generation Logic
**Description:**
- Implement the "Golden Prompts" strategy.
- Create logic to generate text and diagrams based on requirements.
- Parse LLM output (JSON/Markdown) and validate structure.

**Affected Files:**
- `/backend/ai-worker-service/src/main/java/com/docgen/worker/domain/prompts/`

**Dependencies:**
- STEP-014

**Business Value:**
- The core value proposition: converting requirements to docs.

**Acceptance Criteria:**
- Given a requirement, the LLM returns a structured document.
- Output is validated before further processing.

---

### STEP-016: Result Handling & Feedback Loop
**Description:**
- Post the generated result back to Core Service (via API or Queue).
- Handle failures and retries.

**Affected Files:**
- `/backend/ai-worker-service/src/main/java/com/docgen/worker/application/`

**Dependencies:**
- STEP-009, STEP-015

**Business Value:**
- Closes the loop, making the document available to the user.

**Acceptance Criteria:**
- Generated document is persisted in Core Service.
- Frontend receives the update notification.
