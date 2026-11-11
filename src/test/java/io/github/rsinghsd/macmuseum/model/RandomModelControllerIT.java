package io.github.rsinghsd.macmuseum.model;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class RandomModelControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    AppleModelRepository repository;

    @Test
    void returnsAModel() throws Exception {
        mvc.perform(get("/api/v1/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").isNotEmpty())
                .andExpect(jsonPath("$.family.slug").isNotEmpty());
    }

    @Test
    void returnsTheSameModelForTheSameDay() throws Exception {
        String first = slugFor("2026-11-11");
        for (int i = 0; i < 5; i++) {
            Assertions.assertThat(slugFor("2026-11-11")).isEqualTo(first);
        }
    }

    @Test
    void rejectsInvalidDay() throws Exception {
        mvc.perform(get("/api/v1/random").param("day", "yesterday"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(org.hamcrest.Matchers.containsString("'day'")));
    }

    @Test
    @Transactional
    void returnsNotFoundWhenCatalogIsEmpty() throws Exception {
        repository.deleteAll();

        mvc.perform(get("/api/v1/random"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.detail").value("The catalog is empty"));
    }

    private String slugFor(String day) throws Exception {
        String json = mvc.perform(get("/api/v1/random").param("day", day))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.slug");
    }
}
