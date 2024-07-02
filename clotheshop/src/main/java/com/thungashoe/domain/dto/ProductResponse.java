package com.thungashoe.domain.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.thungashoe.domain.entity.Product;

	public record ProductResponse(String id, String name, String category, String brand, List<String> colors, List<String> sizes,
			double minPrice, double maxPrice,
			double star,
			long quantityItem,
			long quantityTotal, //boolean isSale,
			boolean isDeleted) {

	public static ProductResponse toProductDTO(Product product) {
		List<String> colors = product.getProductItems().stream().map(item -> item.getColor().getName()).distinct()
				.collect(Collectors.toList());

		List<String> sizes = product.getProductItems().stream().map(item -> item.getSize().getName()).distinct()
				.collect(Collectors.toList());

		double minPrice = product.getProductItems().stream().mapToDouble(item -> item.getPriceRetail()).min()
				.orElse(0.0);

		double maxPrice = product.getProductItems().stream().mapToDouble(item -> item.getPriceRetail()).max()
				.orElse(0.0);
		
		double averageStar = product.getProductItems().stream()
			    .flatMap(item -> item.getOrderDetails().stream())
			    .mapToInt(i -> i.getNumberStar())
			    .average()
			    .orElse(0.0);

		long quantityItem = product.getProductItems().size();
		long quantityTotal = product.getProductItems().stream().mapToLong(item -> item.getQuantity()).sum();


//		boolean isSale = product.getProductItems().stream().anyMatch(item -> item.getSale() != null);

		boolean isDeleted = product.getProductItems().stream().allMatch(item -> item.getIsDeleted() == true);

		return new ProductResponse(product.getId(), product.getName(), product.getCategory().getName(),
				product.getBrand().getName(), colors, sizes, minPrice, maxPrice, averageStar, quantityItem, quantityTotal, isDeleted);
	}
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductResponse{")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", category='").append(category).append('\'')
                .append(", brand='").append(brand).append('\'')
                .append(", colors=").append(colors)
                .append(", sizes=").append(sizes)
                .append(", minPrice=").append(minPrice)
                .append(", maxPrice=").append(maxPrice)
                .append(", quantityTotal=").append(quantityTotal)
//                .append(", isSale=").append(isSale)
                .append(", isDeleted=").append(isDeleted)
                .append('}');
        return builder.toString();
    }
}
