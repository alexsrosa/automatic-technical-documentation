# Phase 3: Frontend (BFF) Implementation

**Goal:** Build the Next.js application acting as the Backend-for-Frontend and User Interface.

## Steps

### FRONT-000: Monorepo Restructuring & BFF Separation (DONE)
**Description:**
- Reorganize `frontend` into a monorepo structure with `client` (Next.js) and `server` (Node.js BFF).
- Implement explicit BFF layer with Express to handle aggregation, transformation, and auth validation.
- Configure `concurrently` for unified development.

**Affected Files:**
- `/frontend/client/`
- `/frontend/server/`
- `/frontend/package.json`

**Business Value:**
- Clear separation of concerns.
- Independent deployment capability.

---

### FRONT-001: Next.js Setup & Keycloak Integration (DONE)
**Description:**
- Initialize Next.js project (App Router) in `frontend/client`.
- **Architecture Setup:** Create folder structure enforcing Hexagonal Architecture.
- Configure `next-auth` for Keycloak authentication.
- Implement protected routes.

**Affected Files:**
- `/frontend/client/src/app/`
- `/frontend/client/src/lib/auth.ts`

**Dependencies:**
- SETUP-001, SETUP-003

**Business Value:**
- Secures user access and manages sessions.

**Acceptance Criteria:**
- User can login via Keycloak.
- Access token is securely stored.
- Domain logic is isolated.

---

### FRONT-002: Project Management UI (DONE)
**Description:**
- Create pages to list, create, and edit projects in `client`.
- Implement API routes in `server` (BFF) using Controller/Gateway pattern.
- Validate inputs using `Zod` in the BFF layer.

**Affected Files:**
- `/frontend/client/src/app/projects/`
- `/frontend/server/src/controllers/`
- `/frontend/server/src/gateways/`

**Dependencies:**
- CORE-005, FRONT-001

**Business Value:**
- Allows users to manage their documentation projects.

**Acceptance Criteria:**
- User can create a new project.
- List of projects is fetched from Core Service.
- Inputs are validated before reaching the Core Service.

---

### FRONT-003: Requirements Editor (DONE)
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

### FRONT-004: Document Preview & WebSocket Integration (DONE)
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

### FRONT-005: Refinement & Export Features (DONE)
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
