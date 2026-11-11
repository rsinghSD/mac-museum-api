package io.github.rsinghsd.macmuseum.family;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductFamilyRepository extends JpaRepository<ProductFamily, Long> {

    Optional<ProductFamily> findBySlug(String slug);

    @Query("""
            select new io.github.rsinghsd.macmuseum.family.ProductFamilyResponse(
                f.slug, f.name, f.description, count(m))
            from ProductFamily f
            left join io.github.rsinghsd.macmuseum.model.AppleModel m on m.family = f
            group by f.id, f.slug, f.name, f.description
            order by f.name
            """)
    List<ProductFamilyResponse> findAllWithModelCount();
}
