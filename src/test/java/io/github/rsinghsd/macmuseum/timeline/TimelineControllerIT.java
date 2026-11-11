package io.github.rsinghsd.macmuseum.timeline;

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
                .andExpect(jsonPath("$.eras[5].endYear").value(2022));
    }
}
