package io.github.rsinghsd.macmuseum.family;

import io.github.rsinghsd.macmuseum.common.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/families")
@Tag(name = OpenApiConfig.TAG_FAMILIES)
public class ProductFamilyController {

    private final ProductFamilyService service;

    public ProductFamilyController(ProductFamilyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List product families", description = "Sorted by name, with the number of models in each.")
    public List<ProductFamilyResponse> list() {
        return service.findAll();
    }
}
