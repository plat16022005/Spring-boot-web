package plat.dev.category.controller;

import plat.dev.category.entity.Category;
import plat.dev.category.service.CategoryService;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;

	// LIST + SEARCH + PAGINATION
	@GetMapping
	public String list(Model model,
	                   @RequestParam(value = "q", required = false) String q,
	                   @RequestParam(value = "page", defaultValue = "0") int page,
	                   @RequestParam(value = "size", defaultValue = "5") int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    CategoryService categoryService = null;
		@SuppressWarnings("null")
		Page<Category> pageData = categoryService.search(q, pageable);

	    model.addAttribute("pageData", pageData);
	    model.addAttribute("q", q);
	    return "category/list"; // nhớ view tồn tại trong templates/category/list.html
	}

	// SHOW CREATE FORM
	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("title", "Thêm danh mục");
		return "category/form"; // --> templates/category/form.html
	}

	// HANDLE CREATE
	@PostMapping
	public String create(@Valid @ModelAttribute("category") Category category, BindingResult result,
			RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "category/form";
		}
		service.save(category);
		ra.addFlashAttribute("message", "Thêm danh mục thành công!");
		return "redirect:/categories";
	}

	// SHOW EDIT FORM
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Category c = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục"));
		model.addAttribute("category", c);
		model.addAttribute("title", "Cập nhật danh mục");
		return "category/form";
	}

	// HANDLE UPDATE
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("category") Category category,
			BindingResult result, RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "category/form";
		}
		category.setId(id);
		service.save(category);
		ra.addFlashAttribute("message", "Cập nhật danh mục thành công!");
		return "redirect:/categories";
	}

	// DELETE
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes ra) {
		service.deleteById(id);
		ra.addFlashAttribute("message", "Đã xoá danh mục!");
		return "redirect:/categories";
	}
}