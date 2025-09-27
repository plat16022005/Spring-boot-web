package plat.dev.admin.controller;

import plat.dev.user.entity.User;
import plat.dev.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

  @Autowired private UserService service;
  @Autowired private PasswordEncoder passwordEncoder;

  @GetMapping
  public String list(Model model,
                     @RequestParam(value="q", required=false) String q,
                     @RequestParam(value="page", defaultValue="0") int page,
                     @RequestParam(value="size", defaultValue="5") int size) {
    Pageable pageable = PageRequest.of(page, size);
    model.addAttribute("pageData", service.search(q, pageable));
    model.addAttribute("q", q);
    return "admin/user/list";
  }

  @GetMapping("/create")
  public String createForm(Model model){
    model.addAttribute("user", new User(null, null, null, null, false, null, null, null));
    model.addAttribute("title", "Thêm người dùng");
    return "admin/user/form";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute("user") User user,
                       BindingResult result, RedirectAttributes ra){
    if(result.hasErrors()) return "admin/user/form";
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    service.save(user);
    ra.addFlashAttribute("message","Thêm user thành công!");
    return "redirect:/admin/users";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model){
    User u = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user"));
    // để không lộ hash trên form, set rỗng:
    u.setPassword("");
    model.addAttribute("user", u);
    model.addAttribute("title","Cập nhật user");
    return "admin/user/form";
  }

  @PostMapping("/{id}")
  public String update(@PathVariable Long id,
                       @Valid @ModelAttribute("user") User user,
                       BindingResult result, RedirectAttributes ra){
    if(result.hasErrors()) return "admin/user/form";
    user.setId(id);
    if(user.getPassword()!=null && !user.getPassword().isBlank()){
      user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(user.getPassword()));
    }
    service.save(user);
    ra.addFlashAttribute("message","Cập nhật user thành công!");
    return "redirect:/admin/users";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes ra){
    service.deleteById(id);
    ra.addFlashAttribute("message","Đã xoá user!");
    return "redirect:/admin/users";
  }
}
