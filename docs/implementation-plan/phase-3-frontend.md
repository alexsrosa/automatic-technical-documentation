# Phase 3: Frontend (BFF) Implementation

**Goal:** Build the Next.js application acting as the Backend-for-Frontend and User Interface.

## Steps

### FRONT-001: Next.js Setup & Keycloak Integration
**Description:**
- Initialize Next.js project (App Router).
- **Architecture Setup:** Create folder structure enforcing Hexagonal Architecture (`src/domain`, `src/adapters`, `src/app`) as per `technical-specification.md`.
- Configure `next-auth` or `oidc-client` for Keycloak authentication.
- Implement protected routes.

**Affected Files:**
- `/frontend/src/app/`
- `/frontend/src/domain/`
- `/frontend/src/adapters/`
- `/frontend/src/lib/auth.ts`

**Dependencies:**
- SETUP-001, SETUP-003

**Business Value:**
- Secures user access and manages sessions.
- Establishes strict architectural boundaries from the start.

**Acceptance Criteria:**
- User can login via Keycloak.
- Access token is securely stored (server-side session or cookie).
- Redirect to login on unauthorized access.
- Domain logic is isolated from Next.js framework code.

---

### FRONT-002: Project Management UI
**Description:**
- Create pages to list, create, and edit projects.
- Implement API routes (BFF) to proxy requests to Core Service.
- Validate inputs using `Zod` in the BFF layer.

**Affected Files:**
- `/frontend/src/app/projects/`
- `/frontend/src/adapters/api/`

**Dependencies:**
- CORE-005, FRONT-001

**Business Value:**
- Allows users to manage their documentation projects.

**Acceptance Criteria:**
- User can create a new project.
- List of projects is fetched from Core Service.
- Inputs are validated before reaching the Core Service.

---

### FRONT-003: Requirements Editor
**Description:**
- Implement a Rich Text Editor (e.g., TipTap or Slate).
- Allow input of functional and non-functional requirements.

**Affected Files:**
- `/frontend/src/components/editor/`

**Dependencies:**
- FRONT-002

**Business Value:**
- Provides the input mechanism for the AI generation.

**Acceptance Criteria:**
- Rich text content is captured and formatted.
- Content can be saved to Core Service.

---

### FRONT-004: Document Preview & WebSocket Integration
**Description:**
- Implement split-screen view.
- Render Markdown and Mermaid/PlantUML diagrams.
- Connect to Core Service via SSE (Server-Sent Events) or WebSocket for real-time updates.

**Affected Files:**
- `/frontend/src/components/preview/`
- `/frontend/src/hooks/useDocumentStream.ts`

**Dependencies:**
- CORE-005, FRONT-003

**Business Value:**
- Real-time feedback loop for the user.

**Acceptance Criteria:**
- Document updates from the server appear instantly.
- Diagrams render correctly.

---

### FRONT-005: Refinement & Export Features
**Description:**
- **Refinement:** Implement UI for section-level comments to trigger partial regeneration.
- **Export:** Implement actions to export documents as Markdown, PDF, and HTML.

**Affected Files:**
- `/frontend/src/components/preview/`
- `/frontend/src/app/documents/[id]/`

**Dependencies:**
- FRONT-004

**Business Value:**
- Allows users to polish and distribute the generated documentation.

**Acceptance Criteria:**
- User can comment on a specific section and trigger regeneration.
- Export buttons download the document in the selected format.
