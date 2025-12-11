# DocGen AI — Implementation Plan

This directory contains the detailed step-by-step implementation plan for **DocGen AI**, based on the [Technical Specification](../../technical-specification.md).

## Structure
The plan is divided into 5 logical phases, each with its own file:

- **[Phase 1: Setup & Infrastructure](./phase-1-setup.md)**  
  Environment configuration, Docker containers (MongoDB, Keycloak, RabbitMQ, Observability), and repository structure.
- **[Phase 2: Core Service](./phase-2-core.md)**  
  Backend logic, Domain entities, Ports & Adapters, and Persistence layer.
- **[Phase 3: Frontend (BFF & UI)](./phase-3-frontend.md)**  
  Next.js application, Keycloak integration, Project management UI, and Requirements editor.
- **[Phase 4: AI Worker & Processing](./phase-4-worker.md)**  
  RabbitMQ consumers, LLM integration, Prompt engineering, and Result handling.
- **[Phase 5: Observability & Testing](./phase-5-ops.md)**  
  Metrics, Logs, E2E Tests, and CI/CD pipelines.

## How to Use
Each phase contains a list of **STEPs**. Each STEP follows this format:

```markdown
### STEP-XXX: Title
**Description:** What needs to be done.
**Affected Files:** List of files/modules.
**Dependencies:** Prerequisites for this step.
**Business Value:** Why this is important.
**Acceptance Criteria:** How to verify completion.
**Status:** [Pending | In Progress | Done]
```

## Progress Tracking
- [x] **Phase 1** — Infrastructure & Setup (All Steps Done)
- [ ] **Phase 2** — Core Service
- [ ] **Phase 3** — Frontend (BFF)
- [ ] **Phase 4** — AI Worker
- [ ] **Phase 5** — Operations & Quality

## Always do

- Always mark taks after DONE
