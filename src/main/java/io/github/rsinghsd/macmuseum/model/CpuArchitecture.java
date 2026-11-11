package io.github.rsinghsd.macmuseum.model;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Processor architecture families used across Apple's history, in chronological order of adoption.
 * {@link #ARM} covers third-party ARM chips (Newton, early iPod and iPhone);
 * {@link #APPLE_SILICON} covers Apple-designed ARM systems on a chip (A-series and M-series).
 */
public enum CpuArchitecture {
    MOS_6502("MOS 6502", "The 8-bit chip behind the Apple I and Apple II."),
    M68K("Motorola 68k", "The 68000 family powered the Lisa and the first decade of the Macintosh."),
    ARM("ARM (licensed)", "Low-power ARM chips from partners ran the Newton, iPod and first iPhone."),
    POWERPC("PowerPC", "Developed with IBM and Motorola, PowerPC drove Macs from 1994 to 2006."),
    INTEL_X86("Intel x86", "The 2006 switch to Intel brought faster laptops and Windows compatibility."),
    APPLE_SILICON("Apple silicon", "Apple's own chips, first in the iPad and iPhone, then in every Mac from 2020.");

    private final String label;
    private final String description;

    CpuArchitecture(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }

    /** Case-insensitive lookup that also accepts dashes, e.g. {@code apple-silicon}. */
    public static CpuArchitecture fromString(String value) {
        String normalised = value.trim().replace('-', '_').toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(cpu -> cpu.name().equals(normalised))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown CPU architecture '" + value + "'"));
    }

    /** Comma-separated list of the constant names, for error messages. */
    public static String allowedValues() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.joining(", "));
    }
}
