package io.github.rsinghsd.macmuseum;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.rsinghsd.macmuseum.family.ProductFamilyRepository;
import io.github.rsinghsd.macmuseum.model.AppleModel;
import io.github.rsinghsd.macmuseum.model.AppleModelRepository;
import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/** Verifies the Flyway seed migrations load a consistent catalog. */
@DataJpaTest
class SeedDataIT {

    @Autowired
    AppleModelRepository models;

    @Autowired
    ProductFamilyRepository families;

    @Test
    void seedsAtLeastOneHundredFiftyModels() {
        assertThat(models.count()).isGreaterThanOrEqualTo(150);
    }

    @Test
    void keepsContemporariesSeparateFromApple() {
        List<AppleModel> all = models.findAll();
        assertThat(all).filteredOn(m -> !m.getManufacturer().equals("Apple"))
                .isNotEmpty()
                .allSatisfy(m -> assertThat(m.getFamily().getSlug()).isEqualTo("contemporaries"));
        assertThat(all).filteredOn(m -> m.getFamily().getSlug().equals("contemporaries"))
                .allSatisfy(m -> assertThat(m.getManufacturer()).isNotEqualTo("Apple"));
    }

    @Test
    void usesOnlyFreelyLicensedHttpsImagesWithCredit() {
        assertThat(models.findAll()).filteredOn(m -> m.getImage() != null && m.getImage().getUrl() != null)
                .hasSizeGreaterThanOrEqualTo(100)
                .allSatisfy(m -> {
                    assertThat(m.getImage().getUrl()).matches("https://(upload|thumb)\\.wikimedia\\.org/.+");
                    assertThat(m.getImage().getSourceUrl()).startsWith("https://commons.wikimedia.org/");
                    assertThat(m.getImage().getCredit()).isNotBlank();
                    assertThat(m.getImage().getLicense()).matches("(?i)CC0|Public domain|CC BY(-SA)? [0-9.]+( fr)?");
                });
    }

    @Test
    void embedsOnlyInfiniteMacAndLabelsApproximations() {
        assertThat(models.findAll()).filteredOn(m -> m.getEmulator() != null && m.getEmulator().getUrl() != null)
                .hasSizeGreaterThanOrEqualTo(70)
                .allSatisfy(m -> {
                    assertThat(m.getEmulator().getUrl()).startsWith("https://");
                    if (m.getEmulator().getEmbedUrl() != null) {
                        assertThat(m.getEmulator().getEmbedUrl()).startsWith("https://infinitemac.org/embed?disk=");
                    }
                    if (!m.getEmulator().isExactMachine()) {
                        assertThat(m.getEmulator().getLabel()).contains("closest machine");
                    }
                });
    }

    @Test
    void hasNoReleaseYearInTheFuture() {
        assertThat(models.findAll()).allSatisfy(m ->
                assertThat(m.getReleaseYear()).isBetween(1976, java.time.Year.now().getValue()));
    }

    @Test
    void everyFamilyHasAtLeastOneModel() {
        List<AppleModel> all = models.findAll();
        Set<String> usedFamilies = all.stream().map(m -> m.getFamily().getSlug()).collect(Collectors.toSet());
        assertThat(families.findAll()).allSatisfy(f -> assertThat(usedFamilies).contains(f.getSlug()));
    }

    @Test
    void coversEveryCpuArchitecture() {
        Set<CpuArchitecture> used = models.findAll().stream()
                .map(AppleModel::getCpuArchitecture)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(CpuArchitecture.class)));
        assertThat(used).containsExactlyInAnyOrder(CpuArchitecture.values());
    }

    @Test
    void startsWithTheAppleI() {
        AppleModel appleI = models.findBySlug("apple-i").orElseThrow();
        assertThat(appleI.getReleaseYear()).isEqualTo(1976);
        assertThat(appleI.getCpuArchitecture()).isEqualTo(CpuArchitecture.MOS_6502);
    }
}
