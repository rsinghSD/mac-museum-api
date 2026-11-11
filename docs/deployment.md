# Deployment

The app ships as one Docker image (API + frontend) and needs one PostgreSQL
database. Everything is configured through environment variables.

## Configuration

| Variable | Required | Default | Purpose |
|----------|----------|---------|---------|
| `SPRING_PROFILES_ACTIVE` | yes | `prod` in the image | Selects `application-prod.yml` |
| `DB_HOST` | yes | – | PostgreSQL host |
| `DB_PORT` | no | `5432` | PostgreSQL port |
| `DB_NAME` | yes | – | Database name |
| `DB_USER` | yes | – | Database user |
| `DB_PASSWORD` | yes | – | Database password |
| `DB_POOL_SIZE` | no | `5` | Hikari max pool size |
| `PORT` | no | `8080` | HTTP port (Render sets it) |

`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and
`SPRING_DATASOURCE_PASSWORD` override the `DB_*` variables if you prefer a full
JDBC URL.

Flyway creates the schema and loads the seed catalog on first start. Health:
`GET /actuator/health` (also `/actuator/health/liveness` and `/readiness`).

## Render (free tier)

The repository contains a [Blueprint](../render.yaml) that creates a Docker web
service and a PostgreSQL database and wires the credentials between them.

### Option A — Blueprint (recommended)

1. Push the repository to GitHub (already done if you are reading this there).
2. Sign in to [Render](https://render.com) with GitHub.
3. **New → Blueprint**, pick `mac-museum-api`, and confirm. Render reads
   `render.yaml` and creates:
   - `mac-museum-db`: PostgreSQL 16, free plan
   - `mac-museum-api`: Docker web service, free plan, health check on
     `/actuator/health`, `DB_*` variables injected from the database
4. Wait for the first build (around 5 minutes: Maven downloads dependencies,
   then the image is built).
5. Open `https://<your-service>.onrender.com`. Swagger UI is at
   `/swagger-ui.html`.
6. Put the URL in the README's "Live demo" line.

Every push to `main` redeploys automatically (`autoDeploy: true`).

### Option B — manual setup

1. **New → PostgreSQL**: name `mac-museum-db`, plan *Free*, version 16.
   When it is ready, copy the *Hostname*, *Port*, *Database*, *Username* and
   *Password* from its **Info** page (use the internal hostname).
2. **New → Web Service** → connect the repository → runtime **Docker**,
   plan *Free*.
3. **Environment**: add `SPRING_PROFILES_ACTIVE=prod` and the five `DB_*`
   variables from step 1.
4. **Settings → Health Check Path**: `/actuator/health`.
5. Create the service and wait for the deploy to finish.

### Free-tier caveats

- **Cold starts.** Free web services sleep after a period without traffic. The
  first request after that wakes the service, which takes about a minute while
  the JVM starts. Later requests are fast.
- **Database lifetime.** Free PostgreSQL databases expire after a limited
  period (30 days at the time of writing) unless you upgrade. Check Render's
  current pricing page. When a new database is created, Flyway re-seeds the
  catalog automatically.
- **Memory.** The free instance has 512 MB of RAM. The image caps the heap at
  75% of the container memory (`-XX:MaxRAMPercentage=75`), which fits.

### Security note

The write endpoints (`POST`, `PUT`, `DELETE /api/v1/models`) have **no
authentication** yet, so anyone who finds a public deployment can change the
catalog. That is acceptable for a demo, and the roadmap includes protecting them
with an API key. Until then, treat any public data as disposable. Deleting and
recreating the database restores the seed catalog.

## Docker anywhere else

```bash
docker build -t mac-museum-api .
docker run -p 8080:8080 \
  -e DB_HOST=my-db-host -e DB_NAME=macmuseum \
  -e DB_USER=macmuseum -e DB_PASSWORD=secret \
  mac-museum-api
```

Or run the full stack locally with `docker compose up --build`. It uses the
defaults in `docker-compose.yml`, or the values from a `.env` file based on
`.env.example`.
