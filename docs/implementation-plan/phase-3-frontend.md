# Phase 3: Frontend (BFF) Implementation

**Goal:** Build the Next.js application acting as the Backend-for-Frontend and User Interface.

## Steps

### STEP-010: Next.js Setup & Keycloak Integration
**Description:**
- Initialize Next.js project (App Router).
- Configure `next-auth` or `oidc-client` for Keycloak authentication.
- Implement protected routes.

**Affected Files:**
- `/frontend/src/app/`
- `/frontend/src/lib/auth.ts`

**Dependencies:**
- STEP-001, STEP-003

**Business Value:**
- Secures user access and manages sessions.

**Acceptance Criteria:**
- User can login via Keycloak.
- Access token is securely stored (server-side session or cookie).
- Redirect to login on unauthorized access.

---

### STEP-011: Project Management UI
**Description:**
- Create pages to list, create, and edit projects.
- Implement API routes (BFF) to proxy requests to Core Service.
- Validate inputs using `Zod`.

**Affected Files:**
- `/frontend/src/app/projects/`
- `/frontend/src/adapters/api/`

**Dependencies:**
- STEP-009, STEP-010

**Business Value:**
- Allows users to manage their documentation projects.

**Acceptance Criteria:**
- User can create a new project.
- List of projects is fetched from Core Service.

---

### STEP-012: Requirements Editor
**Description:**
- Implement a Rich Text Editor (e.g., TipTap or Slate).
- Allow input of functional and non-functional requirements.

**Affected Files:**
- `/frontend/src/components/editor/`

**Dependencies:**
- STEP-011

**Business Value:**
- Provides the input mechanism for the AI generation.

**Acceptance Criteria:**
- Rich text content is captured and formatted.
- Content can be saved to Core Service.

---

### STEP-013: Document Preview & WebSocket Integration
**Description:**
- Implement split-screen view.
- Render Markdown and Mermaid/PlantUML diagrams.
- Connect to Core Service via SSE (Server-Sent Events) or WebSocket for real-time updates.

**Affected Files:**
- `/frontend/src/components/preview/`
- `/frontend/src/hooks/useDocumentStream.ts`

**Dependencies:**
- STEP-009, STEP-012

**Business Value:**
- Real-time feedback loop for the user.

**Acceptance Criteria:**
- Document updates from the server appear instantly.
- Diagrams render correctly.
