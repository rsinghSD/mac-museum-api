package io.github.rsinghsd.macmuseum;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Runs the prod profile against a real PostgreSQL in Docker, proving the Flyway migrations and
 * queries are portable beyond H2. Skipped automatically when Docker is not available.
 */
@SpringBootTest(properties = {"DB_HOST=unused", "DB_NAME=unused", "DB_USER=unused", "DB_PASSWORD=unused"})
@AutoConfigureMockMvc
@ActiveProfiles("prod")
@Testcontainers(disabledWithoutDocker = true)
class PostgresProfileIT {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    MockMvc mvc;

    @Test
    void migratesAndSeedsPostgres() throws Exception {
        mvc.perform(get("/api/v1/models").param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(35)));
    }

    @Test
    void filtersAndSearchWorkOnPostgres() throws Exception {
        mvc.perform(get("/api/v1/models").param("q", "CUBE").param("cpu", "powerpc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].slug").value("power-mac-g4-cube"));
        mvc.perform(get("/api/v1/models").param("q", "%"))
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void familyCountsTimelineAndCompareWorkOnPostgres() throws Exception {
        mvc.perform(get("/api/v1/families")).andExpect(jsonPath("$.length()").value(16));
        mvc.perform(get("/api/v1/timeline")).andExpect(jsonPath("$.eras.length()").value(6));
        mvc.perform(get("/api/v1/compare").param("slugs", "macintosh-128k,imac-g3"))
                .andExpect(jsonPath("$.comparisons[0].ramRatio").value(256.0));
    }

    @Test
    void rejectsWritesWhenNoApiKeyIsConfigured() throws Exception {
        mvc.perform(delete("/api/v1/models/imac-g3")).andExpect(status().isForbidden());
    }

    @Test
    void healthIsUp() throws Exception {
        mvc.perform(get("/actuator/health")).andExpect(jsonPath("$.status").value("UP"));
    }
}
