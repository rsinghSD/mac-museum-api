# Mac Museum API

[![CI](https://github.com/rsinghSD/mac-museum-api/actions/workflows/ci.yml/badge.svg)](https://github.com/rsinghSD/mac-museum-api/actions/workflows/ci.yml)
![Java 21](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot 3.5](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> A REST API and retro web catalog of Apple hardware history, from the Apple I (1976) to Apple Silicon — like a Pokédex, but for Macs.

> 🚧 **Work in progress.** The CRUD API and seed catalog are done (v0.2). See the [roadmap](#roadmap).

<!-- Screenshot placeholder: replace with docs/screenshot.png once the frontend ships -->
![Screenshot placeholder](https://placehold.co/800x450?text=Mac+Museum+screenshot+coming+soon)

## Planned features
- Browse ~35 iconic models with pagination and sorting
- Filter by year, year range, product family, CPU architecture and free text
- Chronological timeline grouped by CPU era (6502 → 68k → PowerPC → Intel → Apple Silicon)
- Compare models side by side, and a random "Mac of the day"
- Full CRUD for curators, with validation and RFC 7807 error responses
- Mac OS 8 "Platinum"-inspired, mobile-first web UI
- OpenAPI docs via Swagger UI, health checks via Actuator

## Tech stack
| Tech | Why |
|------|-----|
| Java 21 | Current LTS; records and pattern matching keep DTOs concise |
| Spring Boot 3.5 | Industry standard for Java backends ([ADR 0001](docs/adr/0001-use-spring-boot.md)) |
| Spring Data JPA | Repository abstraction and specifications for dynamic filters |
| Flyway | Versioned, reviewable SQL migrations and seed data ([ADR 0002](docs/adr/0002-use-flyway-for-migrations.md)) |
| H2 / PostgreSQL | Zero-setup dev/test DB; robust production DB |
| springdoc-openapi | Generated, always-in-sync API docs |
| JUnit 5 + MockMvc + JaCoCo | Fast integration tests with an enforced coverage bar |
| Vanilla HTML/CSS/JS | No second toolchain; one JAR serves API + UI ([ADR 0003](docs/adr/0003-static-frontend-instead-of-react.md)) |
| GitHub Actions | `mvn verify` on every push and pull request |

## Run locally
```bash
./mvnw verify            # build + tests + coverage
./mvnw spring-boot:run   # http://localhost:8080
```

## Try it
```bash
curl "http://localhost:8080/api/v1/models?size=5&sort=releaseYear,desc"
curl http://localhost:8080/api/v1/models/macintosh-128k
curl http://localhost:8080/api/v1/families

curl -i -X POST http://localhost:8080/api/v1/models -H 'Content-Type: application/json' -d '{
  "slug": "power-mac-g4-mdd", "name": "Power Mac G4 (Mirrored Drive Doors)",
  "releaseYear": 2002, "familySlug": "power-macintosh", "cpuArchitecture": "POWERPC",
  "cpuName": "PowerPC G4", "clockSpeedMhz": 867, "baseRamKb": 262144,
  "summary": "A dual-processor tower nicknamed for its two drive bays."}'
```
Errors are returned as [RFC 7807](https://www.rfc-editor.org/rfc/rfc7807) problem details:
```json
{"type":"about:blank","title":"Resource not found","status":404,
 "detail":"No model found with slug 'nope'","instance":"/api/v1/models/nope"}
```

## Documentation
- [Architecture](docs/architecture.md)
- [Architecture Decision Records](docs/adr/)
- [User stories](docs/user-stories.md)
- [Contributing](CONTRIBUTING.md) · [Project conventions](CLAUDE.md)

## Roadmap
- [x] **v0.1** — Project setup, CI, docs, user stories
- [x] **v0.2** — Domain, migrations, seed data, CRUD API with tests
- [ ] **v0.3** — Filters, timeline, compare, random, Swagger, error handling
- [ ] **v0.4** — Retro Platinum web frontend
- [ ] **v0.5** — Docker, prod profile
- [ ] **v1.0** — Deployed on Render, final polish

## License
[MIT](LICENSE). Not affiliated with or endorsed by Apple Inc. Apple, Macintosh, iMac, iPod, iPhone and iPad are trademarks of Apple Inc., used here only to identify historical products.
