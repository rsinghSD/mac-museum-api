# CLAUDE.md — Project conventions for Mac Museum API

This file tells AI assistants (and humans) how this codebase is organised and
what "done" means. Keep it short and up to date.

## What this project is
A Spring Boot REST API + static retro web catalog of Apple hardware history
(Apple I, 1976 → Apple Silicon). Portfolio project: code clarity, tests and
docs matter as much as features.

## Commands
| Task | Command |
|------|---------|
| Build + all tests + coverage | `./mvnw verify` |
| Run locally (H2, dev data) | `./mvnw spring-boot:run` |
| Single test class | `./mvnw test -Dtest=AppleModelServiceTest` |
| Coverage report | `target/site/jacoco/index.html` (after `verify`) |
| Run with Docker | `docker compose up --build` |

`./mvnw verify` must pass before every commit. CI runs the same command.

## Stack
Java 21 · Spring Boot 3.5 · Maven · Spring Data JPA · Flyway · H2 (dev/test) ·
PostgreSQL (`prod` profile) · springdoc-openapi · Bean Validation · Actuator ·
JUnit 5 + MockMvc · JaCoCo.

## Package layout (package-by-feature)
```
io.github.rsinghsd.macmuseum
├── model/        AppleModel entity, repository, service, controller, DTOs
├── family/       ProductFamily entity, repository, service, controller, DTOs
├── timeline/     timeline grouping logic + controller (phase 3)
├── common/       exceptions, GlobalExceptionHandler (ProblemDetail), PageResponse
└── MacMuseumApplication.java
```
Each feature package owns its controller, service, repository, entity and DTOs.
Cross-feature access goes through services, never through another feature's
repository.

## Coding rules
- **Never expose JPA entities** from controllers. Use Java `record` DTOs and
  map explicitly (small static mappers, no MapStruct).
- **Controllers are thin**: validate input, call a service, return a DTO.
  Business rules live in services.
- **Constructor injection only**, no field `@Autowired`.
- **Errors** are returned as RFC 7807 `ProblemDetail` via a single
  `@RestControllerAdvice`. Throw domain exceptions (e.g. `ModelNotFoundException`)
  from services; do not build HTTP responses in services.
- **Validation** with Bean Validation annotations on request DTOs; invalid input
  → HTTP 400 with field errors in the problem `errors` property.
- **Slugs** are lowercase kebab-case (`macintosh-128k`) and are the public
  identifier. Database ids are internal.
- **Read-only service methods** use `@Transactional(readOnly = true)`.
- `spring.jpa.open-in-view=false`; fetch what you need inside the service.
- Schema changes **only via new Flyway migrations** (`V{n}__description.sql`).
  Never edit a migration that has been merged. Hibernate runs `ddl-auto=validate`.
- Use `java.time` and `BigDecimal` for money. A `null` price, clock speed or
  RAM value means "unknown".
- Sorting is restricted to a whitelist (`AppleModelService.SORTABLE_PROPERTIES`);
  unknown sort properties return 400 instead of a 500 from JPA.

## API conventions
- Base path: `/api/v1`.
- Plural nouns: `/models`, `/families`.
- Lists are paginated with Spring `Pageable` (`page`, `size`, `sort`).
- `201 Created` + `Location` header on POST, `204 No Content` on DELETE.
- Every endpoint is documented with springdoc annotations where defaults are
  not self-explanatory.

## Testing
- Unit tests for services (JUnit 5 + Mockito), named `*Test`.
- MockMvc integration tests for every endpoint, named `*IT` (run by Failsafe
  in `verify`), covering the happy path **and** error cases (404, 400, 409).
  They use `@SpringBootTest` + `@Transactional` so each test rolls back.
- Test names describe behaviour: `returnsNotFoundWhenSlugIsUnknown()`.
- Coverage target: **80%+ lines**, enforced by JaCoCo in `verify`.

## Data accuracy
Seed data must only contain facts we are confident about. If a launch price
or spec is uncertain, leave it `NULL`. Summaries are written in our own words.

## Frontend
Plain HTML/CSS/vanilla JS in `src/main/resources/static`. No build step, no
framework. Mac OS 8 "Platinum"-inspired look, mobile-first. **Do not use the
Apple logo or any Apple-owned assets.**

## Git
- Conventional, imperative commit messages ("Add timeline endpoint").
- One logical change per commit. Branch from `main`, open a PR, CI must be green.
