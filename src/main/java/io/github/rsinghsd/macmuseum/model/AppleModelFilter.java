package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.BadRequestException;

/**
 * Optional criteria for listing models. All non-null criteria are combined with AND.
 *
 * @param year     exact release year
 * @param fromYear inclusive lower bound of the release year
 * @param toYear   inclusive upper bound of the release year
 * @param family   product family slug
 * @param cpu      CPU architecture
 * @param q        case-insensitive text matched against name, CPU name and summary
 */
public record AppleModelFilter(
        Integer year,
        Integer fromYear,
        Integer toYear,
        String family,
        CpuArchitecture cpu,
        String q) {

    public static final AppleModelFilter NONE = new AppleModelFilter(null, null, null, null, null, null);

    /** Rejects contradictory criteria. */
    public void validate() {
        if (fromYear != null && toYear != null && fromYear > toYear) {
            throw new BadRequestException(
                    "fromYear (" + fromYear + ") must be less than or equal to toYear (" + toYear + ")");
        }
    }
}
