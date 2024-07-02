package com.thungashoe.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thungashoe.domain.dto.ProductDTO;
import com.thungashoe.domain.dto.ProductResponse;
import com.thungashoe.domain.entity.Brand;
import com.thungashoe.domain.entity.Category;
import com.thungashoe.domain.entity.Product;
import com.thungashoe.service.CategoryService;
import com.thungashoe.service.ProductService;
import com.thungashoe.util.ArrayJsonConverter;
import com.thungashoe.util.ProductConverter;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
//		Product product = productService.getProductById(1L);
//		printProduct(product);
		List<ProductResponse> productPage = productService.getAllProducts().stream()
                .map(ProductResponse::toProductDTO)
                .collect(Collectors.toList());
		model.addAttribute("products", productPage);
		return "admin/products";
	}
	
	@GetMapping("/product/add")
	public String addProduct(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("colors", productService.getAllColors());
		model.addAttribute("sizes", productService.getAllSizes());
		model.addAttribute("brands", productService.getAllBrands());
		model.addAttribute("product", new ProductDTO());
		return "admin/product";
	}
	
	@GetMapping("/product/edit/{id}")
	public String editCategory(Model model, @PathVariable String id) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
        model.addAttribute("product", productService.getProductById(id));
		return "admin/product";
	}
	
	@PostMapping("/product/save")
	public String addOrUpdateCategory(@ModelAttribute ProductDTO product, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Product productC = ProductConverter.convertToProduct(product);
		Brand brand = Brand.builder()
				.name(ArrayJsonConverter.converToString(request.getParameter("input-brand")
						.trim())).build();
////		System.out.println(request.getParameter("input-brand"));
////		System.out.println(ArrayJsonConverter.converToString(request.getParameter("input-brand")
//				.trim()));
		productC.setBrand(brand);
		if (product.getId() != null) {
			// Có ID -> Cập nhật
			productService.updateProduct(productC.getId(), productC);
			redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công");
		} else {
			// Không có ID -> Thêm mới
			productService.addProduct(productC);
			redirectAttributes.addFlashAttribute("message", "Thêm mới danh mục thành công");
		}
		return "redirect:/admin/products";
	}
	
	public void printProduct(Product product0) {
		  System.out.println("Thông tin sản phẩm:");
		  if (product0 != null) {
		      System.out.println("ID: " + product0.getId());
		      System.out.println("Tên: " + (product0.getName() != null ? product0.getName() : "Không có"));
		      System.out.println("Nhãn hiệu: " + (product0.getBrand() != null ? product0.getBrand().getName() : "Không có"));
		      System.out.println("Danh mục: " + (product0.getCategory() != null ? product0.getCategory().getId() : "Không có"));
		      System.out.println("Thời gian xuất bản: " + (product0.getPublishTime() != null ? product0.getPublishTime() : "Không có"));
		      System.out.println("Mô tả: " + (product0.getDescription() != null ? product0.getDescription() : "Không có"));
		      System.out.println("Thời gian tạo: " + (product0.getCreationTime() != null ? product0.getCreationTime() : "Không có"));
		      System.out.println("Thời gian cập nhật: " + (product0.getUpdateTime() != null ? product0.getUpdateTime() : "Không có"));
		      System.out.println("Ảnh sản phẩm:");
//		      product0.getProductImages().forEach(image -> System.out.println("- " + image.getUrlImgs()));
		      System.out.println("Các mặt hàng sản phẩm:");
		      product0.getProductItems().forEach(item -> {
		          System.out.println("- Mã mặt hàng: " + item.getId());
		          System.out.println("  Màu: " + (item.getColor() != null ? item.getColor(): "Không có"));
		          System.out.println("  Số lượng: " + (item.getSize() != null ? item.getSize() : "Không có"));
		          System.out.println("  Mã mặt hàng: " + item.getId());
		          System.out.println("  Số lượng: " + item.getQuantity());
		          System.out.println("  Giá bán lẻ: " + item.getPriceRetail());
		          System.out.println("  Đã xóa: " + item.getIsDeleted());
		          System.out.println("  Sale: " + (item.getSale() != null ? item.getSale().getName() : "Không có"));
		      });
		      System.out.println("Khách hàng yêu thích:");
		  } else {
		      System.out.println("Sản phẩm không tồn tại.");
		  }
	} 
}
























//
//
//
//
//public void printProduct(Product product0) {
//    System.out.println("Thông tin sản phẩm:");
//    if (product0 != null) {
//        System.out.println("ID: " + product0.getId());
//        System.out.println("Tên: " + (product0.getName() != null ? product0.getName() : "Không có"));
//        System.out.println("Nhãn hiệu: " + (product0.getBrand() != null ? product0.getBrand().getName() : "Không có"));
//        System.out.println("Danh mục: " + (product0.getCategory() != null ? product0.getCategory().getId() : "Không có"));
//        System.out.println("Thời gian xuất bản: " + (product0.getPublishTime() != null ? product0.getPublishTime() : "Không có"));
//        System.out.println("Mô tả: " + (product0.getDescription() != null ? product0.getDescription() : "Không có"));
//        System.out.println("Thời gian tạo: " + (product0.getCreationTime() != null ? product0.getCreationTime() : "Không có"));
//        System.out.println("Thời gian cập nhật: " + (product0.getUpdateTime() != null ? product0.getUpdateTime() : "Không có"));
//        System.out.println("Ảnh sản phẩm:");
//        product0.getProductImages().forEach(image -> System.out.println("- " + image.getUrlImgs()));
//        System.out.println("Các mặt hàng sản phẩm:");
//        product0.getProductItems().forEach(item -> {
//            System.out.println("- Mã mặt hàng: " + item.getId());
//            System.out.println("  Màu: " + (item.getColor() != null ? item.getColor().getName() : "Không có"));
//            System.out.println("  Số lượng: " + (item.getSize() != null ? item.getSize().getName() : "Không có"));
//            System.out.println("  Mã mặt hàng: " + item.getId());
//            System.out.println("  Số lượng: " + item.getQuantity());
//            System.out.println("  Giá bán lẻ: " + item.getPriceRetail());
//            System.out.println("  Đã xóa: " + item.getIsDeleted());
//            System.out.println("  Sale: " + (item.getSale() != null ? item.getSale().getName() : "Không có"));
//        });
//        System.out.println("Khách hàng yêu thích:");
//    } else {
//        System.out.println("Sản phẩm không tồn tại.");
//    }
//}
