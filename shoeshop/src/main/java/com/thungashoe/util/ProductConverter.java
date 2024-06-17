package com.thungashoe.util;

import java.util.ArrayList;
import java.util.HashSet;

import com.thungashoe.dto.ProductDTO;
import com.thungashoe.entity.Product;

public class ProductConverter {

	public static Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setCategory(productDTO.getCategory());
        product.setPublishTime(productDTO.getPublishTime());
        product.setDescription(productDTO.getDescription());
//        product.setCreater(productDTO.getCreater());
//        product.setUpdater(productDTO.getUpdater());
        product.setProductItems(new HashSet<>(productDTO.getProductItems()));
        product.setCustomers(new HashSet<>(productDTO.getCustomers()));
        return product;
    }

    public static ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .brand(product.getBrand())
            .category(product.getCategory())
            .publishTime(product.getPublishTime())
            .description(product.getDescription())
//            .creater(product.getCreater())
//            .updater(product.getUpdater())
            .productItems(new ArrayList<>(product.getProductItems()))
            .customers(new ArrayList<>(product.getCustomers()))
            .build();
    }
}
