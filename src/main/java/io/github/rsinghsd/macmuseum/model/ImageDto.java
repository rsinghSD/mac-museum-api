package io.github.rsinghsd.macmuseum.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Photo of a model with the attribution its license requires.
 *
 * @param url       image URL (https)
 * @param credit    author or rights holder, shown next to the photo
 * @param license   license name, e.g. "CC BY-SA 4.0"
 * @param sourceUrl page describing the image and its license
 */
public record ImageDto(
        @NotBlank @Size(max = 500) @Pattern(regexp = "^https://\\S+$", message = "must be an https URL")
        String url,

        @NotBlank @Size(max = 300)
        String credit,

        @NotBlank @Size(max = 80)
        String license,

        @Size(max = 500) @Pattern(regexp = "^https://\\S+$", message = "must be an https URL")
        String sourceUrl) {

    static ImageDto from(ModelImage image) {
        return image == null || image.getUrl() == null ? null
                : new ImageDto(image.getUrl(), image.getCredit(), image.getLicense(), image.getSourceUrl());
    }

    ModelImage toEmbeddable() {
        return new ModelImage(url, credit, license, sourceUrl);
    }
}
