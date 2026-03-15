package com.example.tategaki.controller;

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
	public String mainPage(Model model) {
		Tanka randomTanka = tankaRepository.findRandomTanka();
		if (randomTanka != null) {
			model.addAttribute("content", randomTanka.getContent());
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
	public String editPage() {
		return "pages/edit";
	}

	@GetMapping("/settings")
	public String settingPage() {
		return "pages/settings";
	}

	@PostMapping("/add")
	public String tankaUpload(@RequestParam String content) {
		Tanka tanka = new Tanka();
		tanka.setContent(content);
		tankaRepository.saveAndFlush(tanka);
		return "pages/upload";
	}
}
