# Contributing to Mac Museum API

Thanks for your interest! Contributions of code, docs and **historical
corrections** are all welcome.

## Getting started
1. Fork and clone the repository.
2. Install **Java 21**. Maven is provided through the wrapper (`./mvnw`).
3. Run `./mvnw verify` — it must pass before you change anything.
4. Start the app with `./mvnw spring-boot:run` and open <http://localhost:8080>.

Read [`CLAUDE.md`](CLAUDE.md) for project conventions (package layout, DTO
rules, error handling, testing).

## Workflow
1. Pick an issue (look for `good first issue`) or open one first for larger changes.
2. Create a branch: `feature/<short-name>` or `fix/<short-name>`.
3. Make small, focused commits with imperative messages ("Add compare endpoint").
4. Add or update tests. New endpoints need MockMvc tests for happy path **and** errors.
5. Run `./mvnw verify` (tests + JaCoCo coverage check).
6. Open a pull request using the template and link the issue (`Closes #12`).

## Correcting historical data
Seed data lives in Flyway migrations under `src/main/resources/db/migration`.
- **Never edit a merged migration.** Add a new `V{n}__fix_<what>.sql` instead.
- Cite a reliable source in the PR description for any factual change.
- If you are not sure about a value (e.g. launch price), leave it `NULL`.
- Write summaries in your own words; do not paste text from other sites.

## Style
- Java: standard Java conventions, 4-space indent, constructor injection, records for DTOs.
- No Apple logos or Apple-owned assets in the frontend.

## Code of conduct
Be kind and constructive. Harassment of any kind is not tolerated.
