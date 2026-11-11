package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.ApiErrorResponses;
import io.github.rsinghsd.macmuseum.common.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/random")
@Tag(name = OpenApiConfig.TAG_DISCOVERY)
public class RandomModelController {

    private final AppleModelService service;

    public RandomModelController(AppleModelService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Mac of the day",
            description = "Returns a random model. Pass `day` to get a stable pick for that date.")
    @ApiErrorResponses.BadRequest
    @ApiErrorResponses.NotFound
    public AppleModelResponse random(
            @Parameter(description = "ISO date for a deterministic pick", example = "2026-11-11")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return service.findRandom(day);
    }
}
