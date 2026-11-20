package io.github.rsinghsd.macmuseum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** A link to an in-browser emulator that boots this model, or the closest machine available. */
@Embeddable
public class ModelEmulator {

    @Column(name = "emulator_provider", length = 60)
    private String provider;

    @Column(name = "emulator_label", length = 200)
    private String label;

    /** Opens the emulator on the provider's site. */
    @Column(name = "emulator_url", length = 500)
    private String url;

    /** URL that may be shown in an iframe; {@code null} when the provider has no embed mode. */
    @Column(name = "emulator_embed_url", length = 500)
    private String embedUrl;

    /** {@code true} when the emulated machine is this exact model, not a close relative. */
    @Column(name = "emulator_exact_machine")
    private Boolean exactMachine;

    protected ModelEmulator() {
        // for JPA
    }

    public ModelEmulator(String provider, String label, String url, String embedUrl, boolean exactMachine) {
        this.provider = provider;
        this.label = label;
        this.url = url;
        this.embedUrl = embedUrl;
        this.exactMachine = exactMachine;
    }

    public String getProvider() {
        return provider;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public boolean isExactMachine() {
        return Boolean.TRUE.equals(exactMachine);
    }
}
