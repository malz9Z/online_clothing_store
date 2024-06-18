package com.thungashoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.thungashoe.service.CategoryService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String index(Model model) {	
		model.addAttribute("categories", categoryService.getAllCategories());
		return "shop/index";
	}
	
	@GetMapping("/admin")
	public String dashboard(Model model) {		
		return "admin/dashboard";
	}
	
}
