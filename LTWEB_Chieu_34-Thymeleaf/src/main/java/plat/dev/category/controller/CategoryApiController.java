package plat.dev.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import plat.dev.category.entity.Category;
import plat.dev.category.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryService service;

    // GET ALL (pagination)
    @GetMapping
    public Page<Category> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        return (Page<Category>) service.findAll(PageRequest.of(page, size));
    }

    // GET ONE
    @GetMapping("/{id}")
    public Category getOne(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // CREATE
    @PostMapping
    public Category create(@RequestBody Category c) {
        return service.save(c);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category c) {
        c.setId(id);
        return service.save(c);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
