package com.thungashoe.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
	List<CartItemDTO> cartItems = new ArrayList<>();
}
