package com.example.tategaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tategaki.entity.Category;
import com.example.tategaki.entity.Tanka;
import com.example.tategaki.repository.CategoryRepository;
import com.example.tategaki.repository.TankaRepository;

@Controller
public class PagesController {

	@Autowired
	private TankaRepository tankaRepository;
	private CategoryRepository categoryRepository;

	@GetMapping("/")
	public String index() {
		return "pages/index";
	}

	@GetMapping("/main")
	public String mainPage(Model model, @RequestParam(value = "currentId", required = false) Long currentId) {

		Tanka randomTanka;

		if (currentId != null && tankaRepository.count() > 1) {
			randomTanka = tankaRepository.findRandomTankaExcluding(currentId);
		} else {
			randomTanka = tankaRepository.findRandomTanka();
		}
		String content;
		if (randomTanka != null) {
			content = randomTanka.getContent();
			model.addAttribute("currentId", randomTanka.getTankaId());
		} else {
			content = "例文";
		}
		List<String> chars = content.replaceAll("\\r\\n|\\n|\\r", "¶")
				.chars().mapToObj(c -> String.valueOf((char) c)).toList();
		model.addAttribute("chars", chars);
		model.addAttribute("mainPage", true);

		return "pages/main";
	}

	@GetMapping("/upload")
	public String uploadPage(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		return "pages/upload";
	}

	@GetMapping("/edit")
	public String editPage(Model model) {
		List<Tanka> list = tankaRepository.findAll();
		model.addAttribute("list", list);
		return "pages/edit";
	}

	@GetMapping("/settings")
	public String settingPage() {
		return "pages/settings";
	}

	@PostMapping("/add")
	public String uploadTanka(@RequestParam String content, @RequestParam Long categoryId,
			RedirectAttributes redirectAttributes) {
		Tanka tanka = new Tanka();
		tanka.setContent(content);
		categoryRepository.findById(categoryId).ifPresent(cate -> {
			tanka.setCategory(cate);
		});
		tankaRepository.saveAndFlush(tanka);
		redirectAttributes.addFlashAttribute("message", "保存しました");
		return "redirect:/upload";
	}

	@PostMapping("/update")
	public String updateTanka(@RequestParam String content, @RequestParam Long tankaId) {
		Tanka tanka = tankaRepository.findById(tankaId).get();
		tanka.setContent(content);
		tankaRepository.saveAndFlush(tanka);
		return "redirect:/edit";
	}

	@PostMapping("/delete")
	public String deleteTanka(@RequestParam Long tankaId) {
		tankaRepository.deleteById(tankaId);
		return "redirect:/edit";
	}
}
