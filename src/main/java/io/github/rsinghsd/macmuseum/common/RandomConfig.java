package io.github.rsinghsd.macmuseum.common;

import java.util.random.RandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Exposes a random generator as a bean so tests can replace it with a deterministic one. */
@Configuration
public class RandomConfig {

    @Bean
    RandomGenerator randomGenerator() {
        return RandomGenerator.getDefault();
    }
}
