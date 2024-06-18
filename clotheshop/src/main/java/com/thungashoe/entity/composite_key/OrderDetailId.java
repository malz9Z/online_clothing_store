package com.thungashoe.entity.composite_key;

import com.thungashoe.entity.Order;
import com.thungashoe.entity.ProductItem;

import lombok.Data;

@Data
public class OrderDetailId {

	private Order order;

	private ProductItem product;

}
