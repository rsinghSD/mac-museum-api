package io.github.rsinghsd.macmuseum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** A freely licensed photo of a model and the attribution its license requires. */
@Embeddable
public class ModelImage {

    @Column(name = "image_url", length = 500)
    private String url;

    @Column(name = "image_credit", length = 300)
    private String credit;

    @Column(name = "image_license", length = 80)
    private String license;

    @Column(name = "image_source_url", length = 500)
    private String sourceUrl;

    protected ModelImage() {
        // for JPA
    }

    public ModelImage(String url, String credit, String license, String sourceUrl) {
        this.url = url;
        this.credit = credit;
        this.license = license;
        this.sourceUrl = sourceUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getCredit() {
        return credit;
    }

    public String getLicense() {
        return license;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
