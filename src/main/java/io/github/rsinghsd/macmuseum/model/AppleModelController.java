package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.PageResponse;
import jakarta.validation.Valid;
import java.net.URI;
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
public class AppleModelController {

    private final AppleModelService service;

    public AppleModelController(AppleModelService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<AppleModelResponse> list(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) CpuArchitecture cpu,
            @RequestParam(required = false) String q,
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "releaseYear", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "name", direction = Sort.Direction.ASC)})
            Pageable pageable) {
        return service.findAll(new AppleModelFilter(year, fromYear, toYear, family, cpu, q), pageable);
    }

    @GetMapping("/{slug}")
    public AppleModelResponse get(@PathVariable String slug) {
        return service.findBySlug(slug);
    }

    @PostMapping
    public ResponseEntity<AppleModelResponse> create(@Valid @RequestBody AppleModelRequest request) {
        AppleModelResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{slug}").buildAndExpand(created.slug()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{slug}")
    public AppleModelResponse update(@PathVariable String slug, @Valid @RequestBody AppleModelRequest request) {
        return service.update(slug, request);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        service.delete(slug);
        return ResponseEntity.noContent().build();
    }
}
