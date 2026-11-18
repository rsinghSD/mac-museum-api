package io.github.rsinghsd.macmuseum.timeline;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TimelineControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void returnsSixErasInChronologicalOrder() throws Exception {
        mvc.perform(get("/api/v1/timeline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eras.length()").value(6))
                .andExpect(jsonPath("$.eras[0].architecture").value("MOS_6502"))
                .andExpect(jsonPath("$.eras[0].startYear").value(1976))
                .andExpect(jsonPath("$.eras[0].models[0].slug").value("apple-i"))
                .andExpect(jsonPath("$.eras[1].architecture").value("M68K"))
                .andExpect(jsonPath("$.eras[2].architecture").value("ARM"))
                .andExpect(jsonPath("$.eras[3].architecture").value("POWERPC"))
                .andExpect(jsonPath("$.eras[3].label").value("PowerPC"))
                .andExpect(jsonPath("$.eras[4].architecture").value("INTEL_X86"))
                .andExpect(jsonPath("$.eras[5].architecture").value("APPLE_SILICON"))
                .andExpect(jsonPath("$.eras[4].startYear").value(2006))
                .andExpect(jsonPath("$.eras[5].endYear").value(2026));
    }

    @Test
    void leavesOutNonAppleContemporaries() throws Exception {
        // The 1987 IBM PS/2 Model 80 would otherwise start the x86 era 19 years early.
        mvc.perform(get("/api/v1/timeline"))
                .andExpect(jsonPath("$.eras[*].models[*].slug", not(hasItem(startsWith("ibm-")))));
    }
}
