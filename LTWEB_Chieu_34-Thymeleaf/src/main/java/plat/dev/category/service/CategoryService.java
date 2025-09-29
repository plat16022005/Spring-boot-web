package plat.dev.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import plat.dev.category.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Page<Category> search(String q, Pageable pageable);

    Iterable<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);
}
