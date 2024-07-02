package com.thungashoe.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.thungashoe.domain.entity.Brand;
import com.thungashoe.domain.entity.Category;
import com.thungashoe.domain.entity.ProductItem;
import com.thungashoe.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

	private String id;
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
