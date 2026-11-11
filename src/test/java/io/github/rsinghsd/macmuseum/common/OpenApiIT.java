package io.github.rsinghsd.macmuseum.common;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiIT {

    @Autowired
    MockMvc mvc;

    @Test
    void publishesOpenApiDocumentForEveryEndpoint() throws Exception {
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Mac Museum API"))
                .andExpect(jsonPath("$.info.license.name").value("MIT"))
                .andExpect(jsonPath("$.paths['/api/v1/models'].get.parameters[?(@.name == 'cpu')]").exists())
                .andExpect(jsonPath("$.paths['/api/v1/models'].post").exists())
                .andExpect(jsonPath("$.paths['/api/v1/models/{slug}'].delete.responses['204']").exists())
                .andExpect(jsonPath("$.paths['/api/v1/families'].get").exists())
                .andExpect(jsonPath("$.paths['/api/v1/timeline'].get").exists())
                .andExpect(jsonPath("$.paths['/api/v1/compare'].get.responses['404']").exists())
                .andExpect(jsonPath("$.paths['/api/v1/random'].get").exists());
    }

    @Test
    void servesSwaggerUi() throws Exception {
        mvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("swagger-ui/index.html")));
    }

    @Test
    void exposesHealthEndpoint() throws Exception {
        mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
