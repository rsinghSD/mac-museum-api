package io.github.rsinghsd.macmuseum.common;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Exposes a random generator as a bean so tests can replace it with a deterministic one. */
@Configuration
public class RandomConfig {

    /**
     * {@link Random} lives in {@code java.base} and is thread-safe. Avoid
     * {@code RandomGenerator.getDefault()}: its algorithms live in the optional {@code jdk.random}
     * module, which slim JRE images (such as the Docker runtime image) do not ship.
     */
    @Bean
    RandomGenerator randomGenerator() {
        return new Random();
    }
}
