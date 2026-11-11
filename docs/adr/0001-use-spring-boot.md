# ADR 0001 — Use Spring Boot for the backend

- **Status:** Accepted
- **Date:** 2026-11-11

## Context
The project is a portfolio piece aimed at junior Java backend roles. It needs a
REST API, persistence, validation, API docs, health checks and good testing
support, built and maintained by one person.

## Decision
Use **Spring Boot 3.5 on Java 21** with Spring Web MVC and Spring Data JPA.

## Consequences
- ✅ It is the most requested Java backend framework in job postings, so the
  project shows directly relevant skills.
- ✅ Auto-configuration and starters (validation, actuator, data-jpa) remove
  boilerplate and let the code focus on the domain.
- ✅ First-class test slices (`@SpringBootTest`, MockMvc) make integration
  tests cheap to write.
- ✅ Built-in `ProblemDetail` support for RFC 7807 errors.
- ⚠️ Heavier startup and memory footprint than Quarkus/Micronaut or a minimal
  framework like Javalin. Acceptable for this scale; Render's free tier can run it.
- ⚠️ "Magic" auto-configuration can hide behaviour; mitigated by keeping
  configuration explicit in `application.yml` and documenting conventions in
  `CLAUDE.md`.

## Alternatives considered
- **Quarkus / Micronaut** — faster startup, but less common in junior job
  descriptions.
- **Plain Jakarta EE / Javalin** — more manual wiring, less to learn from for
  the target audience.
