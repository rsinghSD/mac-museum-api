package io.github.rsinghsd.macmuseum.compare;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compare")
public class CompareController {

    private final CompareService service;

    public CompareController(CompareService service) {
        this.service = service;
    }

    /** {@code ?slugs=a,b} and {@code ?slugs=a&slugs=b} are both accepted. */
    @GetMapping
    public CompareResponse compare(@RequestParam List<String> slugs) {
        return service.compare(slugs);
    }
}
