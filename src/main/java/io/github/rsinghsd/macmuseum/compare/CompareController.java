package io.github.rsinghsd.macmuseum.compare;

import io.github.rsinghsd.macmuseum.common.ApiErrorResponses;
import io.github.rsinghsd.macmuseum.common.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compare")
@Tag(name = OpenApiConfig.TAG_DISCOVERY)
public class CompareController {

    private final CompareService service;

    public CompareController(CompareService service) {
        this.service = service;
    }

    /** {@code ?slugs=a,b} and {@code ?slugs=a&slugs=b} are both accepted. */
    @GetMapping
    @Operation(summary = "Compare models side by side",
            description = "2 to 4 unique slugs. Each model after the first is compared with the first.")
    @ApiErrorResponses.BadRequest
    @ApiErrorResponses.NotFound
    public CompareResponse compare(
            @Parameter(description = "Comma-separated slugs", example = "macintosh-128k,imac-g3")
            @RequestParam List<String> slugs) {
        return service.compare(slugs);
    }
}
