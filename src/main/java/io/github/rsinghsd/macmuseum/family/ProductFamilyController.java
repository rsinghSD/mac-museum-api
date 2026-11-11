package io.github.rsinghsd.macmuseum.family;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/families")
public class ProductFamilyController {

    private final ProductFamilyService service;

    public ProductFamilyController(ProductFamilyService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductFamilyResponse> list() {
        return service.findAll();
    }
}
