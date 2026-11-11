package io.github.rsinghsd.macmuseum.family;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductFamilyServiceTest {

    @Mock
    ProductFamilyRepository repository;

    @InjectMocks
    ProductFamilyService service;

    @Test
    void findAllReturnsFamiliesWithCounts() {
        var imac = new ProductFamilyResponse("imac", "iMac", "All-in-ones", 4);
        when(repository.findAllWithModelCount()).thenReturn(List.of(imac));

        assertThat(service.findAll()).containsExactly(imac);
    }

    @Test
    void findEntityBySlugDelegatesToRepository() {
        var family = new ProductFamily("imac", "iMac", "All-in-ones");
        when(repository.findBySlug("imac")).thenReturn(Optional.of(family));

        assertThat(service.findEntityBySlug("imac")).containsSame(family);
    }
}
