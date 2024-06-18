package com.thungashoe.entity.composite_key;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
@SuppressWarnings("serial")
public class ProductImageId implements Serializable{

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "color_id")
	private Long colorId;

}
