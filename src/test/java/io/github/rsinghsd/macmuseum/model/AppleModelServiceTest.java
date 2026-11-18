package io.github.rsinghsd.macmuseum.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.github.rsinghsd.macmuseum.common.BadRequestException;
import io.github.rsinghsd.macmuseum.common.DuplicateResourceException;
import io.github.rsinghsd.macmuseum.common.ResourceNotFoundException;
import io.github.rsinghsd.macmuseum.family.ProductFamily;
import io.github.rsinghsd.macmuseum.family.ProductFamilyService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class AppleModelServiceTest {

    private static final ProductFamily MACINTOSH = new ProductFamily("macintosh", "Macintosh", "Compact Macs");

    @Mock
    AppleModelRepository repository;

    @Mock
    ProductFamilyService familyService;

    @Mock
    RandomGenerator random;

    @InjectMocks
    AppleModelService service;

    @Test
    void findBySlugMapsEntityToResponse() {
        when(repository.findBySlug("macintosh-128k")).thenReturn(Optional.of(mac128k()));

        AppleModelResponse response = service.findBySlug("macintosh-128k");

        assertThat(response.name()).isEqualTo("Macintosh 128K");
        assertThat(response.family().slug()).isEqualTo("macintosh");
        assertThat(response.launchPriceUsd()).isEqualByComparingTo("2495");
        assertThat(response.baseRamKb()).isEqualTo(128);
    }

    @Test
    void findBySlugThrowsWhenUnknown() {
        when(repository.findBySlug("nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findBySlug("nope"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("nope");
    }

    @Test
    void findAllReturnsPageResponse() {
        var pageable = PageRequest.of(0, 20, Sort.by("releaseYear"));
        when(repository.findAll(ArgumentMatchers.<Specification<AppleModel>>any(), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(mac128k()), pageable, 1));

        var page = service.findAll(AppleModelFilter.NONE, pageable);

        assertThat(page.content()).extracting(AppleModelResponse::slug).containsExactly("macintosh-128k");
        assertThat(page.totalElements()).isEqualTo(1);
        assertThat(page.first()).isTrue();
        assertThat(page.last()).isTrue();
    }

    @Test
    void findAllRejectsUnknownSortProperty() {
        var pageable = PageRequest.of(0, 20, Sort.by("id"));

        assertThatThrownBy(() -> service.findAll(AppleModelFilter.NONE, pageable))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Cannot sort by 'id'");
        verifyNoInteractions(repository);
    }

    @Test
    void findAllRejectsInvertedYearRange() {
        var filter = new AppleModelFilter(null, 2000, 1990, null, null, null, null);

        assertThatThrownBy(() -> service.findAll(filter, PageRequest.of(0, 20)))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("fromYear (2000)");
        verifyNoInteractions(repository);
    }

    @Test
    void createSavesNewModel() {
        when(repository.existsBySlug("macintosh-128k")).thenReturn(false);
        when(familyService.findEntityBySlug("macintosh")).thenReturn(Optional.of(MACINTOSH));
        when(repository.save(any(AppleModel.class))).thenAnswer(inv -> inv.getArgument(0));

        AppleModelResponse response = service.create(request("macintosh-128k", "macintosh"));

        assertThat(response.slug()).isEqualTo("macintosh-128k");
        assertThat(response.clockSpeedMhz()).isEqualTo(8);
    }

    @Test
    void createRejectsDuplicateSlug() {
        when(repository.existsBySlug("macintosh-128k")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request("macintosh-128k", "macintosh")))
                .isInstanceOf(DuplicateResourceException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void createRejectsUnknownFamily() {
        when(repository.existsBySlug("macintosh-128k")).thenReturn(false);
        when(familyService.findEntityBySlug("toaster")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request("macintosh-128k", "toaster")))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("toaster");
    }

    @Test
    void updateChangesFieldsInPlace() {
        AppleModel existing = mac128k();
        when(repository.findBySlug("macintosh-128k")).thenReturn(Optional.of(existing));
        when(familyService.findEntityBySlug("macintosh")).thenReturn(Optional.of(MACINTOSH));
        var changed = new AppleModelRequest("macintosh-128k", "Macintosh", 1984, null, "macintosh",
                CpuArchitecture.M68K, "Motorola 68000", 8, 128, "Updated summary", null);

        AppleModelResponse response = service.update("macintosh-128k", changed);

        assertThat(response.name()).isEqualTo("Macintosh");
        assertThat(response.launchPriceUsd()).isNull();
        assertThat(existing.getSummary()).isEqualTo("Updated summary");
    }

    @Test
    void updateAllowsRenamingToFreeSlug() {
        when(repository.findBySlug("macintosh-128k")).thenReturn(Optional.of(mac128k()));
        when(repository.existsBySlug("original-mac")).thenReturn(false);
        when(familyService.findEntityBySlug("macintosh")).thenReturn(Optional.of(MACINTOSH));

        assertThat(service.update("macintosh-128k", request("original-mac", "macintosh")).slug())
                .isEqualTo("original-mac");
    }

    @Test
    void updateRejectsRenamingToTakenSlug() {
        when(repository.findBySlug("macintosh-128k")).thenReturn(Optional.of(mac128k()));
        when(repository.existsBySlug("macintosh-plus")).thenReturn(true);

        assertThatThrownBy(() -> service.update("macintosh-128k", request("macintosh-plus", "macintosh")))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    void updateThrowsWhenUnknown() {
        when(repository.findBySlug("nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update("nope", request("nope", "macintosh")))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteRemovesExistingModel() {
        AppleModel existing = mac128k();
        when(repository.findBySlug("macintosh-128k")).thenReturn(Optional.of(existing));

        service.delete("macintosh-128k");

        verify(repository).delete(existing);
    }

    @Test
    void deleteThrowsWhenUnknown() {
        when(repository.findBySlug("nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete("nope")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findBySlugsKeepsRequestedOrder() {
        var plus = new AppleModel("macintosh-plus", "Macintosh Plus", 1986, MACINTOSH,
                CpuArchitecture.M68K, "Motorola 68000", "More memory.");
        when(repository.findBySlugIn(List.of("macintosh-plus", "macintosh-128k")))
                .thenReturn(List.of(mac128k(), plus));

        assertThat(service.findBySlugs(List.of("macintosh-plus", "macintosh-128k")))
                .extracting(AppleModelResponse::slug)
                .containsExactly("macintosh-plus", "macintosh-128k");
    }

    @Test
    void findBySlugsThrowsForMissingSlug() {
        when(repository.findBySlugIn(List.of("macintosh-128k", "nope"))).thenReturn(List.of(mac128k()));

        assertThatThrownBy(() -> service.findBySlugs(List.of("macintosh-128k", "nope")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("'nope'");
    }

    @Test
    void findRandomUsesGeneratorWithoutDay() {
        when(repository.countByManufacturer("Apple")).thenReturn(10L);
        when(random.nextInt(10)).thenReturn(7);
        when(repository.findByManufacturer("Apple", PageRequest.of(7, 1, Sort.by("slug"))))
                .thenReturn(new PageImpl<>(List.of(mac128k())));

        assertThat(service.findRandom(null).slug()).isEqualTo("macintosh-128k");
    }

    @Test
    void findRandomIsDeterministicForADay() {
        when(repository.countByManufacturer("Apple")).thenReturn(10L);
        when(repository.findByManufacturer(eq("Apple"), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(mac128k())));

        service.findRandom(LocalDate.of(2026, 11, 11));
        service.findRandom(LocalDate.of(2026, 11, 11));

        verify(repository, times(2)).findByManufacturer(eq("Apple"), eq(PageRequest.of(
                Math.floorMod(Long.hashCode(LocalDate.of(2026, 11, 11).toEpochDay() * 2_654_435_761L), 10),
                1, Sort.by("slug"))));
        verifyNoInteractions(random);
    }

    @Test
    void findRandomThrowsForEmptyCatalog() {
        when(repository.countByManufacturer("Apple")).thenReturn(0L);

        assertThatThrownBy(() -> service.findRandom(null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("The catalog is empty");
    }

    private static AppleModel mac128k() {
        var model = new AppleModel("macintosh-128k", "Macintosh 128K", 1984, MACINTOSH,
                CpuArchitecture.M68K, "Motorola 68000", "The original Mac.");
        model.setLaunchPriceUsd(new BigDecimal("2495.00"));
        model.setClockSpeedMhz(8);
        model.setBaseRamKb(128);
        return model;
    }

    private static AppleModelRequest request(String slug, String familySlug) {
        return new AppleModelRequest(slug, "Macintosh 128K", 1984, new BigDecimal("2495.00"), familySlug,
                CpuArchitecture.M68K, "Motorola 68000", 8, 128, "The original Mac.", null);
    }
}
