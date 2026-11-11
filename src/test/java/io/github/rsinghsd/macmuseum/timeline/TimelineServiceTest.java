package io.github.rsinghsd.macmuseum.timeline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.github.rsinghsd.macmuseum.model.AppleModelResponse;
import io.github.rsinghsd.macmuseum.model.AppleModelService;
import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimelineServiceTest {

    @Mock
    AppleModelService modelService;

    @InjectMocks
    TimelineService service;

    @Test
    void groupsModelsIntoErasOrderedByFirstRelease() {
        when(modelService.findAllChronological()).thenReturn(List.of(
                model("imac-g3", 1998, CpuArchitecture.POWERPC),
                model("apple-ii", 1977, CpuArchitecture.MOS_6502),
                model("newton", 1993, CpuArchitecture.ARM),
                model("power-mac-6100", 1994, CpuArchitecture.POWERPC),
                model("apple-i", 1976, CpuArchitecture.MOS_6502)));

        TimelineResponse timeline = service.build();

        assertThat(timeline.eras()).extracting(TimelineResponse.Era::architecture)
                .containsExactly(CpuArchitecture.MOS_6502, CpuArchitecture.ARM, CpuArchitecture.POWERPC);

        TimelineResponse.Era powerPc = timeline.eras().get(2);
        assertThat(powerPc.startYear()).isEqualTo(1994);
        assertThat(powerPc.endYear()).isEqualTo(1998);
        assertThat(powerPc.modelCount()).isEqualTo(2);
        assertThat(powerPc.label()).isEqualTo("PowerPC");
        assertThat(powerPc.models()).extracting(TimelineResponse.Entry::slug)
                .containsExactly("power-mac-6100", "imac-g3");
    }

    @Test
    void omitsErasWithoutModels() {
        TimelineResponse timeline = TimelineService.build(List.of(model("m1", 2020, CpuArchitecture.APPLE_SILICON)));

        assertThat(timeline.eras()).singleElement()
                .extracting(TimelineResponse.Era::architecture).isEqualTo(CpuArchitecture.APPLE_SILICON);
    }

    @Test
    void returnsNoErasForEmptyCatalog() {
        assertThat(TimelineService.build(List.of()).eras()).isEmpty();
    }

    private static AppleModelResponse model(String slug, int year, CpuArchitecture cpu) {
        return new AppleModelResponse(slug, slug, year, null, new AppleModelResponse.FamilyRef("f", "Family"),
                cpu, "cpu", null, null, "summary");
    }
}
