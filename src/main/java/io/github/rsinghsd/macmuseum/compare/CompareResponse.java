package io.github.rsinghsd.macmuseum.compare;

import io.github.rsinghsd.macmuseum.model.AppleModelResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Models side by side, plus how each one compares with the first (the baseline).
 */
public record CompareResponse(List<AppleModelResponse> models, List<Comparison> comparisons) {

    /**
     * Ratios are {@code other / baseline}, rounded to two decimals; {@code null} when a value is unknown.
     *
     * @param yearsApart         {@code other.releaseYear - baseline.releaseYear}
     * @param priceDifferenceUsd {@code other.launchPrice - baseline.launchPrice}
     */
    public record Comparison(
            String baselineSlug,
            String otherSlug,
            int yearsApart,
            Double clockSpeedRatio,
            Double ramRatio,
            BigDecimal priceDifferenceUsd) {
    }
}
