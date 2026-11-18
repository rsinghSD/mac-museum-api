package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.ApiErrorResponses;
import io.github.rsinghsd.macmuseum.common.OpenApiConfig;
import io.github.rsinghsd.macmuseum.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/models")
@Tag(name = OpenApiConfig.TAG_MODELS)
public class AppleModelController {

    private final AppleModelService service;

    public AppleModelController(AppleModelService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List models",
            description = "Paginated list of models. All filters are optional and combined with AND. "
                    + "Sortable by slug, name, releaseYear, launchPriceUsd, clockSpeedMhz and baseRamKb. "
                    + "Default sort: releaseYear,name. Maximum page size: 100.")
    @ApiErrorResponses.BadRequest
    public PageResponse<AppleModelResponse> list(
            @Parameter(description = "Exact release year", example = "1984")
            @RequestParam(required = false) Integer year,
            @Parameter(description = "Inclusive lower bound of the release year", example = "1990")
            @RequestParam(required = false) Integer fromYear,
            @Parameter(description = "Inclusive upper bound of the release year", example = "1999")
            @RequestParam(required = false) Integer toYear,
            @Parameter(description = "Product family slug", example = "powerbook")
            @RequestParam(required = false) String family,
            @Parameter(description = "CPU architecture (case-insensitive)", example = "POWERPC")
            @RequestParam(required = false) CpuArchitecture cpu,
            @Parameter(description = "Case-insensitive text search in name, CPU name and summary", example = "cube")
            @RequestParam(required = false) String q,
            @Parameter(description = "Manufacturer, case-insensitive. Apple, or e.g. IBM for OS/2-era contemporaries",
                    example = "Apple")
            @RequestParam(required = false) String manufacturer,
            @Parameter(description = "true: only models you can boot in an in-browser emulator", example = "true")
            @RequestParam(required = false) Boolean bootable,
            @ParameterObject
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "releaseYear", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC)})
            Pageable pageable) {
        return service.findAll(new AppleModelFilter(year, fromYear, toYear, family, cpu, q, manufacturer, bootable), pageable);
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a model by slug")
    @ApiErrorResponses.NotFound
    public AppleModelResponse get(@Parameter(example = "macintosh-128k") @PathVariable String slug) {
        return service.findBySlug(slug);
    }

    @PostMapping
    @ApiErrorResponses.RequiresApiKey
    @Operation(summary = "Create a model")
    @ApiResponse(responseCode = "201", description = "Created; the Location header points to the new model")
    @ApiErrorResponses.BadRequest
    @ApiErrorResponses.Conflict
    public ResponseEntity<AppleModelResponse> create(@Valid @RequestBody AppleModelRequest request) {
        AppleModelResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{slug}").buildAndExpand(created.slug()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{slug}")
    @ApiErrorResponses.RequiresApiKey
    @Operation(summary = "Replace a model",
            description = "Replaces every field. The slug in the body may differ from the path to rename the model.")
    @ApiErrorResponses.BadRequest
    @ApiErrorResponses.NotFound
    @ApiErrorResponses.Conflict
    public AppleModelResponse update(@PathVariable String slug, @Valid @RequestBody AppleModelRequest request) {
        return service.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    @ApiErrorResponses.RequiresApiKey
    @Operation(summary = "Delete a model")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiErrorResponses.NotFound
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        service.delete(slug);
        return ResponseEntity.noContent().build();
    }
}
