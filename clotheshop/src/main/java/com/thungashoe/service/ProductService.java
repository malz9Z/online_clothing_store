package com.thungashoe.service;

import java.util.List;

//import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thungashoe.dto.ProductResponse;
import com.thungashoe.entity.Brand;
import com.thungashoe.entity.Color;
import com.thungashoe.entity.Product;
import com.thungashoe.entity.ProductItem;
import com.thungashoe.entity.Size;
import com.thungashoe.repository.BrandRepository;
import com.thungashoe.repository.ColorRepository;
import com.thungashoe.repository.ProductItemRepository;
import com.thungashoe.repository.ProductRepository;
import com.thungashoe.repository.ProductSpecifications;
import com.thungashoe.repository.SizeRepository;

//import jakarta.persistence.EntityManager;


@Service
public class ProductService {
	
//	@Autowired
//    private EntityManager entityManager;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductItemRepository productItemRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ColorRepository colorRepository;

	@Autowired
	private SizeRepository sizeRepository;

	public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
//	@Transactional
	public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		Page<Product> productPage = productRepository.findAll(pageable);
//		productPage.getContent().forEach(product -> {
//            Hibernate.initialize(product.getProductItems());
//        });
//		printProductPage(productPage);
		return productPage.map(ProductResponse::toProductDTO);
	}

	public Page<Product> searchProducts(String searchTerm, int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return productRepository.findByNameContainingOrBrandContainingOrDescriptionContaining(searchTerm, searchTerm,
				searchTerm, pageable);
	}

	public Page<Product> getProductsByCategory(Long category, int page, int size, String sortBy, String sortDirection) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		return productRepository.findByCategoryId(category, pageable);
	}

	public Page<Product> filterProducts(Long category, int page, int size, String sortBy, String sortDirection,
			String categoryName, List<String> brandNames, List<String> colorNames, List<String> sizeNames,
			Double minPrice, Double maxPrice) {
		Pageable pageable;
		if ((sortBy == null || sortBy.isEmpty()) && (sortDirection == null || sortDirection.isEmpty())) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			pageable = PageRequest.of(page, size, sort);
		}
		Specification<Product> spec = Specification.where(ProductSpecifications.hasCategory(categoryName))
				.and(ProductSpecifications.hasBrands(brandNames)).and(ProductSpecifications.hasColors(colorNames))
				.and(ProductSpecifications.hasSizes(sizeNames))
				.and(ProductSpecifications.hasPriceBetween(minPrice, maxPrice));
		return productRepository.findAll(spec, pageable);
	}

	@Transactional
	public void addProduct(Product product) {

		// Kiểm tra và lưu thương hiệu
		String brandName = product.getBrand().getName();
		Brand brand = brandRepository.findByName(brandName).orElseGet(() -> {
			Brand newBrand = Brand.builder().name(brandName).build();
			return brandRepository.save(newBrand);
		});

		Product newProduct = Product.builder().name(product.getName()).category(product.getCategory())
				.description(product.getDescription()).brand(brand).build();
		newProduct = productRepository.save(newProduct);

		// Kiểm tra và lưu các màu sắc và kích thước cho từng sản phẩm
		for (ProductItem item : product.getProductItems()) {
			ProductItem productItem = ProductItem.builder()
					.product(newProduct)
					.color(item.getColor())
					.size(item.getSize())
					.quantity(item.getQuantity())
					.priceRetail(item.getPriceRetail())
					.isDeleted(Boolean.FALSE)
					.build();
			productItem.setProduct(newProduct);

			if (item.getColor() != null && item.getSize() != null) {
				String colorName = item.getColor().getName();
				Color color = colorRepository.findByName(colorName).orElseGet(() -> {
					Color newColor = Color.builder().name(colorName).build();
					return colorRepository.save(newColor);
				});
				productItem.setColor(color);

				String sizeName = item.getSize().getName();
				Size size = sizeRepository.findByName(sizeName).orElseGet(() -> {
					Size newSize = Size.builder().name(sizeName).build();
					return sizeRepository.save(newSize);
				});
				productItem.setSize(size);

				productItemRepository.save(productItem);
			}
		}
	}

	@Transactional
	public void updateProduct(Long productId, Product updatedProduct) {
		
		String brandName = updatedProduct.getBrand().getName();
		Brand brand = brandRepository.findByName(brandName).orElseGet(() -> {
			Brand newBrand = Brand.builder().name(brandName).build();
			return brandRepository.save(newBrand);
		});

		updatedProduct = Product.builder().name(updatedProduct.getName()).category(updatedProduct.getCategory())
				.description(updatedProduct.getDescription()).brand(brand).build();
		updatedProduct = productRepository.save(updatedProduct);

		// Kiểm tra và lưu các màu sắc và kích thước cho từng sản phẩm
		for (ProductItem item : updatedProduct.getProductItems()) {
			ProductItem productItem = new ProductItem();
			productItem.setProduct(updatedProduct);

			if (item.getColor() != null && item.getSize() != null) {
				String colorName = item.getColor().getName();
				Color color = colorRepository.findByName(colorName).orElseGet(() -> {
					Color newColor = Color.builder().name(colorName).build();
					return colorRepository.save(newColor);
				});
				productItem.setColor(color);

				String sizeName = item.getSize().getName();
				Size size = sizeRepository.findByName(sizeName).orElseGet(() -> {
					Size newSize = Size.builder().name(sizeName).build();
					return sizeRepository.save(newSize);
				});
				productItem.setSize(size);

				productItemRepository.save(productItem);
			}
		}
	}

	public void statusProduct(Long productId) {
		Product product = getProductById(productId);
		if (product != null) {
			product.setPublishTime(null);
			productRepository.save(product);
		}
	}
	
	
	public List<String> getAllSizes() {
		return productRepository.findAllSizes();
	}
	
	public List<String> getAllColors() {
		return productRepository.findAllColors();
	}
	
	public List<String> getAllBrands() {
		return productRepository.findAllBrands();
	}
	
	public  void printProductPage(Page<Product> productPage) {
        System.out.println("Page information:");
        System.out.println("Total elements: " + productPage.getTotalElements());
        System.out.println("Total pages: " + productPage.getTotalPages());
        System.out.println("Current page number: " + productPage.getNumber());
        System.out.println("Page size: " + productPage.getSize());

        System.out.println("Products in this page:");
        for (Product product : productPage.getContent()) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Category: " + product.getCategory());
            System.out.println("Product Brand: " + product.getBrand());
            System.out.println("Product Brand: " + product.getProductItems());
            // In ra các thông tin khác của sản phẩm (nếu có)
            System.out.println("--------------------------------------");
        }
    }
}
