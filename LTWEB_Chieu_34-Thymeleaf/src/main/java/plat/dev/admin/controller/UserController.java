package plat.dev.admin.controller;

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
import plat.dev.user.entity.User;
import plat.dev.user.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // LIST + SEARCH + PAGINATION
    @GetMapping
    public String list(Model model,
                       @RequestParam(value = "q", required = false) String q,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageData = service.search(q, pageable);

        model.addAttribute("pageData", pageData);
        model.addAttribute("q", q);
        return "admin/user/list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/form";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/user/form";
        }
        service.save(user);
        ra.addFlashAttribute("message", "Thêm user thành công!");
        return "redirect:/admin/users";
    }

    // EDIT FORM
    @GetMapping("/{id:[0-9]+}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        User u = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user"));
        model.addAttribute("user", u);
        return "admin/user/form";
    }

    // UPDATE SAVE
    @PostMapping("/{id:[0-9]+}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/user/form";
        }
        user.setId(id);
        service.save(user);
        ra.addFlashAttribute("message", "Cập nhật user thành công!");
        return "redirect:/admin/users";
    }

    // DELETE
    @PostMapping("/{id:[0-9]+}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("message", "Xoá user thành công!");
        return "redirect:/admin/users";
    }
}
