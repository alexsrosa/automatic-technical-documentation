# Phase 5: Observability & Quality Assurance

**Goal:** Ensure the system is robust, monitored, and thoroughly tested.

## Steps

### STEP-017: Distributed Tracing & Logging
**Description:**
- Instrument all services with Micrometer Tracing / OpenTelemetry.
- Ensure Trace IDs are propagated (BFF -> Core -> Rabbit -> Worker).
- Configure structured logging (JSON format).

**Affected Files:**
- All Services (`pom.xml`, `application.yml`)

**Dependencies:**
- STEP-004, STEP-009, STEP-014

**Business Value:**
- Ability to debug issues across microservices.

**Acceptance Criteria:**
- Traces visible in Grafana (Tempo/Jaeger).
- Logs correlated with Trace IDs.

---

### STEP-018: Performance Testing (JMeter/K6)
**Description:**
- Create load test scripts.
- Test endpoint latency and queue throughput.
- Verify system behavior under load (1000 concurrent users).

**Affected Files:**
- `/tests/performance/`

**Dependencies:**
- All previous phases.

**Business Value:**
- Validates non-functional requirements.

**Acceptance Criteria:**
- P95 latency is within acceptable limits.
- No memory leaks or crashes under load.

---

### STEP-019: End-to-End Testing (Playwright)
**Description:**
- Create E2E test suite covering critical paths:
  - Login -> Create Project -> Generate Doc -> View Result.
- Integrate into CI/CD pipeline.

**Affected Files:**
- `/tests/e2e/`
- `.github/workflows/`

**Dependencies:**
- All previous phases.

**Business Value:**
- Guarantees feature stability before deployment.

**Acceptance Criteria:**
- All critical flows pass automatically.
- Tests run in CI environment.
