package com.example.tategaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tategaki.entity.Tanka;
import com.example.tategaki.repository.TankaRepository;

@Controller
public class PagesController {

	@Autowired
	private TankaRepository tankaRepository;

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

		if (randomTanka != null) {
			model.addAttribute("content", randomTanka.getContent());
			model.addAttribute("currentId", randomTanka.getTankaId());
		} else {
			model.addAttribute("content", "例文");
		}
		return "pages/main";
	}

	@GetMapping("/upload")
	public String uploadPage() {
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
	public String uploadTanka(@RequestParam String content) {
		Tanka tanka = new Tanka();
		tanka.setContent(content);
		tankaRepository.saveAndFlush(tanka);
		return "pages/upload";
	}

	@PostMapping("/update")
	public String updateTanka(@RequestParam String content, @RequestParam Long tankaId) {
		Tanka tanka = tankaRepository.findById(tankaId).get();
		tanka.setContent(content);
		tankaRepository.saveAndFlush(tanka);
		return "redirect:/edit";
	}
}
