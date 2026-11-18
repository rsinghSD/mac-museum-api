package io.github.rsinghsd.macmuseum.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * In-browser emulator for a model.
 *
 * @param provider     emulator name, e.g. "Infinite Mac"
 * @param label        what boots, e.g. "System 6.0.8 on an emulated Mac SE/30"
 * @param url          opens the emulator on the provider's site (https)
 * @param embedUrl     URL the frontend may show in an iframe; only Infinite Mac's /embed is accepted,
 *                     so curators cannot make the site frame arbitrary pages
 * @param exactMachine whether the emulated machine is this exact model
 */
public record EmulatorDto(
        @NotBlank @Size(max = 60)
        String provider,

        @NotBlank @Size(max = 200)
        String label,

        @NotBlank @Size(max = 500) @Pattern(regexp = "^https://\\S+$", message = "must be an https URL")
        String url,

        @Size(max = 500)
        @Pattern(regexp = "^https://infinitemac\\.org/embed\\?\\S+$",
                message = "must be an Infinite Mac embed URL (https://infinitemac.org/embed?...)")
        String embedUrl,

        boolean exactMachine) {

    static EmulatorDto from(ModelEmulator emulator) {
        return emulator == null || emulator.getUrl() == null ? null
                : new EmulatorDto(emulator.getProvider(), emulator.getLabel(), emulator.getUrl(),
                        emulator.getEmbedUrl(), emulator.isExactMachine());
    }

    ModelEmulator toEmbeddable() {
        return new ModelEmulator(provider, label, url, embedUrl, exactMachine);
    }
}
