package io.github.rsinghsd.macmuseum;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** The static frontend is served by Spring Boot from {@code classpath:/static}. */
@SpringBootTest
@AutoConfigureMockMvc
class StaticFrontendIT {

    @Autowired
    MockMvc mvc;

    @Test
    void rootForwardsToIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("index.html"));
    }

    @Test
    void indexPageLinksAssetsAndSwagger() throws Exception {
        mvc.perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<title>Mac Museum</title>")))
                .andExpect(content().string(containsString("css/platinum.css")))
                .andExpect(content().string(containsString("js/app.js")))
                .andExpect(content().string(containsString("swagger-ui.html")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/platinum.css", "/js/app.js", "/js/api.js", "/js/format.js", "/js/dom.js",
            "/img/icon.svg"})
    void servesStaticAssets(String path) throws Exception {
        mvc.perform(get(path)).andExpect(status().isOk());
    }

    @Test
    void unknownStaticFileReturnsProblemDetail() throws Exception {
        mvc.perform(get("/does-not-exist.png"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"));
    }
}
