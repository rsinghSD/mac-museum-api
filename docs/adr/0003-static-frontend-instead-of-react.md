# ADR 0003 — Static HTML/CSS/JS frontend instead of React

- **Status:** Accepted
- **Date:** 2026-11-11

## Context
The project's main goal is to showcase a Java backend. It still needs a
pleasant, mobile-friendly UI (search, cards, timeline, compare) with a retro
Mac OS 8 "Platinum" look.

## Decision
Build the frontend with **plain HTML, CSS and vanilla JavaScript** served by
Spring Boot from `src/main/resources/static`. No framework, no bundler, no
Node toolchain.

## Consequences
- ✅ One artifact, one deploy: the JAR serves both API and UI; no CORS setup.
- ✅ Zero frontend build step keeps CI fast and the repo approachable for
  backend reviewers.
- ✅ The UI is a real consumer of the public API, proving it is usable.
- ✅ Hand-written CSS is a good fit for a pixel-precise retro theme.
- ⚠️ No component model or state management; must keep JS modular
  (ES modules, small functions) to stay maintainable.
- ⚠️ No frontend unit tests initially; the API itself is fully tested.

## Alternatives considered
- **React/Vite SPA** — richer tooling but adds a second toolchain, a separate
  build and deploy, and shifts reviewer attention away from the backend.
- **Thymeleaf server-side rendering** — viable, but a JSON API + JS client
  better demonstrates API design.
