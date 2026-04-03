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

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/")
	public String index() {
		return "pages/index";
	}

	@GetMapping("/main")
	public String mainPage(Model model, @RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "currentId", required = false) Long currentId) {
		Tanka tanka;
		if (categoryId == null) {
			if (currentId != null) {
				tanka = tankaRepository.findRandomTankaExcluding(currentId);
				if (tanka == null) {
					tanka = tankaRepository.findRandomTanka();
				}
			} else {
				tanka = tankaRepository.findRandomTanka();
			}
		} else {
			if (currentId != null) {
				tanka = tankaRepository.findRandomTankaByCategoryExcluding(categoryId, currentId);
				if (tanka == null) {
					tanka = tankaRepository.findRandomTankaByCategory(categoryId);
				}
			} else {
				tanka = tankaRepository.findRandomTankaByCategory(categoryId);
			}
		}
		String content;
		if (tanka != null) {
			content = tanka.getContent();
			model.addAttribute("currentId", tanka.getTankaId());
		} else {
			content = "ここに登録した文章を表示します。¶右のメニューボタンから登録してみましょう。";
		}
		List<String> chars = content.replaceAll("\\r\\n|\\n|\\r", "¶")
				.chars().mapToObj(c -> String.valueOf((char) c)).toList();
		model.addAttribute("chars", chars);
		addCategoryList(model);
		model.addAttribute("mainPage", true);
		model.addAttribute("selectedCategoryId", categoryId);

		return "pages/main";
	}

	@GetMapping("/upload")
	public String uploadPage(Model model) {
		addCategoryList(model);
		return "pages/upload";
	}

	@GetMapping("/edit")
	public String editPage(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
		List<Tanka> list;
		if (categoryId == null) {
			list = tankaRepository.findAll();
		} else {
			list = tankaRepository.findByCategory_categoryId(categoryId);
		}
		model.addAttribute("list", list);
		model.addAttribute("selectedCategoryId", categoryId);
		addCategoryList(model);
		return "pages/edit";
	}

	@GetMapping("/settings")
	public String settingPage() {
		return "pages/settings";
	}

	@PostMapping("/add")
	public String add(@RequestParam String content, @RequestParam(required = false) Long categoryId,
			RedirectAttributes redirectAttributes) {
		Tanka tanka = new Tanka();
		tanka.setContent(content);
		if (categoryId != null) {
			categoryRepository.findById(categoryId).ifPresent(cate -> {
				tanka.setCategory(cate);
			});
		}
		tankaRepository.saveAndFlush(tanka);
		redirectAttributes.addFlashAttribute("message", "保存しました");
		return "redirect:/upload";
	}

	@PostMapping("/update")
	public String update(@RequestParam String content, @RequestParam Long tankaId, @RequestParam Long categoryId) {
		Tanka tanka = tankaRepository.findById(tankaId).get();
		tanka.setContent(content);
		tanka.setCategory(categoryRepository.findById(categoryId).get());
		tankaRepository.saveAndFlush(tanka);
		return "redirect:/edit";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam Long tankaId) {
		tankaRepository.deleteById(tankaId);
		return "redirect:/edit";
	}

	private void addCategoryList(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);
	}
}
