package com.example.tategaki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tategaki.entity.Category;
import com.example.tategaki.repository.CategoryRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("")
	public String getPage(Model model) {
		model.addAttribute("categoryList", categoryRepository.findAll());
		return "pages/category_edit";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam Long categoryId) {
		categoryRepository.deleteById(categoryId);
		return "redirect:/category";
	}

	@PostMapping("/update")
	public String update(@RequestParam Long categoryId, @RequestParam String categoryName) {
		Category category = categoryRepository.findById(categoryId).get();
		category.setCategoryName(categoryName);
		categoryRepository.saveAndFlush(category);
		return "redirect:/category";
	}

	@PostMapping("/add")
	public String add(@RequestParam String categoryName) {
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryRepository.saveAndFlush(category);
		return "redirect:/category";
	}

}
