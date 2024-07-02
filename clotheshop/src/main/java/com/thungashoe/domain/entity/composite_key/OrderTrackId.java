package com.thungashoe.domain.entity.composite_key;

import com.thungashoe.common.OrderAction;
import com.thungashoe.domain.entity.Order;

import lombok.Data;

@Data
public class OrderTrackId {

	private Order order;

	private OrderAction action;

}
