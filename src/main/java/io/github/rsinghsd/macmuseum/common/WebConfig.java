package io.github.rsinghsd.macmuseum.common;

import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        // Lets clients write ?cpu=powerpc or ?cpu=apple-silicon, not only the exact enum name.
        registry.addConverter(String.class, CpuArchitecture.class,
                (Converter<String, CpuArchitecture>) CpuArchitecture::fromString);
    }
}
