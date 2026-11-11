package io.github.rsinghsd.macmuseum.compare;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CompareControllerIT {

    private static final String PROBLEM_JSON = "application/problem+json";

    @Autowired
    MockMvc mvc;

    @Test
    void comparesModelsInRequestedOrder() throws Exception {
        mvc.perform(get("/api/v1/compare").param("slugs", "macbook-air-m1,macintosh-128k"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.models.length()").value(2))
                .andExpect(jsonPath("$.models[0].slug").value("macbook-air-m1"))
                .andExpect(jsonPath("$.models[1].slug").value("macintosh-128k"))
                .andExpect(jsonPath("$.comparisons[0].baselineSlug").value("macbook-air-m1"))
                .andExpect(jsonPath("$.comparisons[0].yearsApart").value(-36))
                .andExpect(jsonPath("$.comparisons[0].priceDifferenceUsd").value(1496.00));
    }

    @Test
    void acceptsRepeatedParameterForUpToFourModels() throws Exception {
        mvc.perform(get("/api/v1/compare")
                        .param("slugs", "apple-i").param("slugs", "imac-g3")
                        .param("slugs", "iphone").param("slugs", "macbook-air-m2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comparisons.length()").value(3));
    }

    @Test
    void returnsNotFoundNamingTheMissingSlug() throws Exception {
        mvc.perform(get("/api/v1/compare").param("slugs", "imac-g3,hackintosh"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("No model found with slug 'hackintosh'"));
    }

    @Test
    void rejectsSingleSlug() throws Exception {
        mvc.perform(get("/api/v1/compare").param("slugs", "imac-g3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(PROBLEM_JSON));
    }

    @Test
    void rejectsTooManySlugs() throws Exception {
        mvc.perform(get("/api/v1/compare").param("slugs", "apple-i,apple-ii,lisa,iphone,ipad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsMissingParameter() throws Exception {
        mvc.perform(get("/api/v1/compare"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(PROBLEM_JSON));
    }
}
