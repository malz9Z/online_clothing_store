package com.thungashoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thungashoe.domain.dto.ProductResponse;
import com.thungashoe.domain.entity.Product;
import com.thungashoe.domain.entity.ProductItem;
import com.thungashoe.service.CategoryService;
import com.thungashoe.service.ProductService;

@Controller
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String getStore(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 3;
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("colors", productService.getAllColors());
		model.addAttribute("sizes", productService.getAllSizes());
		model.addAttribute("brands", productService.getAllBrands());
		Page<ProductResponse> productPage = productService.getAllProducts( page - 1, pageSize, null, null);
		model.addAttribute("productPage", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber()+1);
        model.addAttribute("totalPages", productPage.getTotalPages());
		return "shop/store";
	}
	
	@GetMapping("/product-detail/{id}")
    public String getProductDetails(@PathVariable String id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("selectedItem", new ProductItem());
        return "shop/productDetail";
    }
}
