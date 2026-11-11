package io.github.rsinghsd.macmuseum.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** Body of POST and PUT requests for a model. */
public record AppleModelRequest(
        @NotBlank
        @Size(max = 80)
        @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "must be lowercase kebab-case, e.g. macintosh-128k")
        String slug,

        @NotBlank @Size(max = 120)
        String name,

        @NotNull @Min(1976) @Max(2100)
        Integer releaseYear,

        @DecimalMin(value = "0.00") @Digits(integer = 8, fraction = 2)
        BigDecimal launchPriceUsd,

        @NotBlank
        String familySlug,

        @NotNull
        CpuArchitecture cpuArchitecture,

        @NotBlank @Size(max = 100)
        String cpuName,

        @Positive
        Integer clockSpeedMhz,

        @Positive
        Integer baseRamKb,

        @NotBlank @Size(max = 2000)
        String summary) {
}
