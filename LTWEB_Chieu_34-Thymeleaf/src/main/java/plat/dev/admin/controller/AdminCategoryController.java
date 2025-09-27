package plat.dev.admin.controller;

import plat.dev.category.entity.Category;
import plat.dev.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping
	public String list(Model model, @RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Category> pageData = service.search(q, pageable);
		model.addAttribute("pageData", pageData);
		model.addAttribute("q", q);
		return "category/list"; // tái dùng view hiện tại
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("title", "Thêm danh mục");
		return "category/form";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute("category") Category category, BindingResult result,
			RedirectAttributes ra) {
		if (result.hasErrors())
			return "category/form";
		service.save(category);
		ra.addFlashAttribute("message", "Thêm danh mục thành công!");
		return "redirect:/admin/categories";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		Category c = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy"));
		model.addAttribute("category", c);
		model.addAttribute("title", "Cập nhật danh mục");
		return "category/form";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("category") Category category,
			BindingResult result, RedirectAttributes ra) {
		if (result.hasErrors())
			return "category/form";
		category.setId(id);
		service.save(category);
		ra.addFlashAttribute("message", "Cập nhật danh mục thành công!");
		return "redirect:/admin/categories";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes ra) {
		service.deleteById(id);
		ra.addFlashAttribute("message", "Đã xoá danh mục!");
		return "redirect:/admin/categories";
	}
}
