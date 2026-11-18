package io.github.rsinghsd.macmuseum.timeline;

import io.github.rsinghsd.macmuseum.model.AppleModelResponse;
import io.github.rsinghsd.macmuseum.model.AppleModelService;
import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Groups the catalog into CPU eras. Eras are ordered by the year their first model was released,
 * so overlapping eras (e.g. ARM devices during the PowerPC years) still read chronologically.
 */
@Service
public class TimelineService {

    private final AppleModelService modelService;

    public TimelineService(AppleModelService modelService) {
        this.modelService = modelService;
    }

    public TimelineResponse build() {
        return build(modelService.findAppleModelsChronological());
    }

    /** Pure grouping logic, separated from data access so it can be unit tested directly. */
    static TimelineResponse build(List<AppleModelResponse> models) {
        Map<CpuArchitecture, List<AppleModelResponse>> byArchitecture = new EnumMap<>(CpuArchitecture.class);
        models.stream()
                .sorted(Comparator.comparingInt(AppleModelResponse::releaseYear)
                        .thenComparing(AppleModelResponse::name))
                .forEach(m -> byArchitecture.computeIfAbsent(m.cpuArchitecture(), k -> new ArrayList<>()).add(m));

        List<TimelineResponse.Era> eras = byArchitecture.entrySet().stream()
                .map(e -> toEra(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingInt(TimelineResponse.Era::startYear)
                        .thenComparing(TimelineResponse.Era::architecture))
                .toList();
        return new TimelineResponse(eras);
    }

    private static TimelineResponse.Era toEra(CpuArchitecture architecture, List<AppleModelResponse> models) {
        List<TimelineResponse.Entry> entries = models.stream()
                .map(m -> new TimelineResponse.Entry(m.slug(), m.name(), m.releaseYear(), m.family().name()))
                .toList();
        return new TimelineResponse.Era(
                architecture,
                architecture.label(),
                architecture.description(),
                models.getFirst().releaseYear(),
                models.getLast().releaseYear(),
                models.size(),
                entries);
    }
}
