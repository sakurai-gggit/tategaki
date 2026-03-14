package com.example.tategaki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

	@GetMapping("/")
	public String index() {
		return "pages/index";
	}

	@GetMapping("/main")
	public String mainPage() {
		return "pages/main";
	}

	@GetMapping("/upload")
	public String uploadPage() {
		return "pages/upload";
	}
}
