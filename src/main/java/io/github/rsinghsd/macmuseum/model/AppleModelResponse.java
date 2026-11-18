package io.github.rsinghsd.macmuseum.model;

import java.math.BigDecimal;

/** Public representation of a model. Database ids are intentionally not exposed. */
public record AppleModelResponse(
        String slug,
        String name,
        int releaseYear,
        BigDecimal launchPriceUsd,
        FamilyRef family,
        CpuArchitecture cpuArchitecture,
        String cpuName,
        Integer clockSpeedMhz,
        Integer baseRamKb,
        String summary,
        String manufacturer) {

    /** Compact reference to the model's product family. */
    public record FamilyRef(String slug, String name) {
    }
}
