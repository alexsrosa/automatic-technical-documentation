# DocGen AI

DocGen AI is a SaaS platform that orchestrates Large Language Models (LLMs) to transform requirements into structured, multilingual technical documentation with editable diagrams. The project strictly adheres to Hexagonal Architecture (Ports & Adapters) with a Backend-for-Frontend (BFF) built in Next.js acting as a security and orchestration layer.

## Overview

The primary goal of this platform is to generate versioned technical documents from functional and non-functional requirements. It features AI-powered generation for both text and diagrams, supports human-in-the-loop review and refinement, provides real-time previews, and allows exporting to Markdown, PDF, and HTML.

## Technology Stack

The project leverages a robust set of technologies:

*   **Frontend (BFF):** Next.js 14 (App Router), TypeScript, and Tailwind CSS.
*   **Backend (Core):** Java 21, Spring Boot 3.2, and Spring Data MongoDB.
*   **AI Worker:** Java 21 using Spring AI or LangChain4j.
*   **Infrastructure:** Docker, MongoDB, RabbitMQ, Redis, and Keycloak.
*   **Architecture:** Hexagonal Architecture (Ports & Adapters).

## Repository Structure

This monorepo houses all services and infrastructure configurations:

*   `frontend`: The Next.js application handling the UI and BFF layer.
*   `backend/doc-core-service`: The core business logic and persistence layer.
*   `backend/ai-worker-service`: The asynchronous worker responsible for LLM interactions.
*   `docker`: Infrastructure orchestration using Docker Compose.
*   `docs`: Project documentation and detailed implementation plans.

## Documentation

We maintain detailed documentation and step-by-step implementation plans. The [Technical Specification](./technical-specification.md) serves as the single source of truth for architecture, requirements, and scope.

Implementation phases are broken down as follows:

1.  [Phase 1: Infrastructure & Setup](./docs/implementation-plan/phase-1-setup.md)
2.  [Phase 2: Core Service Implementation](./docs/implementation-plan/phase-2-core.md)
3.  [Phase 3: Frontend (BFF) Implementation](./docs/implementation-plan/phase-3-frontend.md)
4.  [Phase 4: AI Worker & Processing](./docs/implementation-plan/phase-4-worker.md)
5.  [Phase 5: Observability & Quality Assurance](./docs/implementation-plan/phase-5-ops.md)

## Getting Started

### Prerequisites

Ensure you have the following installed:
*   Node.js 20 or higher
*   Java 21 JDK
*   Docker and Docker Compose

### Running Infrastructure

Navigate to the docker directory and start the infrastructure services:

```bash
cd docker
docker compose -f docker-compose-infra.yml up -d
```

### Running Services

For instructions on running individual services, please refer to the specific documentation within each service directory or follow the Phase 1 Setup Guide linked above.

## License

Private / Proprietary
