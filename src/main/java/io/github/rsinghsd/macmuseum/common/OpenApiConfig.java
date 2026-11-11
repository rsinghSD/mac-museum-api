package io.github.rsinghsd.macmuseum.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenAPI metadata shown in Swagger UI at {@code /swagger-ui.html}. */
@Configuration
public class OpenApiConfig {

    public static final String TAG_MODELS = "Models";
    public static final String TAG_DISCOVERY = "Discovery";
    public static final String TAG_FAMILIES = "Families";
    public static final String API_KEY_SCHEME = "apiKey";

    @Bean
    OpenAPI macMuseumOpenApi(@Value("${app.version:dev}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("Mac Museum API")
                        .version(version)
                        .description("""
                                A catalog of Apple hardware history, from the Apple I (1976) to Apple silicon.
                                Errors use RFC 7807 problem details (`application/problem+json`).
                                Not affiliated with Apple Inc.""")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .components(new Components().addSecuritySchemes(API_KEY_SCHEME, new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("X-API-Key")
                        .description("Required for POST, PUT and DELETE when the server has WRITE_API_KEY set.")))
                .tags(List.of(
                        new Tag().name(TAG_MODELS).description("Browse, search and curate models"),
                        new Tag().name(TAG_DISCOVERY).description("Timeline, comparison and Mac of the day"),
                        new Tag().name(TAG_FAMILIES).description("Product lines")));
    }
}
