package com.thungashoe.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.thungashoe.entity.Brand;
import com.thungashoe.entity.Category;
import com.thungashoe.entity.Color;
import com.thungashoe.entity.Product;
import com.thungashoe.entity.ProductItem;
import com.thungashoe.entity.Size;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class ProductSpecifications {
	
	public static Specification<Product> getAllProducts() {
	    return (root, query, criteriaBuilder) -> {
	        root.fetch("category", JoinType.LEFT);
	        root.fetch("brand", JoinType.LEFT);
	        root.fetch("productItems", JoinType.LEFT).fetch("color", JoinType.LEFT);
	        root.fetch("productItems", JoinType.LEFT).fetch("size", JoinType.LEFT);
	        return criteriaBuilder.conjunction();
	    };
	}

	public static Specification<Product> hasColors(List<String> colorNames) {
        return (root, query, criteriaBuilder) -> {
            if (colorNames == null || colorNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Product, ProductItem> productItems = root.join("productItems", JoinType.INNER);
            Join<ProductItem, Color> colors = productItems.join("colors", JoinType.INNER);
            return colors.get("name").in(colorNames);
        };
    }

    public static Specification<Product> hasSizes(List<String> sizeNames) {
        return (root, query, criteriaBuilder) -> {
            if (sizeNames == null || sizeNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Product, ProductItem> productItems = root.join("productItems", JoinType.INNER);
            Join<ProductItem, Size> sizes = productItems.join("sizes", JoinType.INNER);
            return sizes.get("name").in(sizeNames);
        };
    }

    public static Specification<Product> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null || categoryName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Product, Category> category = root.join("category");
            return criteriaBuilder.equal(category.get("name"), categoryName);
        };
    }
    
    public static Specification<Product> hasBrands(List<String> brandNames) {
        return (root, query, criteriaBuilder) -> {
            if (brandNames == null || brandNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Product, Brand> brandJoin = root.join("brand", JoinType.INNER);
            return brandJoin.get("name").in(brandNames);
        };
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return criteriaBuilder.conjunction();
            }
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }
}
