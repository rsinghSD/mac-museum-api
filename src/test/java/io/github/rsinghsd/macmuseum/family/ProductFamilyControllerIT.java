package io.github.rsinghsd.macmuseum.family;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductFamilyControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void listsFamiliesSortedByNameWithModelCounts() throws Exception {
        mvc.perform(get("/api/v1/families"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(16))
                .andExpect(jsonPath("$[0].slug").value("apple-i-ii"))
                .andExpect(jsonPath("$[0].modelCount").value(3))
                .andExpect(jsonPath("$[*].slug", hasItem("macintosh")))
                .andExpect(jsonPath("$[?(@.slug == 'macintosh')].modelCount").value(
                        hasItem(greaterThanOrEqualTo(10))));
    }

    @Test
    void doesNotExposeDatabaseIds() throws Exception {
        mvc.perform(get("/api/v1/families"))
                .andExpect(jsonPath("$[0].id").doesNotExist());
    }
}
