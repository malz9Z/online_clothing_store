package com.thungashoe.dto;

import java.util.List;

public record ProductFilterForm(
		Long category, int page, int size, String sortBy, String sortDirection,
		String categoryName,
		List<String> brandNames, List<String> colorNames, List<String> sizeNames,
		Double minPrice, Double maxPrice) {
}
