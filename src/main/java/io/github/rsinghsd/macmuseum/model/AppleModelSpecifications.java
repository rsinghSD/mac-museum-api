package io.github.rsinghsd.macmuseum.model;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

/** Builds a JPA {@link Specification} from an {@link AppleModelFilter}. */
final class AppleModelSpecifications {

    private static final char LIKE_ESCAPE = '\\';

    private AppleModelSpecifications() {
    }

    static Specification<AppleModel> matching(AppleModelFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.year() != null) {
                predicates.add(cb.equal(root.get("releaseYear"), filter.year()));
            }
            if (filter.fromYear() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("releaseYear"), filter.fromYear()));
            }
            if (filter.toYear() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("releaseYear"), filter.toYear()));
            }
            if (hasText(filter.family())) {
                predicates.add(cb.equal(root.get("family").get("slug"), filter.family().trim()));
            }
            if (filter.cpu() != null) {
                predicates.add(cb.equal(root.get("cpuArchitecture"), filter.cpu()));
            }
            if (hasText(filter.q())) {
                String pattern = "%" + escapeLike(filter.q().trim().toLowerCase(Locale.ROOT)) + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), pattern, LIKE_ESCAPE),
                        cb.like(cb.lower(root.get("cpuName")), pattern, LIKE_ESCAPE),
                        cb.like(cb.lower(root.get("summary")), pattern, LIKE_ESCAPE)));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    /** Escapes LIKE wildcards so user input such as "100%" is matched literally. */
    static String escapeLike(String value) {
        return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
