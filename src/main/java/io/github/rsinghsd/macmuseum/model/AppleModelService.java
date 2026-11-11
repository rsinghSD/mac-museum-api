package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.BadRequestException;
import io.github.rsinghsd.macmuseum.common.DuplicateResourceException;
import io.github.rsinghsd.macmuseum.common.PageResponse;
import io.github.rsinghsd.macmuseum.common.ResourceNotFoundException;
import io.github.rsinghsd.macmuseum.family.ProductFamily;
import io.github.rsinghsd.macmuseum.family.ProductFamilyService;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AppleModelService {

    /** Properties clients may sort by. Anything else is rejected with 400 instead of failing in JPA. */
    static final Set<String> SORTABLE_PROPERTIES =
            Set.of("slug", "name", "releaseYear", "launchPriceUsd", "clockSpeedMhz", "baseRamKb");

    private final AppleModelRepository repository;
    private final ProductFamilyService familyService;

    public AppleModelService(AppleModelRepository repository, ProductFamilyService familyService) {
        this.repository = repository;
        this.familyService = familyService;
    }

    public PageResponse<AppleModelResponse> findAll(AppleModelFilter filter, Pageable pageable) {
        filter.validate();
        validateSort(pageable.getSort());
        return PageResponse.from(
                repository.findAll(AppleModelSpecifications.matching(filter), pageable),
                AppleModelMapper::toResponse);
    }

    public AppleModelResponse findBySlug(String slug) {
        return AppleModelMapper.toResponse(getEntity(slug));
    }

    @Transactional
    public AppleModelResponse create(AppleModelRequest request) {
        if (repository.existsBySlug(request.slug())) {
            throw duplicateSlug(request.slug());
        }
        AppleModel model = AppleModelMapper.toEntity(request, resolveFamily(request.familySlug()));
        return AppleModelMapper.toResponse(repository.save(model));
    }

    @Transactional
    public AppleModelResponse update(String slug, AppleModelRequest request) {
        AppleModel model = getEntity(slug);
        if (!slug.equals(request.slug()) && repository.existsBySlug(request.slug())) {
            throw duplicateSlug(request.slug());
        }
        AppleModelMapper.update(model, request, resolveFamily(request.familySlug()));
        return AppleModelMapper.toResponse(model);
    }

    @Transactional
    public void delete(String slug) {
        repository.delete(getEntity(slug));
    }

    private AppleModel getEntity(String slug) {
        return repository.findBySlug(slug).orElseThrow(() -> ResourceNotFoundException.model(slug));
    }

    private ProductFamily resolveFamily(String familySlug) {
        return familyService.findEntityBySlug(familySlug)
                .orElseThrow(() -> new BadRequestException("Unknown family '" + familySlug + "'"));
    }

    private static DuplicateResourceException duplicateSlug(String slug) {
        return new DuplicateResourceException("A model with slug '" + slug + "' already exists");
    }

    private static void validateSort(Sort sort) {
        for (Sort.Order order : sort) {
            if (!SORTABLE_PROPERTIES.contains(order.getProperty())) {
                throw new BadRequestException("Cannot sort by '" + order.getProperty()
                        + "'. Allowed: " + String.join(", ", SORTABLE_PROPERTIES.stream().sorted().toList()));
            }
        }
    }
}
