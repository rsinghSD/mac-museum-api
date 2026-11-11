package io.github.rsinghsd.macmuseum.model;

/**
 * Processor architecture families used across Apple's history, in chronological order of adoption.
 * {@link #ARM} covers third-party ARM chips (Newton, early iPod and iPhone);
 * {@link #APPLE_SILICON} covers Apple-designed ARM systems on a chip (A-series and M-series).
 */
public enum CpuArchitecture {
    MOS_6502,
    M68K,
    POWERPC,
    INTEL_X86,
    ARM,
    APPLE_SILICON
}
