package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.family.ProductFamily;

/** Explicit mapping between {@link AppleModel} entities and DTOs. */
final class AppleModelMapper {

    private AppleModelMapper() {
    }

    static AppleModelResponse toResponse(AppleModel model) {
        ProductFamily family = model.getFamily();
        return new AppleModelResponse(
                model.getSlug(),
                model.getName(),
                model.getReleaseYear(),
                model.getLaunchPriceUsd(),
                new AppleModelResponse.FamilyRef(family.getSlug(), family.getName()),
                model.getCpuArchitecture(),
                model.getCpuName(),
                model.getClockSpeedMhz(),
                model.getBaseRamKb(),
                model.getSummary(),
                model.getManufacturer(),
                ImageDto.from(model.getImage()),
                EmulatorDto.from(model.getEmulator()));
    }

    static AppleModel toEntity(AppleModelRequest request, ProductFamily family) {
        AppleModel model = new AppleModel(request.slug(), request.name(), request.releaseYear(), family,
                request.cpuArchitecture(), request.cpuName(), request.summary());
        model.setLaunchPriceUsd(request.launchPriceUsd());
        model.setClockSpeedMhz(request.clockSpeedMhz());
        model.setBaseRamKb(request.baseRamKb());
        model.setManufacturer(manufacturerOrDefault(request));
        applyMedia(model, request);
        return model;
    }

    static void update(AppleModel model, AppleModelRequest request, ProductFamily family) {
        model.setSlug(request.slug());
        model.setName(request.name());
        model.setReleaseYear(request.releaseYear());
        model.setLaunchPriceUsd(request.launchPriceUsd());
        model.setFamily(family);
        model.setCpuArchitecture(request.cpuArchitecture());
        model.setCpuName(request.cpuName());
        model.setClockSpeedMhz(request.clockSpeedMhz());
        model.setBaseRamKb(request.baseRamKb());
        model.setSummary(request.summary());
        model.setManufacturer(manufacturerOrDefault(request));
        applyMedia(model, request);
    }

    private static void applyMedia(AppleModel model, AppleModelRequest request) {
        model.setImage(request.image() == null ? null : request.image().toEmbeddable());
        model.setEmulator(request.emulator() == null ? null : request.emulator().toEmbeddable());
    }

    private static String manufacturerOrDefault(AppleModelRequest request) {
        String manufacturer = request.manufacturer();
        return manufacturer == null || manufacturer.isBlank() ? AppleModel.APPLE : manufacturer.trim();
    }
}
