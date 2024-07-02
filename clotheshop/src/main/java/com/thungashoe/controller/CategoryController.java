package com.thungashoe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thungashoe.domain.entity.Category;
import com.thungashoe.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public String getAllCategories(Model model) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);

		return "admin/categories";
	}

	@GetMapping("/category/add")
	public String addCategory(Model model) {
    	List<Category> parents = categoryService.getParentCategories();
        model.addAttribute("parents", parents);
		model.addAttribute("category", new Category());
		return "admin/category";
	}
	
	@GetMapping("/category/edit/{id}")
    public String editCategory(Model model, @PathVariable Long id) {
    	List<Category> parents = categoryService.getParentCategories();
        model.addAttribute("parents", parents);
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/category";
    }
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("message","Xoá danh mục thành công");
        return "redirect:/admin/categories";
    }

	@PostMapping("/category/save")
	public String addOrUpdateCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
		if(category.getParent().getId() == -1) {
    		category.setParent(null);
    	}
        System.out.println("ID: " + category.getId());
		if (category.getId() != null) {
			// Có ID -> Cập nhật
			categoryService.updateCategory(category.getId(), category);
			redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công");
		} else {
			// Không có ID -> Thêm mới
			categoryService.addCategory(category);
			redirectAttributes.addFlashAttribute("message", "Thêm mới danh mục thành công");
		}
		return "redirect:/admin/categories";
	}
}
