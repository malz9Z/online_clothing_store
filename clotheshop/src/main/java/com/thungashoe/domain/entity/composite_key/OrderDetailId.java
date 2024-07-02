package com.thungashoe.domain.entity.composite_key;

import com.thungashoe.domain.entity.Order;
import com.thungashoe.domain.entity.ProductItem;

import lombok.Data;

@Data
public class OrderDetailId {

	private Order order;

	private ProductItem product;

}
