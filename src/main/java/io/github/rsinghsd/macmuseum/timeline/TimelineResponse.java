package io.github.rsinghsd.macmuseum.timeline;

import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.util.List;

/** Apple's history as a list of CPU eras, oldest first. */
public record TimelineResponse(List<Era> eras) {

    /** One CPU era with the models released on it, in release order. */
    public record Era(
            CpuArchitecture architecture,
            String label,
            String description,
            int startYear,
            int endYear,
            int modelCount,
            List<Entry> models) {
    }

    /** A compact timeline entry; full details are at {@code /api/v1/models/{slug}}. */
    public record Entry(String slug, String name, int releaseYear, String familyName) {
    }
}
