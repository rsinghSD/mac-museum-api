# User stories

Stories are grouped by epic. Each one becomes a GitHub issue (see
`scripts/setup-github.sh`) labelled `type:user-story`, its `epic:*` label, a
priority, and the milestone it ships in.

**Roles**
- **Visitor** — anyone browsing the web catalog, mostly on a phone.
- **API consumer** — a developer calling the REST API.
- **Curator** — an admin who maintains catalog data.
- **Operator** — whoever deploys and runs the service.

| ID | Story | Epic | Priority | Milestone |
|----|-------|------|----------|-----------|
| US-01 | Browse all models | Browse | high | v0.2 |
| US-02 | View model details | Browse | high | v0.2 |
| US-03 | List product families | Browse | medium | v0.2 |
| US-04 | Explore the timeline | Browse | medium | v0.3 |
| US-05 | Mac of the day | Browse | low | v0.3 |
| US-06 | Filter by year range | Search | high | v0.3 |
| US-07 | Filter by family and CPU | Search | high | v0.3 |
| US-08 | Free-text search | Search | medium | v0.3 |
| US-09 | Compare two models | Compare | medium | v0.3 |
| US-10 | Compare on mobile | Compare | medium | v0.4 |
| US-11 | Add a model | Admin | high | v0.2 |
| US-12 | Update and delete a model | Admin | high | v0.2 |
| US-13 | Clear validation errors | Admin | high | v0.3 |
| US-14 | Health check | Ops | medium | v0.5 |
| US-15 | One-command deploy | Ops | medium | v1.0 |

---

## Epic: Browse

### US-01 — Browse all models
**As a** visitor, **I want** to see a paginated list of Apple models as cards,
**so that** I can casually explore the catalog.

**Acceptance criteria**
- [ ] `GET /api/v1/models` returns a page of models with `content`, page number, size and total elements.
- [ ] Default page size is 20; `size` is capped at 100.
- [ ] Results can be sorted with `sort=releaseYear,asc` (and by `name`, `launchPriceUsd`).
- [ ] The web UI shows each model as a card with name, year, family and CPU.
- [ ] Responses never expose internal database ids.

### US-02 — View model details
**As a** visitor, **I want** to open a model and see all its specs and a short summary,
**so that** I can learn what made it notable.

**Acceptance criteria**
- [ ] `GET /api/v1/models/{slug}` returns name, year, family, CPU architecture, CPU name, clock speed, base RAM, launch price and summary.
- [ ] An unknown slug returns `404` as `application/problem+json`.
- [ ] Unknown launch price is returned as `null` and shown as "Unknown" in the UI.
- [ ] The UI shows details in a Platinum-style window that can be closed.

### US-03 — List product families
**As an** API consumer, **I want** to list all product families,
**so that** I can build filters and navigation.

**Acceptance criteria**
- [ ] `GET /api/v1/families` returns all families with slug, name, description and model count.
- [ ] Families are sorted alphabetically by name.

### US-04 — Explore the timeline
**As a** visitor, **I want** a chronological timeline grouped by CPU era,
**so that** I can see how Apple moved from 6502 to 68k, PowerPC, Intel and Apple Silicon.

**Acceptance criteria**
- [ ] `GET /api/v1/timeline` returns eras in chronological order, each with architecture, label, start year, end year and its models sorted by year.
- [ ] An era with no models is omitted.
- [ ] The UI renders a vertical, scrollable timeline usable on a 360 px wide screen.

### US-05 — Mac of the day
**As a** visitor, **I want** a featured random model,
**so that** I discover something new each visit.

**Acceptance criteria**
- [ ] `GET /api/v1/random` returns one model.
- [ ] When the catalog is empty, it returns `404` with a problem detail.
- [ ] The home page shows the "Mac of the day" in its own window.

## Epic: Search

### US-06 — Filter by year range
**As an** API consumer, **I want** to filter models by exact year or year range,
**so that** I can focus on a period.

**Acceptance criteria**
- [ ] `year`, `fromYear` and `toYear` query parameters are supported and combinable with pagination.
- [ ] `fromYear > toYear` returns `400` with a clear message.
- [ ] Non-numeric years return `400`.

### US-07 — Filter by family and CPU
**As a** visitor, **I want** to filter by product family and CPU architecture,
**so that** I can, for example, see only PowerPC PowerBooks.

**Acceptance criteria**
- [ ] `family=<slug>` and `cpu=<ARCHITECTURE>` filters can be combined with each other and with year filters.
- [ ] An unknown `cpu` value returns `400` listing the allowed values.
- [ ] An unknown family slug returns an empty page (not an error).
- [ ] The UI offers dropdowns populated from `/families` and the CPU enum.

### US-08 — Free-text search
**As a** visitor, **I want** to type a word like "cube" or "G4",
**so that** I can quickly find a model I half remember.

**Acceptance criteria**
- [ ] `q` matches case-insensitively against model name, CPU name and summary.
- [ ] `q` can be combined with all other filters.
- [ ] The UI search box updates results as I type (debounced).

## Epic: Compare

### US-09 — Compare two models
**As an** API consumer, **I want** to fetch two or more models side by side,
**so that** I can show how specs evolved.

**Acceptance criteria**
- [ ] `GET /api/v1/compare?slugs=a,b` returns the requested models in the order given.
- [ ] Fewer than 2 or more than 4 slugs return `400`.
- [ ] Any unknown slug returns `404` naming the missing slug.
- [ ] Response includes simple derived ratios (e.g. clock speed and RAM multiples) between the first and the others.

### US-10 — Compare on mobile
**As a** visitor on my phone, **I want** to pick two models and see them in a readable comparison,
**so that** I can compare without a desktop.

**Acceptance criteria**
- [ ] The compare view stacks into two columns on narrow screens without horizontal scrolling.
- [ ] Bigger values are highlighted per row.
- [ ] The comparison is shareable via URL (`#compare=a,b`).

## Epic: Admin

### US-11 — Add a model
**As a** curator, **I want** to add a model via the API,
**so that** the catalog can grow.

**Acceptance criteria**
- [ ] `POST /api/v1/models` with a valid body returns `201 Created` and a `Location` header.
- [ ] A duplicate slug returns `409 Conflict`.
- [ ] An unknown family slug returns `400`.
- [ ] In production, writes require the `X-API-Key` header (401 if missing or wrong).

### US-12 — Update and delete a model
**As a** curator, **I want** to correct or remove a model,
**so that** the catalog stays accurate.

**Acceptance criteria**
- [ ] `PUT /api/v1/models/{slug}` replaces the model and returns `200` with the updated body.
- [ ] `DELETE /api/v1/models/{slug}` returns `204`.
- [ ] Both return `404` for an unknown slug.

### US-13 — Clear validation errors
**As a** curator, **I want** precise error messages when I send bad data,
**so that** I can fix my request quickly.

**Acceptance criteria**
- [ ] All errors use RFC 7807 `ProblemDetail` (`type`, `title`, `status`, `detail`, `instance`).
- [ ] Validation failures return `400` with an `errors` map of field → message.
- [ ] Malformed JSON returns `400`, not `500`.
- [ ] Unexpected errors return `500` without leaking stack traces.

## Epic: Ops

### US-14 — Health check
**As an** operator, **I want** a health endpoint,
**so that** Docker and the hosting platform can detect a broken instance.

**Acceptance criteria**
- [ ] `GET /actuator/health` returns `{"status":"UP"}` when the database is reachable.
- [ ] Only `health` and `info` actuator endpoints are exposed.
- [ ] The Docker image defines a `HEALTHCHECK` using it.

### US-15 — One-command deploy
**As an** operator, **I want** to run the full stack with one command and deploy to Render,
**so that** reviewers can try the project easily.

**Acceptance criteria**
- [ ] `docker compose up --build` starts the app with PostgreSQL using the `prod` profile.
- [ ] Database credentials come from environment variables only.
- [ ] README documents deploying to Render's free tier step by step.
- [ ] CI runs `mvn verify` on every push and pull request.
