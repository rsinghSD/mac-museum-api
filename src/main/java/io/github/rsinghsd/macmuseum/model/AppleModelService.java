package io.github.rsinghsd.macmuseum.model;

import io.github.rsinghsd.macmuseum.common.BadRequestException;
import io.github.rsinghsd.macmuseum.common.DuplicateResourceException;
import io.github.rsinghsd.macmuseum.common.PageResponse;
import io.github.rsinghsd.macmuseum.common.ResourceNotFoundException;
import io.github.rsinghsd.macmuseum.family.ProductFamily;
import io.github.rsinghsd.macmuseum.family.ProductFamilyService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
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
    private final RandomGenerator random;

    public AppleModelService(AppleModelRepository repository, ProductFamilyService familyService,
                             RandomGenerator random) {
        this.repository = repository;
        this.familyService = familyService;
        this.random = random;
    }

    public PageResponse<AppleModelResponse> findAll(AppleModelFilter filter, Pageable pageable) {
        filter.validate();
        validateSort(pageable.getSort());
        return PageResponse.from(
                repository.findAll(AppleModelSpecifications.matching(filter), pageable),
                AppleModelMapper::toResponse);
    }

    /** Every model ordered by release year, then name. Used by the timeline. */
    public List<AppleModelResponse> findAllChronological() {
        return repository.findAll(Sort.by("releaseYear", "name")).stream()
                .map(AppleModelMapper::toResponse)
                .toList();
    }

    public AppleModelResponse findBySlug(String slug) {
        return AppleModelMapper.toResponse(getEntity(slug));
    }

    /**
     * Returns the models for the given slugs, in the same order.
     *
     * @throws ResourceNotFoundException naming the first slug that does not exist
     */
    public List<AppleModelResponse> findBySlugs(List<String> slugs) {
        Map<String, AppleModel> found = repository.findBySlugIn(slugs).stream()
                .collect(Collectors.toMap(AppleModel::getSlug, Function.identity()));
        return slugs.stream()
                .map(slug -> Optional.ofNullable(found.get(slug))
                        .map(AppleModelMapper::toResponse)
                        .orElseThrow(() -> ResourceNotFoundException.model(slug)))
                .toList();
    }

    /**
     * Picks a model at random, or deterministically for a given day so that every visitor sees the
     * same "Mac of the day".
     *
     * @throws ResourceNotFoundException when the catalog is empty
     */
    public AppleModelResponse findRandom(LocalDate day) {
        long count = repository.count();
        if (count == 0) {
            throw new ResourceNotFoundException("The catalog is empty");
        }
        int index = day == null
                ? random.nextInt((int) count)
                : Math.floorMod(Long.hashCode(day.toEpochDay() * 2_654_435_761L), (int) count);
        return repository.findAll(PageRequest.of(index, 1, Sort.by("slug"))).stream()
                .findFirst()
                .map(AppleModelMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("The catalog is empty"));
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
