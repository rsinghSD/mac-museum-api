package io.github.rsinghsd.macmuseum.model;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AppleModelRepository extends JpaRepository<AppleModel, Long> {

    @EntityGraph(attributePaths = "family")
    Optional<AppleModel> findBySlug(String slug);

    boolean existsBySlug(String slug);

    /** Overridden to fetch the family in the same query and avoid N+1 selects. */
    @Override
    @EntityGraph(attributePaths = "family")
    @NonNull
    Page<AppleModel> findAll(@NonNull Pageable pageable);
}
