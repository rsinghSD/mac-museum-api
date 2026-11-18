# Architecture

Mac Museum API is a single deployable Spring Boot application that serves both
a JSON REST API and a static web frontend.

## Overview

```mermaid
flowchart LR
    subgraph Client
        B[Browser / phone<br/>static HTML + JS]
        C[curl / Swagger UI /<br/>3rd-party clients]
    end

    subgraph App[Spring Boot application]
        S[Static resources<br/>/index.html, /css, /js]
        CT[REST controllers<br/>/api/v1/**]
        EH[GlobalExceptionHandler<br/>RFC 7807 ProblemDetail]
        SV[Services<br/>business rules]
        R[Spring Data JPA<br/>repositories]
        F[Flyway migrations<br/>schema + seed data]
        A[Actuator<br/>/actuator/health]
    end

    DB[(H2 in dev/test<br/>PostgreSQL in prod)]

    B -->|GET /| S
    B -->|fetch JSON| CT
    C --> CT
    CT --> SV --> R --> DB
    CT -.errors.-> EH
    F --> DB
```

## Layers

| Layer | Responsibility | Rules |
|-------|----------------|-------|
| Controller | HTTP mapping, input validation, DTO in/out | No business logic, no entities in signatures |
| Service | Business rules, transactions, mapping entity ↔ DTO | Throws domain exceptions |
| Repository | Persistence via Spring Data JPA | Queries/specifications only |
| Entity | JPA mapping of the schema | Never leaves the service layer |

## Domain model

```mermaid
erDiagram
    PRODUCT_FAMILY ||--o{ APPLE_MODEL : contains
    PRODUCT_FAMILY {
        bigint id PK
        varchar slug UK
        varchar name
        text description
    }
    APPLE_MODEL {
        bigint id PK
        varchar slug UK
        varchar name
        int release_year
        numeric launch_price_usd "nullable"
        bigint family_id FK
        varchar cpu_architecture "MOS_6502, M68K, POWERPC, INTEL_X86, ARM, APPLE_SILICON"
        varchar cpu_name
        int clock_speed_mhz "nullable"
        int base_ram_kb "nullable"
        text summary
        varchar manufacturer "Apple, or e.g. IBM for contemporaries"
    }
```

## Request flow (example: `GET /api/v1/models/macintosh-128k`)

```mermaid
sequenceDiagram
    participant U as Client
    participant C as AppleModelController
    participant S as AppleModelService
    participant R as AppleModelRepository
    U->>C: GET /api/v1/models/macintosh-128k
    C->>S: findBySlug("macintosh-128k")
    S->>R: findBySlug(...)
    R-->>S: Optional<AppleModel>
    alt found
        S-->>C: AppleModelResponse (record)
        C-->>U: 200 OK + JSON
    else not found
        S-->>C: throws ModelNotFoundException
        C-->>U: 404 application/problem+json
    end
```

## Profiles

| Profile | Database | Use |
|---------|----------|-----|
| default | H2 in-memory | local development, tests |
| `prod`  | PostgreSQL (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`) | Docker / Render deployment |

## Deployment

```mermaid
flowchart LR
    GH[GitHub main] -->|auto-deploy| R[Render: Docker web service<br/>multi-stage image, JRE 21, non-root]
    R -->|DB_* from Blueprint| PG[(Render PostgreSQL 16)]
    R -->|/actuator/health| HC[Render health check]
    U[Visitors] -->|HTTPS| R
```

CI (`.github/workflows/ci.yml`) runs `mvn verify`, including a Testcontainers
PostgreSQL test, and in a second job builds the Docker image, starts it against
PostgreSQL and smoke-tests it. See [deployment.md](deployment.md).

Flyway runs the same migrations against both databases, so the SQL is kept
portable (ANSI types, no vendor-specific functions). `PostgresProfileIT`
proves it on real PostgreSQL.

## Cross-cutting concerns
- **Errors**: one `@RestControllerAdvice` maps exceptions to `ProblemDetail`.
- **Write protection**: `WriteApiKeyInterceptor` requires `X-API-Key` on write
  methods when `WRITE_API_KEY` is set; in `prod` without a key, writes get 403.
- **Docs**: springdoc generates OpenAPI 3 at `/v3/api-docs`, UI at `/swagger-ui.html`.
- **Health**: Actuator `/actuator/health` used by Docker and Render health checks.
- **Coverage**: JaCoCo report and threshold check during `mvn verify`.

See [`adr/`](adr/) for the reasoning behind key decisions.
