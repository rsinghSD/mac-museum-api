package io.github.rsinghsd.macmuseum.model;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

/** End-to-end tests of the model CRUD endpoints against the seeded H2 database. */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AppleModelControllerIT {

    private static final String PROBLEM_JSON = "application/problem+json";

    private static final String VALID_BODY = """
            {
              "slug": "power-mac-g4-test-entry",
              "name": "Power Mac G4 (test entry)",
              "releaseYear": 2002,
              "launchPriceUsd": null,
              "familySlug": "power-macintosh",
              "cpuArchitecture": "POWERPC",
              "cpuName": "PowerPC G4",
              "clockSpeedMhz": 867,
              "baseRamKb": 262144,
              "summary": "A dual-processor tower nicknamed for its two drive bays."
            }
            """;

    @Autowired
    MockMvc mvc;

    @Nested
    class ListModels {

        @Test
        void returnsFirstPageSortedByYear() throws Exception {
            mvc.perform(get("/api/v1/models"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.page").value(0))
                    .andExpect(jsonPath("$.size").value(20))
                    .andExpect(jsonPath("$.content.length()").value(20))
                    .andExpect(jsonPath("$.content[0].slug").value("apple-i"))
                    .andExpect(jsonPath("$.content[0].family.slug").value("apple-i-ii"))
                    .andExpect(jsonPath("$.content[0].id").doesNotExist())
                    .andExpect(jsonPath("$.first").value(true));
        }

        @Test
        void supportsPaginationAndDescendingSort() throws Exception {
            mvc.perform(get("/api/v1/models").param("size", "5").param("page", "1")
                            .param("sort", "releaseYear,desc"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.page").value(1))
                    .andExpect(jsonPath("$.content.length()").value(5))
                    .andExpect(jsonPath("$.first").value(false));
        }

        @Test
        void capsPageSizeAtOneHundred() throws Exception {
            mvc.perform(get("/api/v1/models").param("size", "1000"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size").value(100));
        }

        @Test
        void rejectsUnknownSortProperty() throws Exception {
            mvc.perform(get("/api/v1/models").param("sort", "id"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(jsonPath("$.detail").value(org.hamcrest.Matchers.containsString("Cannot sort by 'id'")));
        }
    }

    @Nested
    class GetModel {

        @Test
        void returnsModelBySlug() throws Exception {
            mvc.perform(get("/api/v1/models/macintosh-128k"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Macintosh 128K"))
                    .andExpect(jsonPath("$.releaseYear").value(1984))
                    .andExpect(jsonPath("$.launchPriceUsd").value(2495.00))
                    .andExpect(jsonPath("$.cpuArchitecture").value("M68K"))
                    .andExpect(jsonPath("$.baseRamKb").value(128));
        }

        @Test
        void returnsImageAndExactEmulator() throws Exception {
            mvc.perform(get("/api/v1/models/macintosh-128k"))
                    .andExpect(jsonPath("$.image.url").value(matchesPattern("https://(upload|thumb)\\.wikimedia\\.org/.+")))
                    .andExpect(jsonPath("$.image.license").isNotEmpty())
                    .andExpect(jsonPath("$.image.credit").isNotEmpty())
                    .andExpect(jsonPath("$.emulator.provider").value("Infinite Mac"))
                    .andExpect(jsonPath("$.emulator.label").value("System 1.0 on an emulated Mac 128K"))
                    .andExpect(jsonPath("$.emulator.embedUrl")
                            .value("https://infinitemac.org/embed?disk=System%201.0&machine=Mac%20128K"))
                    .andExpect(jsonPath("$.emulator.url")
                            .value("https://infinitemac.org/run?disk=System%201.0&machine=Mac%20128K"))
                    .andExpect(jsonPath("$.emulator.exactMachine").value(true));
        }

        @Test
        void returnsNullImageAndEmulatorWhenUnavailable() throws Exception {
            mvc.perform(get("/api/v1/models/mac-pro-2019"))
                    .andExpect(jsonPath("$.image").isEmpty())
                    .andExpect(jsonPath("$.emulator").isEmpty());
        }

        @Test
        void returnsNullPriceWhenUnknown() throws Exception {
            mvc.perform(get("/api/v1/models/macintosh-se"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.launchPriceUsd").isEmpty());
        }

        @Test
        void returnsProblemDetailWhenSlugIsUnknown() throws Exception {
            mvc.perform(get("/api/v1/models/hackintosh"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Resource not found"))
                    .andExpect(jsonPath("$.detail").value("No model found with slug 'hackintosh'"))
                    .andExpect(jsonPath("$.instance").value("/api/v1/models/hackintosh"));
        }
    }

    @Nested
    class CreateModel {

        @Test
        void createsModelAndReturnsLocation() throws Exception {
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", endsWith("/api/v1/models/power-mac-g4-test-entry")))
                    .andExpect(jsonPath("$.slug").value("power-mac-g4-test-entry"))
                    .andExpect(jsonPath("$.family.name").value("Power Macintosh"))
                    .andExpect(jsonPath("$.manufacturer").value("Apple"));

            mvc.perform(get("/api/v1/models/power-mac-g4-test-entry")).andExpect(status().isOk());
        }

        @Test
        void storesImageAndEmulator() throws Exception {
            String body = VALID_BODY.replace("\"summary\":", """
                    "image": {"url": "https://upload.wikimedia.org/x.jpg", "credit": "Someone",
                              "license": "CC BY 4.0", "sourceUrl": "https://commons.wikimedia.org/wiki/File:X.jpg"},
                    "emulator": {"provider": "Infinite Mac", "label": "Mac OS 9.0 on an emulated G4",
                                 "url": "https://infinitemac.org/run?disk=Mac%20OS%209.0",
                                 "embedUrl": "https://infinitemac.org/embed?disk=Mac%20OS%209.0", "exactMachine": false},
                    "summary":""");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.image.credit").value("Someone"))
                    .andExpect(jsonPath("$.emulator.exactMachine").value(false));
        }

        @Test
        void rejectsEmbedUrlOutsideInfiniteMacAndNonHttpsImages() throws Exception {
            String body = VALID_BODY.replace("\"summary\":", """
                    "image": {"url": "http://example.com/x.jpg", "credit": "Someone", "license": "CC BY 4.0"},
                    "emulator": {"provider": "Evil", "label": "x", "url": "https://example.com",
                                 "embedUrl": "https://example.com/phish", "exactMachine": true},
                    "summary":""");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors['image.url']").value("must be an https URL"))
                    .andExpect(jsonPath("$.errors['emulator.embedUrl']").exists());
        }

        @Test
        void storesExplicitManufacturer() throws Exception {
            String ibm = VALID_BODY.replace("\"summary\":", "\"manufacturer\": \"IBM\", \"summary\":");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(ibm))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.manufacturer").value("IBM"));
        }

        @Test
        void returnsFieldErrorsWhenInvalid() throws Exception {
            String invalid = """
                    {"slug": "Not A Slug", "name": "", "releaseYear": 1950, "launchPriceUsd": -1,
                     "familySlug": "imac", "cpuName": "x", "summary": "x"}
                    """;
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(invalid))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(jsonPath("$.title").value("Validation failed"))
                    .andExpect(jsonPath("$.errors.slug").exists())
                    .andExpect(jsonPath("$.errors.name").exists())
                    .andExpect(jsonPath("$.errors.releaseYear").exists())
                    .andExpect(jsonPath("$.errors.launchPriceUsd").exists())
                    .andExpect(jsonPath("$.errors.cpuArchitecture").exists());
        }

        @Test
        void returnsConflictForDuplicateSlug() throws Exception {
            String duplicate = VALID_BODY.replace("power-mac-g4-test-entry", "imac-g3");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(duplicate))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(PROBLEM_JSON))
                    .andExpect(jsonPath("$.detail").value("A model with slug 'imac-g3' already exists"));
        }

        @Test
        void returnsBadRequestForUnknownFamily() throws Exception {
            String unknownFamily = VALID_BODY.replace("\"power-macintosh\"", "\"toaster\"");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(unknownFamily))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.detail").value("Unknown family 'toaster'"));
        }

        @Test
        void returnsBadRequestForMalformedJson() throws Exception {
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content("{not json"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(PROBLEM_JSON));
        }

        @Test
        void returnsBadRequestForUnknownCpuArchitecture() throws Exception {
            String badCpu = VALID_BODY.replace("\"POWERPC\"", "\"Z80\"");
            mvc.perform(post("/api/v1/models").contentType(MediaType.APPLICATION_JSON).content(badCpu))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void returnsUnsupportedMediaTypeForNonJsonBody() throws Exception {
            mvc.perform(post("/api/v1/models").contentType(MediaType.TEXT_PLAIN).content("hello"))
                    .andExpect(status().isUnsupportedMediaType())
                    .andExpect(content().contentType(PROBLEM_JSON));
        }
    }

    @Nested
    class UpdateModel {

        @Test
        void replacesModel() throws Exception {
            String body = VALID_BODY.replace("power-mac-g4-test-entry", "imac-g3")
                    .replace("Power Mac G4 (test entry)", "iMac G3 (Bondi Blue)");
            mvc.perform(put("/api/v1/models/imac-g3").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("iMac G3 (Bondi Blue)"))
                    .andExpect(jsonPath("$.launchPriceUsd").isEmpty());
        }

        @Test
        void returnsNotFoundForUnknownSlug() throws Exception {
            mvc.perform(put("/api/v1/models/hackintosh").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(PROBLEM_JSON));
        }

        @Test
        void returnsConflictWhenRenamingToTakenSlug() throws Exception {
            String body = VALID_BODY.replace("power-mac-g4-test-entry", "imac-g4");
            mvc.perform(put("/api/v1/models/imac-g3").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isConflict());
        }

        @Test
        void returnsBadRequestWhenInvalid() throws Exception {
            mvc.perform(put("/api/v1/models/imac-g3").contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.slug").exists());
        }
    }

    @Nested
    class DeleteModel {

        @Test
        void deletesModel() throws Exception {
            mvc.perform(delete("/api/v1/models/ipod-1st-gen")).andExpect(status().isNoContent());
            mvc.perform(get("/api/v1/models/ipod-1st-gen")).andExpect(status().isNotFound());
        }

        @Test
        void returnsNotFoundForUnknownSlug() throws Exception {
            mvc.perform(delete("/api/v1/models/hackintosh"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(PROBLEM_JSON));
        }
    }
}
