# ADR 0002 — Use Flyway for schema migrations and seed data

- **Status:** Accepted
- **Date:** 2026-11-11

## Context
The schema must be identical on H2 (dev/test) and PostgreSQL (prod), and the
catalog ships with ~35 curated seed models. We need a repeatable, versioned way
to create the schema and load data.

## Decision
Use **Flyway** with plain SQL migrations in `src/main/resources/db/migration`.
Hibernate runs with `ddl-auto=validate` so it only checks that entities match
the migrated schema.

## Consequences
- ✅ Schema history is versioned in Git and applied in order everywhere
  (developer laptop, CI, Docker, Render).
- ✅ Plain SQL is readable in code review and teaches real DDL.
- ✅ `validate` catches entity/schema drift at startup and in tests.
- ✅ Seed data is just another migration, so every environment starts with the
  same catalog.
- ⚠️ SQL must stay portable between H2 and PostgreSQL (no vendor-specific
  syntax). Tests on H2 do not catch every PostgreSQL difference; a
  Testcontainers job is on the roadmap.
- ⚠️ Merged migrations are immutable; fixes require a new migration.

## Alternatives considered
- **Hibernate `ddl-auto=update`** — convenient but not versioned, unsafe in
  production, and cannot seed data reliably.
- **Liquibase** — equally capable; XML/YAML changelogs are more verbose and
  Flyway's SQL-first approach is simpler for this project.
