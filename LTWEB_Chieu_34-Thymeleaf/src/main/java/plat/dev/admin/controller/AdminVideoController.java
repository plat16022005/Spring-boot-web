package plat.dev.admin.controller;

import plat.dev.video.entity.Video;
import plat.dev.video.service.VideoService;
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
@RequestMapping("/admin/videos")
public class AdminVideoController {

  @Autowired private VideoService service;
  @Autowired private CategoryService categoryService;

  @GetMapping
  public String list(Model model,
                     @RequestParam(value="q", required=false) String q,
                     @RequestParam(value="page", defaultValue="0") int page,
                     @RequestParam(value="size", defaultValue="5") int size) {
    Pageable pageable = PageRequest.of(page, size);
    model.addAttribute("pageData", service.search(q, pageable));
    model.addAttribute("q", q);
    return "admin/video/list";
  }

  @GetMapping("/create")
  public String createForm(Model model){
    model.addAttribute("video", new Video(null, null, null, null, false, null, null));
    model.addAttribute("categories", categoryService.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
    model.addAttribute("title", "Thêm video");
    return "admin/video/form";
  }

  @PostMapping
  public String create(@Valid @ModelAttribute("video") Video video,
                       BindingResult result, RedirectAttributes ra){
    if(result.hasErrors()) return "admin/video/form";
    service.save(video);
    ra.addFlashAttribute("message","Thêm video thành công!");
    return "redirect:/admin/videos";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model){
    Video v = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy video"));
    model.addAttribute("video", v);
    model.addAttribute("categories", categoryService.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
    model.addAttribute("title", "Cập nhật video");
    return "admin/video/form";
  }

  @PostMapping("/{id}")
  public String update(@PathVariable Long id,
                       @Valid @ModelAttribute("video") Video video,
                       BindingResult result, RedirectAttributes ra){
    if(result.hasErrors()) return "admin/video/form";
    video.setId(id);
    service.save(video);
    ra.addFlashAttribute("message","Cập nhật video thành công!");
    return "redirect:/admin/videos";
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes ra){
    service.deleteById(id);
    ra.addFlashAttribute("message","Đã xoá video!");
    return "redirect:/admin/videos";
  }
}
