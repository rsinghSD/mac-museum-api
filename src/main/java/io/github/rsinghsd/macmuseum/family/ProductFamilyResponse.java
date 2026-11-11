package io.github.rsinghsd.macmuseum.family;

/** Public representation of a product family, including how many models it contains. */
public record ProductFamilyResponse(String slug, String name, String description, long modelCount) {
}
