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
| `prod`  | PostgreSQL (via env vars) | Docker / Render deployment |

Flyway runs the same migrations against both databases, so the SQL is kept
portable (ANSI types, no vendor-specific functions).

## Cross-cutting concerns
- **Errors**: one `@RestControllerAdvice` maps exceptions to `ProblemDetail`.
- **Docs**: springdoc generates OpenAPI 3 at `/v3/api-docs`, UI at `/swagger-ui.html`.
- **Health**: Actuator `/actuator/health` used by Docker and Render health checks.
- **Coverage**: JaCoCo report and threshold check during `mvn verify`.

See [`adr/`](adr/) for the reasoning behind key decisions.
