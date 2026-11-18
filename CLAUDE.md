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
| Run with Docker | `docker compose up --build` (app + PostgreSQL) |
| Deploy | Render Blueprint `render.yaml`, see `docs/deployment.md` |

`./mvnw verify` must pass before every commit. CI runs the same command.

## Stack
Java 21 · Spring Boot 3.5 · Maven · Spring Data JPA · Flyway · H2 (dev/test) ·
PostgreSQL (`prod` profile) · springdoc-openapi · Bean Validation · Actuator ·
JUnit 5 + MockMvc · Testcontainers · JaCoCo · Docker.

## Package layout (package-by-feature)
```
io.github.rsinghsd.macmuseum
├── model/        AppleModel entity, repository, filters (Specification), service,
│                 CRUD + /random controllers, DTOs
├── family/       ProductFamily entity, repository, service, controller, DTOs
├── timeline/     groups models into CPU eras (/timeline)
├── compare/      side-by-side comparison (/compare)
├── common/       exceptions, GlobalExceptionHandler (ProblemDetail), PageResponse,
│                 OpenAPI + web config
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
- Every endpoint has `@Operation` + `@Tag`; error responses use the reusable
  `ApiErrorResponses.*` annotations. `OpenApiIT` fails if an endpoint is missing.
- Filters are optional query params combined with AND; unknown filter values
  that cannot be converted → 400, unknown family slug → empty page.
- Randomness goes through the injectable `RandomGenerator` bean.
- Write methods (POST/PUT/PATCH/DELETE under `/api/v1`) are guarded by
  `WriteApiKeyInterceptor` (`X-API-Key` vs `WRITE_API_KEY`). Mark new write
  operations with `@ApiErrorResponses.RequiresApiKey`. Dev/test default: open
  without a key; `prod` default: 403 without a key.

## Testing
- Unit tests for services (JUnit 5 + Mockito), named `*Test`.
- MockMvc integration tests for every endpoint, named `*IT` (run by Failsafe
  in `verify`), covering the happy path **and** error cases (404, 400, 409).
  They use `@SpringBootTest` + `@Transactional` so each test rolls back.
- Test names describe behaviour: `returnsNotFoundWhenSlugIsUnknown()`.
- `PostgresProfileIT` runs the `prod` profile on real PostgreSQL via
  Testcontainers; it is skipped without Docker but always runs in CI.
- Coverage target: **80%+ lines**, enforced by JaCoCo in `verify`.

## Runtime
- Production config comes **only** from environment variables (`DB_*`, `PORT`);
  never commit credentials. See `docs/deployment.md`.
- The Docker runtime is a slim JRE: only use JDK modules that are in it
  (e.g. use `java.util.Random`, not `RandomGenerator.getDefault()`).
- If you add a new endpoint, the CI Docker job's smoke test is a good place
  for one extra `curl`.

## Data accuracy
Seed data must only contain facts we are confident about. If a launch price
or spec is uncertain, leave it `NULL`. Summaries are written in our own words.
- Prices are US launch list prices of the base configuration. Subsidised
  on-contract iPhone prices (before 2014) are left `NULL`.
- Apple does not publish clock speeds for recent A-series and M5/M6 chips:
  leave them `NULL` rather than copying benchmark readings.
- Non-Apple machines (`manufacturer` other than `Apple`) belong only in the
  `contemporaries` family. They appear in the catalog, search, detail and
  compare, but never in `/timeline` or `/random` ("Mac of the day").
- **Photos** come only from Wikimedia Commons with a free license (CC0, public
  domain, CC BY, CC BY-SA), always with credit, license and source page. Use
  a photo only if it shows that model or a physically identical sibling;
  otherwise leave the image empty. Never use Apple press images.
- **Emulators**: `emulator.embedUrl` may only be an Infinite Mac `/embed` URL
  (validated in `EmulatorDto` and again in the frontend). Use Infinite Mac's
  exact machine and disk names from its source. If the machine is not this
  exact model, set `exactMachine=false` and say "closest machine" in the label.

## Frontend
Plain HTML/CSS/vanilla JS in `src/main/resources/static`. No build step, no
framework. Mac OS 8 "Platinum"-inspired look, mobile-first. **Do not use the
Apple logo or any Apple-owned assets.**
```
static/
├── index.html        all views as windows; one page
├── css/platinum.css  design tokens in :root, mobile-first media queries
├── img/icon.svg      original pixel-art icon
└── js/
    ├── app.js        routing (#catalog, #timeline, #compare=a,b, #model=slug) + views
    ├── api.js        fetch wrapper; rejects with ApiError holding the ProblemDetail
    ├── dom.js        h() element builder — text via textContent only (no innerHTML)
    └── format.js     clock/RAM/price formatting; null → "Unknown"
```
- Never use `innerHTML` with API data; build nodes with `h()`.
- Keep tap targets ≥ 36px and inputs at 16px font (prevents iOS zoom).
- Test changes at 390px width and on desktop.

## Git
- Conventional, imperative commit messages ("Add timeline endpoint").
- One logical change per commit. Branch from `main`, open a PR, CI must be green.
