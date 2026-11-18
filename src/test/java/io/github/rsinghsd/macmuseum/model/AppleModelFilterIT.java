package io.github.rsinghsd.macmuseum.model;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Filter parameters of GET /api/v1/models against the seed catalog. */
@SpringBootTest
@AutoConfigureMockMvc
class AppleModelFilterIT {

    @Autowired
    MockMvc mvc;

    @Test
    void filtersByExactYear() throws Exception {
        mvc.perform(get("/api/v1/models").param("year", "1984"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect(jsonPath("$.content[*].slug", hasItem("macintosh-128k")))
                .andExpect(jsonPath("$.content[*].releaseYear", everyItem(is(1984))));
    }

    @Test
    void filtersByInclusiveYearRange() throws Exception {
        mvc.perform(get("/api/v1/models").param("fromYear", "1998").param("toYear", "2001").param("size", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].releaseYear", everyItem(greaterThanOrEqualTo(1998))))
                .andExpect(jsonPath("$.content[*].releaseYear", everyItem(lessThanOrEqualTo(2001))))
                .andExpect(jsonPath("$.content[*].slug", hasItem("imac-g3")))
                .andExpect(jsonPath("$.content[*].slug", hasItem("ipod-1st-gen")));
    }

    @Test
    void combinesFamilyAndCpu() throws Exception {
        mvc.perform(get("/api/v1/models").param("family", "powerbook").param("cpu", "POWERPC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(8))
                .andExpect(jsonPath("$.content[*].family.slug", everyItem(is("powerbook"))))
                .andExpect(jsonPath("$.content[*].slug", hasItem("powerbook-g4-titanium")));
    }

    @Test
    void acceptsCpuInAnyCaseWithDashes() throws Exception {
        mvc.perform(get("/api/v1/models").param("cpu", "apple-silicon").param("size", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].cpuArchitecture", everyItem(is("APPLE_SILICON"))))
                .andExpect(jsonPath("$.content[*].slug", hasItem("ipad")));
    }

    @Test
    void searchesNameCaseInsensitively() throws Exception {
        mvc.perform(get("/api/v1/models").param("q", "CUBE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].slug").value("power-mac-g4-cube"));
    }

    @Test
    void searchesCpuNameAndSummary() throws Exception {
        mvc.perform(get("/api/v1/models").param("q", "68030"))
                .andExpect(jsonPath("$.content[*].slug", hasItem("macintosh-se-30")));
        mvc.perform(get("/api/v1/models").param("q", "magsafe"))
                .andExpect(jsonPath("$.content[*].slug", hasItem("macbook-pro-15-2006")));
    }

    @Test
    void treatsLikeWildcardsLiterally() throws Exception {
        mvc.perform(get("/api/v1/models").param("q", "%"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void combinesSearchWithYearFilter() throws Exception {
        mvc.perform(get("/api/v1/models").param("q", "imac").param("fromYear", "2006"))
                .andExpect(jsonPath("$.content[*].releaseYear", everyItem(greaterThanOrEqualTo(2006))))
                .andExpect(jsonPath("$.content[*].slug", hasItem("imac-24-m1")));
    }

    @Test
    void filtersByManufacturerCaseInsensitively() throws Exception {
        mvc.perform(get("/api/v1/models").param("manufacturer", "ibm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.content[*].manufacturer", everyItem(is("IBM"))))
                .andExpect(jsonPath("$.content[*].family.slug", everyItem(is("contemporaries"))));
        mvc.perform(get("/api/v1/models").param("manufacturer", "Apple").param("size", "1"))
                .andExpect(jsonPath("$.content[0].manufacturer").value("Apple"));
    }

    @Test
    void filtersBootableModels() throws Exception {
        mvc.perform(get("/api/v1/models").param("bootable", "true").param("size", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].emulator.url", everyItem(startsWith("https://"))))
                .andExpect(jsonPath("$.content[*].slug", hasItem("macintosh-se-30")))
                .andExpect(jsonPath("$.content[*].slug", not(hasItem("iphone"))));
    }

    @Test
    void returnsEmptyPageForUnknownFamily() throws Exception {
        mvc.perform(get("/api/v1/models").param("family", "toaster"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void rejectsInvertedYearRange() throws Exception {
        mvc.perform(get("/api/v1/models").param("fromYear", "2000").param("toYear", "1990"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.detail").value(containsString("fromYear (2000)")));
    }

    @Test
    void rejectsNonNumericYear() throws Exception {
        mvc.perform(get("/api/v1/models").param("year", "nineteen-eighty-four"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid parameter"))
                .andExpect(jsonPath("$.detail").value(containsString("parameter 'year'")));
    }

    @Test
    void rejectsUnknownCpuListingAllowedValues() throws Exception {
        mvc.perform(get("/api/v1/models").param("cpu", "Z80"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(containsString("Allowed values: MOS_6502, M68K")));
    }
}
