package io.github.rsinghsd.macmuseum.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AppleModelRepository extends JpaRepository<AppleModel, Long>, JpaSpecificationExecutor<AppleModel> {

    @EntityGraph(attributePaths = "family")
    Optional<AppleModel> findBySlug(String slug);

    @EntityGraph(attributePaths = "family")
    List<AppleModel> findBySlugIn(List<String> slugs);

    boolean existsBySlug(String slug);

    long countByManufacturer(String manufacturer);

    @EntityGraph(attributePaths = "family")
    List<AppleModel> findByManufacturer(String manufacturer, Sort sort);

    @EntityGraph(attributePaths = "family")
    Page<AppleModel> findByManufacturer(String manufacturer, Pageable pageable);

    /** Overridden to fetch the family in the same query and avoid N+1 selects. */
    @Override
    @EntityGraph(attributePaths = "family")
    @NonNull
    Page<AppleModel> findAll(@NonNull Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "family")
    @NonNull
    Page<AppleModel> findAll(@Nullable Specification<AppleModel> spec, @NonNull Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "family")
    @NonNull
    List<AppleModel> findAll(@NonNull Sort sort);
}
