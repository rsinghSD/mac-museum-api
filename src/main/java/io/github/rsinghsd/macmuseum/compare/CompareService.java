package io.github.rsinghsd.macmuseum.compare;

import io.github.rsinghsd.macmuseum.common.BadRequestException;
import io.github.rsinghsd.macmuseum.model.AppleModelResponse;
import io.github.rsinghsd.macmuseum.model.AppleModelService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompareService {

    static final int MIN_MODELS = 2;
    static final int MAX_MODELS = 4;

    private final AppleModelService modelService;

    public CompareService(AppleModelService modelService) {
        this.modelService = modelService;
    }

    public CompareResponse compare(List<String> rawSlugs) {
        List<String> slugs = rawSlugs.stream().map(String::trim).filter(s -> !s.isEmpty()).toList();
        if (slugs.size() < MIN_MODELS || slugs.size() > MAX_MODELS) {
            throw new BadRequestException("Provide between " + MIN_MODELS + " and " + MAX_MODELS
                    + " slugs to compare, got " + slugs.size());
        }
        if (new HashSet<>(slugs).size() != slugs.size()) {
            throw new BadRequestException("Each slug may appear only once");
        }

        List<AppleModelResponse> models = modelService.findBySlugs(slugs);
        AppleModelResponse baseline = models.getFirst();
        List<CompareResponse.Comparison> comparisons = models.stream()
                .skip(1)
                .map(other -> compare(baseline, other))
                .toList();
        return new CompareResponse(models, comparisons);
    }

    static CompareResponse.Comparison compare(AppleModelResponse baseline, AppleModelResponse other) {
        return new CompareResponse.Comparison(
                baseline.slug(),
                other.slug(),
                other.releaseYear() - baseline.releaseYear(),
                ratio(other.clockSpeedMhz(), baseline.clockSpeedMhz()),
                ratio(other.baseRamKb(), baseline.baseRamKb()),
                baseline.launchPriceUsd() == null || other.launchPriceUsd() == null
                        ? null
                        : other.launchPriceUsd().subtract(baseline.launchPriceUsd()));
    }

    static Double ratio(Integer value, Integer baseline) {
        if (value == null || baseline == null || baseline == 0) {
            return null;
        }
        return BigDecimal.valueOf(value)
                .divide(BigDecimal.valueOf(baseline), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
