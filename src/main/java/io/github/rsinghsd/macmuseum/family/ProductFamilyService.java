package io.github.rsinghsd.macmuseum.family;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductFamilyService {

    private final ProductFamilyRepository repository;

    public ProductFamilyService(ProductFamilyRepository repository) {
        this.repository = repository;
    }

    /** All families sorted by name, each with its number of models. */
    public List<ProductFamilyResponse> findAll() {
        return repository.findAllWithModelCount();
    }

    /** Looks up a family entity for use by other services (never returned to clients). */
    public Optional<ProductFamily> findEntityBySlug(String slug) {
        return repository.findBySlug(slug);
    }
}
