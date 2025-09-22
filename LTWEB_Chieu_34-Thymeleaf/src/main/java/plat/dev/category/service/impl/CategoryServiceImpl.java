package plat.dev.category.service.impl;

import plat.dev.category.entity.Category;
import plat.dev.category.repository.CategoryRepository;
import plat.dev.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repo;

	@Override
	public Page<Category> search(String keyword, Pageable pageable) {
		if (keyword == null || keyword.isBlank()) {
			return repo.findAll(pageable);
		}
		return repo.findByNameContainingIgnoreCase(keyword.trim(), pageable);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public Category save(Category c) {
		return repo.save(c);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
}
