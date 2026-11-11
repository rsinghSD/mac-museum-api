package io.github.rsinghsd.macmuseum.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.random.RandomGenerator;
import org.junit.jupiter.api.Test;

class RandomConfigTest {

    @Test
    void generatorComesFromJavaBaseSoItWorksOnSlimJres() {
        RandomGenerator generator = new RandomConfig().randomGenerator();

        // RandomGenerator.getDefault() needs the optional jdk.random module and crashed in Docker.
        assertThat(generator.getClass().getModule().getName()).isEqualTo("java.base");
        assertThat(generator.nextInt(10)).isBetween(0, 9);
    }
}
