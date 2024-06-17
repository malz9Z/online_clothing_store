package com.thungashoe.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.thungashoe.entity.Brand;
import com.thungashoe.entity.Category;
import com.thungashoe.entity.ProductItem;
import com.thungashoe.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

	private Long id;
    private String name;
    private Brand brand;
    private Category category;
    private LocalDateTime publishTime;
    private String description;
    private User creater;
    private User updater;
	@Builder.Default
    private List<ProductItem> productItems = new ArrayList<>();
	@Builder.Default
    private List<User> customers = new ArrayList<>();
}
