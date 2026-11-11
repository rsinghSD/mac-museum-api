package io.github.rsinghsd.macmuseum.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CpuArchitectureTest {

    @Test
    void parsesNamesCaseInsensitivelyAndWithDashes() {
        assertThat(CpuArchitecture.fromString("powerpc")).isEqualTo(CpuArchitecture.POWERPC);
        assertThat(CpuArchitecture.fromString(" Apple-Silicon ")).isEqualTo(CpuArchitecture.APPLE_SILICON);
        assertThat(CpuArchitecture.fromString("intel_x86")).isEqualTo(CpuArchitecture.INTEL_X86);
    }

    @Test
    void rejectsUnknownValues() {
        assertThatThrownBy(() -> CpuArchitecture.fromString("z80"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("z80");
    }

    @Test
    void listsAllowedValuesInDeclarationOrder() {
        assertThat(CpuArchitecture.allowedValues())
                .isEqualTo("MOS_6502, M68K, ARM, POWERPC, INTEL_X86, APPLE_SILICON");
    }

    @Test
    void escapesLikeWildcards() {
        assertThat(AppleModelSpecifications.escapeLike("100%_\\")).isEqualTo("100\\%\\_\\\\");
    }
}
