package io.github.rsinghsd.macmuseum.compare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.github.rsinghsd.macmuseum.common.BadRequestException;
import io.github.rsinghsd.macmuseum.model.AppleModelResponse;
import io.github.rsinghsd.macmuseum.model.AppleModelService;
import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompareServiceTest {

    private static final AppleModelResponse MAC = model("macintosh-128k", 1984, "2495.00", 8, 128);
    private static final AppleModelResponse IMAC = model("imac-g3", 1998, "1299.00", 233, 32768);

    @Mock
    AppleModelService modelService;

    @InjectMocks
    CompareService service;

    @Test
    void comparesEachModelWithTheFirst() {
        when(modelService.findBySlugs(List.of("macintosh-128k", "imac-g3"))).thenReturn(List.of(MAC, IMAC));

        CompareResponse response = service.compare(List.of(" macintosh-128k", "imac-g3 "));

        assertThat(response.models()).containsExactly(MAC, IMAC);
        CompareResponse.Comparison c = response.comparisons().getFirst();
        assertThat(c.baselineSlug()).isEqualTo("macintosh-128k");
        assertThat(c.otherSlug()).isEqualTo("imac-g3");
        assertThat(c.yearsApart()).isEqualTo(14);
        assertThat(c.clockSpeedRatio()).isEqualTo(29.13);
        assertThat(c.ramRatio()).isEqualTo(256.0);
        assertThat(c.priceDifferenceUsd()).isEqualByComparingTo("-1196.00");
    }

    @Test
    void leavesDerivedValuesNullWhenUnknown() {
        AppleModelResponse unknown = model("ipod", 2001, null, null, null);

        CompareResponse.Comparison c = CompareService.compare(MAC, unknown);

        assertThat(c.clockSpeedRatio()).isNull();
        assertThat(c.ramRatio()).isNull();
        assertThat(c.priceDifferenceUsd()).isNull();
    }

    @Test
    void ratioIsNullForZeroBaseline() {
        assertThat(CompareService.ratio(10, 0)).isNull();
    }

    @Test
    void rejectsFewerThanTwoSlugs() {
        assertThatThrownBy(() -> service.compare(List.of("imac-g3", " ")))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("between 2 and 4");
        verifyNoInteractions(modelService);
    }

    @Test
    void rejectsMoreThanFourSlugs() {
        assertThatThrownBy(() -> service.compare(List.of("a", "b", "c", "d", "e")))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("got 5");
    }

    @Test
    void rejectsDuplicateSlugs() {
        assertThatThrownBy(() -> service.compare(List.of("imac-g3", "imac-g3")))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("only once");
    }

    private static AppleModelResponse model(String slug, int year, String price, Integer mhz, Integer ramKb) {
        return new AppleModelResponse(slug, slug, year, price == null ? null : new BigDecimal(price),
                new AppleModelResponse.FamilyRef("f", "Family"), CpuArchitecture.M68K, "cpu", mhz, ramKb, "s", "Apple", null, null);
    }
}
