package com.thungashoe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO{
	Long idProductItem;
	String image;
	String name;
	double price;
	long quantity;
}
