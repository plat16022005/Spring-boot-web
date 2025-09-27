package plat.dev.category.service;

import plat.dev.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	Page<Category> search(String keyword, Pageable pageable);

	Page<Category> findAll(Pageable pageable);
	
    List<Category> findAll(); // ✅ thêm hàm này

	Optional<Category> findById(Long id);

	Category save(Category c);

	void deleteById(Long id);
	
}
