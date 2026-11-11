package io.github.rsinghsd.macmuseum.timeline;

import io.github.rsinghsd.macmuseum.common.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timeline")
@Tag(name = OpenApiConfig.TAG_DISCOVERY)
public class TimelineController {

    private final TimelineService service;

    public TimelineController(TimelineService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Timeline by CPU era",
            description = "All models grouped by CPU architecture; eras are ordered by their first release.")
    public TimelineResponse timeline() {
        return service.build();
    }
}
