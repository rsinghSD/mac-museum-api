package io.github.rsinghsd.macmuseum.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/random")
public class RandomModelController {

    private final AppleModelService service;

    public RandomModelController(AppleModelService service) {
        this.service = service;
    }

    @GetMapping
    public AppleModelResponse random(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return service.findRandom(day);
    }
}
