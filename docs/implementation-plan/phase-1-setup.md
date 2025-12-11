# Phase 1: Infrastructure & Setup

**Goal:** Establish the foundation for the project, including the Monorepo structure, Docker infrastructure, and Keycloak configuration.

## Steps

### STEP-001: Initial Project Configuration
**Description:**
~~- Create the folder structure defined in `technical-specification.md`.~~
~~- Initialize `package.json` for Frontend.~~
~~- Initialize `pom.xml` for Backend services.~~
~~- Create `.gitignore` and `.env.example`.~~

**Affected Files:**
- `/` (root)
- `/frontend/package.json`
- `/backend/doc-core-service/pom.xml`
- `/backend/ai-worker-service/pom.xml`

**Dependencies:**
- None.

**Business Value:**
- Ensures a consistent environment for the team.
- Standardizes dependency management.

**Acceptance Criteria:**
~~- Directory structure matches the spec.~~
~~- Build tools (npm, maven) run without errors in their respective folders.~~

---

### STEP-002: Docker Infrastructure (Data & Messaging)
**Description:**
~~- Create `docker/docker-compose-infra.yml`.~~
~~- Configure `MongoDB`, `Redis`, and `RabbitMQ`.~~
~~- Define networks and volumes.~~

**Affected Files:**
- `/docker/docker-compose-infra.yml`

**Dependencies:**
- STEP-001

**Business Value:**
- Provides local persistence and messaging capabilities required for development.

**Acceptance Criteria:**
~~- `docker compose -f docker/docker-compose-infra.yml up` starts MongoDB, Redis, and RabbitMQ.~~
~~- Services are accessible on ports 27017, 6380 (Redis updated from 6379), 5672/15672.~~

---

### STEP-003: Keycloak Setup
**Description:**
~~- Add `Postgres` (for Keycloak) and `Keycloak` to `docker-compose-infra.yml`.~~
~~- Configure realm `DocGenRealm` and client `docgen-web`.~~
~~- Export realm configuration to `docker/config/realm-export.json` for reproducibility.~~

**Affected Files:**
- `/docker/docker-compose-infra.yml`
- `/docker/config/realm-export.json`

**Dependencies:**
- STEP-002

**Business Value:**
- Enables secure Identity and Access Management (IAM) from day one.

**Acceptance Criteria:**
~~- Keycloak starts on port 8080.~~
~~- Admin console is accessible.~~
~~- `docgen-web` client is configured with valid redirect URIs.~~

---

### STEP-004: Observability Stack
**Description:**
~~- Add `Prometheus`, `Grafana`, `Loki`, and `Promtail` to `docker-compose-infra.yml`.~~
~~- Create configuration files in `/docker/config/`.~~

**Affected Files:**
- `/docker/docker-compose-infra.yml`
- `/docker/config/prometheus.yml`
- `/docker/config/loki-local-config.yml`
- `/docker/config/promtail-config.yml`

**Dependencies:**
- STEP-002

**Business Value:**
- Provides visibility into system health and logs during development.

**Acceptance Criteria:**
~~- Grafana accessible on port 3001.~~
~~- Prometheus scraping targets (initially just itself).~~
~~- Loki receiving logs via Promtail (Loki port updated to 3101).~~
