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
    void seedsAtLeastThirtyFiveModels() {
        assertThat(models.count()).isGreaterThanOrEqualTo(35);
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
