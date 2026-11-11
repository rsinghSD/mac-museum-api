package io.github.rsinghsd.macmuseum.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** End-to-end behaviour of the write API key, with and without a key configured. */
class WriteApiKeyIT {

    private static final String PROBLEM_JSON = "application/problem+json";

    private static final String BODY = """
            {"slug": "apple-iigs", "name": "Apple IIGS", "releaseYear": 1986, "familySlug": "apple-i-ii",
             "cpuArchitecture": "MOS_6502", "cpuName": "WDC 65C816", "summary": "A 16-bit Apple II."}
            """;

    @Nested
    @SpringBootTest(properties = "app.security.write-api-key=test-key")
    @AutoConfigureMockMvc
    @Transactional
    class WithKeyConfigured {

        @Autowired
        MockMvc mvc;

        @Test
        void readsNeedNoKey() throws Exception {
            mvc.perform(get("/api/v1/models/imac-g3")).andExpect(status().isOk());
        }

        @Test
        void rejectsWriteWithoutKey() throws Exception {
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(BODY))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(header().string("WWW-Authenticate", "ApiKey header=\"X-API-Key\""))
                    .andExpect(jsonPath("$.title").value("Unauthorized"));
        }

        @Test
        void rejectsWriteWithWrongKey() throws Exception {
            mvc.perform(delete("/api/v1/models/imac-g3").header("X-API-Key", "nope"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.detail").value("The API key is not valid."));
            mvc.perform(get("/api/v1/models/imac-g3")).andExpect(status().isOk());
        }

        @Test
        void acceptsWriteWithCorrectKey() throws Exception {
            mvc.perform(post("/api/v1/models").header("X-API-Key", "test-key")
                            .contentType(MediaType.APPLICATION_JSON).content(BODY))
                    .andExpect(status().isCreated());
            mvc.perform(delete("/api/v1/models/apple-iigs").header("X-API-Key", "test-key"))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @SpringBootTest(properties = "app.security.allow-unauthenticated-writes=false")
    @AutoConfigureMockMvc
    class WithoutKeyInProduction {

        @Autowired
        MockMvc mvc;

        @Test
        void rejectsAllWrites() throws Exception {
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(BODY))
                    .andExpect(status().isForbidden())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(jsonPath("$.title").value("Writes disabled"));
            mvc.perform(delete("/api/v1/models/imac-g3").header("X-API-Key", "anything"))
                    .andExpect(status().isForbidden());
        }

        @Test
        void readsStillWork() throws Exception {
            mvc.perform(get("/api/v1/timeline")).andExpect(status().isOk());
        }
    }
}
