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
import plat.dev.video.entity.Video;
import plat.dev.video.service.VideoService;
import plat.dev.category.service.CategoryService;

@Controller
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @Autowired
    private CategoryService categoryService;

    // LIST + SEARCH + PAGINATION
    @GetMapping
    public String list(Model model,
                       @RequestParam(value = "q", required = false) String q,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Video> pageData = service.search(q, pageable);

        model.addAttribute("pageData", pageData);
        model.addAttribute("q", q);
        return "video/list";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("video", new Video(null, null, null, null, false, null, null));
        model.addAttribute("categories", categoryService.findAll());
        return "video/form";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("video") Video video,
                         BindingResult result,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/video/form";
        }
        service.save(video);
        ra.addFlashAttribute("message", "Thêm video thành công!");
        return "redirect:/admin/videos";
    }

    // EDIT FORM
    @GetMapping("/{id:[0-9]+}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Video v = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy video"));
        model.addAttribute("video", v);
        model.addAttribute("categories", categoryService.findAll(null));
        return "admin/video/form";
    }

    // UPDATE SAVE
    @PostMapping("/{id:[0-9]+}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("video") Video video,
                         BindingResult result,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "admin/video/form";
        }
        video.setId(id);
        service.save(video);
        ra.addFlashAttribute("message", "Cập nhật video thành công!");
        return "redirect:/admin/videos";
    }

    // DELETE
    @PostMapping("/{id:[0-9]+}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("message", "Xoá video thành công!");
        return "redirect:/admin/videos";
    }
}
